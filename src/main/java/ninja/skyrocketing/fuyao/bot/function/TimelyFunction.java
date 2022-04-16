package ninja.skyrocketing.fuyao.bot.function;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.fuyao.bot.config.GlobalVariables;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupMessageCount;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupRSSMessage;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupTimelyMessage;
import ninja.skyrocketing.fuyao.bot.pojo.user.User;
import ninja.skyrocketing.fuyao.bot.sender.group.GroupMessageSender;
import ninja.skyrocketing.fuyao.bot.service.bot.BotConfigService;
import ninja.skyrocketing.fuyao.bot.service.bot.BotReplyMessageService;
import ninja.skyrocketing.fuyao.bot.service.group.GroupMessageCountService;
import ninja.skyrocketing.fuyao.bot.service.group.GroupRSSMessageService;
import ninja.skyrocketing.fuyao.bot.service.group.GroupTimelyMessageService;
import ninja.skyrocketing.fuyao.util.HttpUtil;
import ninja.skyrocketing.fuyao.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Time;
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
    private static GroupMessageCountService groupMessageCountService;
    private static BotReplyMessageService botReplyMessageService;
    
    @Autowired
    private TimelyFunction(
            GroupTimelyMessageService groupTimelyMessageService,
            GroupRSSMessageService groupRSSMessageService,
            BotConfigService botConfigService,
            GroupMessageCountService groupMessageCountService,
            BotReplyMessageService botReplyMessageService
    ) {
        TimelyFunction.groupTimelyMessageService = groupTimelyMessageService;
        TimelyFunction.groupRSSMessageService = groupRSSMessageService;
        TimelyFunction.botConfigService = botConfigService;
        TimelyFunction.groupMessageCountService = groupMessageCountService;
        TimelyFunction.botReplyMessageService = botReplyMessageService;
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
        for (User user : GlobalVariables.getGlobalVariables().getGroupUserTriggerDelay().keySet()) {
            //当用户已经超过冷却时间时，将用户移除
            if (GlobalVariables.getGlobalVariables().getGroupUserTriggerDelay().get(user) + 10 <= timeStamp) {
                GlobalVariables.getGlobalVariables().getGroupUserTriggerDelay().remove(user);
                GlobalVariables.getGlobalVariables().getGroupUserTriggerDelay().remove(user);
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
                    "🔔 RSS订阅提醒\n" +
                    "🗞️ 订阅源: " + feed.getTitle() + "\n" +
                    "🏷️ 标题: " + firstEntry.getTitle() + "\n" +
                    "🔗 链接: " + firstEntry.getLink() +  "\n" +
                    "⏰ 推送时间: " + TimeUtil.dateTimeFormatter(firstEntryPublishedDate) +"\n";
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
     * 每天08点00分1秒发送问候消息
     */
    @Value("${fuyao-bot.rss.morning-url}")
    private String morningRSSURL;
    @Scheduled(cron = "1 0 8 * * ?")
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
                                .replace("详情点击👉.*", "");
            } else {
                resultMessage = "☀ 群友们早上好啊\n由于抓取 \"即刻\" APP 的 RSS Hub 没有获取到今天的“一觉醒来发生了什么”，所以今天没有这个哦";
            }
        }
        for (Long groupId : groupMessageCountService.getLastDayGroupMessageCountListByCount(20)) {
            GroupMessageSender.sendMessageByGroupId(resultMessage, groupId);
        }
    }
    
    /**
     * 每天0点0分1秒将昨日消息数量放入last_day_message_count字段中并发送前一日消息统计信息
     * */
    @Scheduled(cron = "1 0 0 * * ?")
    public static void groupMessageCountUpdate() {
        //获取当前时间戳
        long currentTimeMillis = System.currentTimeMillis();
        //需要发送消息数量统计信息的群
        Map<Long, Integer> messageCountSenderMap = new HashMap<>();
        //获取全部GroupMessageCount
        List<GroupMessageCount> groupMessageCountList = groupMessageCountService.getAllGroupMessageCount();
        for (GroupMessageCount groupMessageCount : groupMessageCountList) {
            //将昨日消息数量设置
            groupMessageCount.setYesterdayMessageCount(groupMessageCount.getMessageCount());
            //将（今日）消息数量置为0
            groupMessageCount.setMessageCount(0);
            //如果时间差小于60秒，则发送消息统计信息
            if (currentTimeMillis - groupMessageCount.getLastUpdateTime().getTime() <= 60000L) {
                messageCountSenderMap.put(groupMessageCount.getGroupId(), groupMessageCount.getMessageCount());
            }
            //将最后修改时间修改为当前时间
            groupMessageCount.setLastUpdateTime(new Date());
            //写回数据库
            groupMessageCountService.updateGroupMessageCountById(groupMessageCount);
        }
        //发送消息统计消息
        for (Map.Entry<Long, Integer> entry: messageCountSenderMap.entrySet()) {
            MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
            messageChainBuilder.add("当前时间为" + TimeUtil.nowDateTime() +"\n");
            messageChainBuilder.add("📊 昨日本群共发送消息 " + entry.getValue() + " 条\n");
            messageChainBuilder.add("🌃 新的一天已经开始了\n群内的" +
                    botReplyMessageService.getGroupMemberTitleById(String.valueOf(entry.getKey())) +
                    "们" + "早点休息哦");
            GroupMessageSender.sendMessageByGroupId(messageChainBuilder, entry.getKey());
        }
    }
}
