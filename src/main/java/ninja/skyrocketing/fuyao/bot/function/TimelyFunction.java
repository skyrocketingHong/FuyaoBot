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
import ninja.skyrocketing.fuyao.util.MessageUtil;
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
     * 每10秒抓取一次
     */
    @Scheduled(cron = "*/10 * * * * ?")
    public void rssMessage() {
        //获取所有需要查询的RSS源
        List<String> allRSSUrl = groupRSSMessageService.getAllRSSUrl();
        //获取所有GroupRSSMessage
        List<GroupRSSMessage> groupRSSMessage = groupRSSMessageService.getAllGroupRSSMessage();
        //RSS URL, SyndFeed的键值对
        Map<String, SyndFeed> urlAndSyndFeedMap = new HashMap<>();
        for (String rssUrl : allRSSUrl) {
            //获取RSS Feed
            SyndFeed feed = HttpUtil.getRSSFeed(rssUrl);
            //获取不到直接跳过
            if (feed == null) {
                continue;
            }
            //将获取到的放入map中
            urlAndSyndFeedMap.put(rssUrl, feed);
        }
        for (GroupRSSMessage singleGroupRSSMessage : groupRSSMessage) {
            List<SyndEntry> syndEntry = new LinkedList<>();
            if (urlAndSyndFeedMap.containsKey(singleGroupRSSMessage.getRssUrl())) {
                syndEntry = urlAndSyndFeedMap.get(singleGroupRSSMessage.getRssUrl()).getEntries();
            }
            //数组下标，保证不多于6条且以从旧到新的顺序发送
            int i = Math.min(syndEntry.size(), 5);
            //消息发送状态
            boolean sendStatus = false;
            //最后推送的消息的链接，用于写回数据库
            String url = null;
            //最后推送的消息的时间，用于写回数据库
            Date publishedDate = null;
            // 1、获取到的项目的推送日期不早于数据库中上次推送的时间
            // 2、获取到最新推送的链接与数据库中上次推送的链接不同
            // 3、不超过6条
            while (i >= 0
                    && syndEntry.get(i).getPublishedDate().getTime() >= singleGroupRSSMessage.getLastNotifiedDate().getTime()
                    && !syndEntry.get(0).getLink().equals(singleGroupRSSMessage.getLastNotifiedUrl())
            ) {
                //保留链接
                url = syndEntry.get(i).getLink();
                //保留日期
                publishedDate = syndEntry.get(i).getPublishedDate();
                //生成消息
                String resultMessage =
                        "🔔 RSS订阅提醒\n" +
                                "🗞️ 标题: " + syndEntry.get(i).getTitle() + " (" + urlAndSyndFeedMap.get(singleGroupRSSMessage.getRssUrl()).getTitle() + ")" + "\n" +
                                "🔗 链接: " + url +  "\n" +
                                "⏰ 发布时间: " + TimeUtil.dateTimeFormatter(publishedDate) +"\n";
                //保留发送状态
                sendStatus = GroupMessageSender.sendMessageByGroupId(resultMessage, singleGroupRSSMessage.getGroupId());
                --i;
            }
            //根据最后一次的发送状态，选择是否将数据写回数据库
            if (sendStatus) {
                singleGroupRSSMessage.setLastNotifiedDate(publishedDate);
                singleGroupRSSMessage.setLastNotifiedUrl(url);
                groupRSSMessageService.updateGroupRSSMessage(singleGroupRSSMessage);
            }
        }
    }

    /**
     * 每天08点00分0秒发送问候消息
     */
    @Value("${fuyao-bot.rss.morning-url}")
    private String morningRSSURL;
    @Scheduled(cron = "0 0 8 * * ?")
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
     * 每天0点0分0秒将昨日消息数量放入last_day_message_count字段中并发送前一日消息统计信息
     * */
    @Scheduled(cron = "0 0 0 * * ?")
    public static void groupMessageCountUpdate() {
        //获取当前时间戳
        Date currentDate = new Date();
        long currentTimeMillis = currentDate.getTime();
        //获取全部GroupMessageCount
        List<GroupMessageCount> groupMessageCountList = groupMessageCountService.getAllGroupMessageCount();
        //昨日的最后更新时间
        Map<Long, Date> yesterdayLastUpdateTimes = new HashMap<>();
        //修改数据库数据
        for (GroupMessageCount groupMessageCount : groupMessageCountList) {
            //将昨日消息数量设置
            groupMessageCount.setYesterdayMessageCount(groupMessageCount.getMessageCount());
            //将（今日）消息数量置为0
            groupMessageCount.setMessageCount(0);
            //记录昨日的最后更新时间
            yesterdayLastUpdateTimes.put(groupMessageCount.getGroupId(), groupMessageCount.getLastUpdateTime());
            //将最后修改时间修改为当前时间
            groupMessageCount.setLastUpdateTime(currentDate);
            //写回数据库
            groupMessageCountService.updateGroupMessageCountById(groupMessageCount);
        }
        //前一日已发送消息统计消息
        for (GroupMessageCount groupMessageCount : groupMessageCountList) {
            //如果时间差小于60秒，则发送消息统计信息
            if (currentTimeMillis - yesterdayLastUpdateTimes.get(groupMessageCount.getGroupId()).getTime() <= 1800000L) {
                MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
                messageChainBuilder.add("当前时间为" + TimeUtil.nowDateTime() +"\n");
                messageChainBuilder.add("📊 昨日本群发送消息约 " + MessageUtil.getEmojiNumber(groupMessageCount.getYesterdayMessageCount()) + " 条\n");
                messageChainBuilder.add(
                        "🌃 新的一天已经开始了" + "\n" +
                        "🌙 " + botReplyMessageService.getGroupMemberTitleById(String.valueOf(groupMessageCount.getGroupId())) + "们早点休息哦"
                );
                GroupMessageSender.sendMessageByGroupId(messageChainBuilder, groupMessageCount.getGroupId());
            }
        }
    }
}
