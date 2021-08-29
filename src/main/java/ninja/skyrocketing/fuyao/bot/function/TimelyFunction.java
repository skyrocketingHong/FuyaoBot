package ninja.skyrocketing.fuyao.bot.function;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ninja.skyrocketing.fuyao.FuyaoBotApplication;
import ninja.skyrocketing.fuyao.bot.config.GlobalVariables;
import ninja.skyrocketing.fuyao.bot.config.MiraiBotConfig;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupRSSMessage;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupTimelyMessage;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupUser;
import ninja.skyrocketing.fuyao.bot.sender.group.GroupMessageSender;
import ninja.skyrocketing.fuyao.bot.service.bot.BotConfigService;
import ninja.skyrocketing.fuyao.bot.service.group.GroupRSSMessageService;
import ninja.skyrocketing.fuyao.bot.service.group.GroupTimelyMessageService;
import ninja.skyrocketing.fuyao.util.HttpUtil;
import ninja.skyrocketing.fuyao.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author skyrocketing Hong
 * @date 2020-12-01 14:02:20
 */

@Component
public class TimelyFunction {
    private static GroupTimelyMessageService groupTimelyMessageService;
    private static GroupRSSMessageService groupRSSMessageService;
    private static BotConfigService botConfigService;
    
    @Autowired
    private TimelyFunction(
            GroupTimelyMessageService groupTimelyMessageService,
            GroupRSSMessageService groupRSSMessageService,
            BotConfigService botConfigService
    ) {
        TimelyFunction.groupTimelyMessageService = groupTimelyMessageService;
        TimelyFunction.groupRSSMessageService = groupRSSMessageService;
        TimelyFunction.botConfigService = botConfigService;
    }
    
    /**
     * 定时消息
     * 每分钟读取一次数据库
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public static void timelyMessage() {
        //获取实时时间
        Date nowDate = DateUtil.date();
        //从数据库中获取所有定时消息并迭代
        for (GroupTimelyMessage groupTimelyMessage : groupTimelyMessageService.getAllTimelyMessage()) {
            //如果数据库中的定时消息和当前时间在以分钟为单位时相等，则发送对应的消息
            if (DateUtil.between(nowDate, groupTimelyMessage.getSendTime(), DateUnit.MINUTE) == 0) {
                //发送消息
                GroupMessageSender.sendMessageByGroupId(groupTimelyMessage.getMessageString(), groupTimelyMessage.getGroupId());
                //从数据库中移除已经发送了的消息
                int status = groupTimelyMessageService.deleteSentMessageById(
                        groupTimelyMessage.getGroupId(), groupTimelyMessage.getUserId()
                );
                if (status != 1) {
                    GroupMessageSender.sendMessageByGroupId("❌ 数据库连接有问题，请联系开发者", groupTimelyMessage.getGroupId());
                }
            }
        }
    }
    
    /**
     * 定时处理防刷屏
     * 每10秒钟判断一次
     */
    @Scheduled(cron = "*/10 * * * * ?")
    public static void preventAbuse() {
        long timeStamp = TimeUtil.getTimestamp();
        for (GroupUser groupUser : MiraiBotConfig.GroupUserTriggerDelay.keySet()) {
            //当用户已经超过冷却时间时，将用户移除
            if (MiraiBotConfig.GroupUserTriggerDelay.get(groupUser) + 10 <= timeStamp) {
                MiraiBotConfig.GroupUserTriggerDelay.remove(groupUser);
                MiraiBotConfig.GroupUserTriggerDelayNotified.remove(groupUser);
            }
        }
    }
    
    /**
     * 定时获取RSS源更新
     * 每30秒抓取一次
     */
    @Scheduled(cron = "*/30 * * * * ?")
    public void rssMessage() {
        //构造PushMessage内部类
        @Getter
        @Setter
        @AllArgsConstructor
        class PushMessage {
            private String message;
            private Date publishedDate;
            private String url;
        }
        //获取所有需要查询的RSS源
        List<String> allRSSUrl = groupRSSMessageService.getAllRSSUrl();
        //获取所有GroupRSSMessage
        List<GroupRSSMessage> groupRSSMessage = groupRSSMessageService.getAllGroupRSSMessage();
        //RSS URL, PushMessage的键值对
        Map<String, PushMessage> urlAndPushMessageMap = new HashMap<>();
        for (String rssUrl : allRSSUrl) {
            //获取RSS Feed
            SyndFeed feed = HttpUtil.getRSSFeed(rssUrl);
            if (feed == null) {
                urlAndPushMessageMap.remove(rssUrl);
                continue;
            }
            //获取首条消息
            SyndEntry firstEntry = feed.getEntries().get(0);
            //获取首条消息的推送时间
            Date firstEntryPublishedDate = firstEntry.getPublishedDate();
            //直接抓取目前最新的一篇文章
            //最终推送文案
            String resultMessage =
                    "🔔 \"" + feed.getTitle() + "\"" +
                            " 在 " + TimeUtil.dateTimeFormatter(firstEntryPublishedDate) +
                            " 推送了：\n" +
                            firstEntry.getTitle() + "\n" + firstEntry.getLink();
            PushMessage pushMessage = new PushMessage(resultMessage, firstEntryPublishedDate, firstEntry.getLink());
            urlAndPushMessageMap.put(rssUrl, pushMessage);
        }
        //遍历GroupRSSMessage
        for (GroupRSSMessage singleGroupRSSMessage : groupRSSMessage) {
            if (singleGroupRSSMessage.isEnabled()) {
                //获取单个GroupRSSMessage中的RssUrl
                String rssUrl = singleGroupRSSMessage.getRssUrl();
                if (urlAndPushMessageMap.containsKey(rssUrl)) {
                    //获取PushMessage类
                    PushMessage pushMessage = urlAndPushMessageMap.get(rssUrl);
                    //获取文章URL，便于后期判断
                    String url = pushMessage.getUrl();
                    if (singleGroupRSSMessage.getLastNotifiedDate() == null
                            || singleGroupRSSMessage.getLastNotifiedUrl() == null
                            || singleGroupRSSMessage.getLastNotifiedDate().before(pushMessage.getPublishedDate())
                            || !Objects.equals(singleGroupRSSMessage.getLastNotifiedUrl(), url)
                    ) {
                        //发送消息
                        if (GroupMessageSender.sendMessageByGroupId(pushMessage.getMessage(), singleGroupRSSMessage.getGroupId())) {
                            //将当前推送时间和推送URL写回数据库，便于下次判断
                            singleGroupRSSMessage.setLastNotifiedDate(new Date());
                            singleGroupRSSMessage.setLastNotifiedUrl(url);
                            groupRSSMessageService.updateGroupRSSMessage(singleGroupRSSMessage);
                        }
                    }
                }
            }
        }
    }
    
    /**
     * 每天早上7点40分发送问候消息
     */
    @Value("${fuyao-bot.rss.morning-url}")
    private String morningRSSURL;
    @Scheduled(cron = "0 29 7 * * ?")
    public void morningMessage() {
        //获取RSS Feed
        SyndFeed feed = HttpUtil.getRSSFeed(morningRSSURL);
        String resultMessage;
        if (feed == null) {
            resultMessage = "☀ 群友们早上好啊\n由于系统原因今天没有“一觉醒来发生了什么”";
        } else {
            //获取首条消息
            SyndEntry firstEntry = feed.getEntries().get(0);
            //获取首条消息的推送时间
            Date firstEntryPublishedDate = firstEntry.getPublishedDate();
            if (DateUtil.isSameDay(firstEntryPublishedDate, new Date())) {
                //最终推送文案
                resultMessage = "☀ 群友们早上好啊\n下面是“一觉醒来发生了什么”（来自\"即刻\" APP）\n" +
                        firstEntry.getDescription().getValue()
                                .replace("<br>", "\n")
                                .replace("\n（欢迎到评论区理性发言，友好讨论）", "")
                                .replace("详情点击👉", "新闻详情请点击👇\n");
            } else {
                resultMessage = "☀ 群友们早上好啊\n由于\"即刻\" APP 没有推送，今天没有“一觉醒来发生了什么”";
            }
        }
        for (Long groupId : GlobalVariables.getGlobalVariables().getMorningMessageList()) {
            GroupMessageSender.sendMessageByGroupId(resultMessage, groupId);
        }
        GlobalVariables.getGlobalVariables().getMorningMessageList().clear();
    }
    
    /**
     * 每天0点发送消息数量统计并将满足要求的群放入list中
     * */
    @Scheduled(cron = "0 0 0 * * ?")
    public static void groupMessageCount() {
        Map<Long, Integer> GroupMessagesCount = GlobalVariables.getGlobalVariables().getGroupMessagesCount();
        //从map中移除所有统计记录
        GlobalVariables.getGlobalVariables().getGroupMessagesCount().clear();
        //结束统计时间
        Date endDate = new Date();
        String endDateStr = TimeUtil.dateTimeFormatter(endDate);
        //开始统计时间
        Date startDate;
        //如果bot启动时间在当前发送消息的时间的24小时内，则使用启动时间作为开始统计时间
        if (DateUtil.between(FuyaoBotApplication.StartDate, endDate, DateUnit.HOUR) < 24) {
            startDate = FuyaoBotApplication.StartDate;
        } else {
            startDate = DateUtil.offsetHour(endDate, -24);
        }
        String startDateStr = TimeUtil.dateTimeFormatter(startDate);
        for (Map.Entry<Long, Integer> entry : GroupMessagesCount.entrySet()) {
            if (entry.getValue() >= 3) {
                //将满足要求的群放入list中
                GlobalVariables.getGlobalVariables().getMorningMessageList().add(entry.getKey());
            }
            if (entry.getValue() >= 10) {
                String message = "📊 发送消息数量统计\n" +
                        startDateStr + " 至 " + endDateStr + "\n" +
                        "本群共发送消息 " + entry.getValue() + " 条";
                GroupMessageSender.sendMessageByGroupId(message, entry.getKey());
            }
        }
    }
}
