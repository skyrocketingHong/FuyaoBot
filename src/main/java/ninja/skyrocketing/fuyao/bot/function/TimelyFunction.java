package ninja.skyrocketing.fuyao.bot.function;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import ninja.skyrocketing.fuyao.bot.config.MiraiBotConfig;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupRSSMessage;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupTimelyMessage;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupUser;
import ninja.skyrocketing.fuyao.bot.sender.group.GroupMessageSender;
import ninja.skyrocketing.fuyao.bot.service.group.GroupRSSMessageService;
import ninja.skyrocketing.fuyao.bot.service.group.GroupTimelyMessageService;
import ninja.skyrocketing.fuyao.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * @author skyrocketing Hong
 * @date 2020-12-01 14:02:20
 */

@Component
public class TimelyFunction {
    private static GroupTimelyMessageService groupTimelyMessageService;
    private static GroupRSSMessageService groupRSSMessageService;

    @Autowired
    private TimelyFunction(
            GroupTimelyMessageService groupTimelyMessageService,
            GroupRSSMessageService groupRSSMessageService
    ) {
        TimelyFunction.groupTimelyMessageService = groupTimelyMessageService;
        TimelyFunction.groupRSSMessageService = groupRSSMessageService;
    }

    /**
     * 定时消息
     * 每分钟读取一次数据库
     * */
    @Scheduled(cron = "0 */1 * * * ?")
    public static void timelyMessage() throws IOException {
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
     * */
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
     * 每1分钟获取一次，然后判断时间戳
     * */
    @Scheduled(cron = "0 */1 * * * ?")
    public static void rssMessage() throws IOException, FeedException {
        //当前时间戳
        long nowTime = TimeUtil.getTimestamp();
        //当前时间戳去掉秒数，避免非整时间
        long nowTimeWithoutSecond = nowTime - nowTime % 60;
        //获取所有需要查询的RSS源
        List<GroupRSSMessage> groupRSSMessage = groupRSSMessageService.getAllGroupRSSMessage();
        for (GroupRSSMessage singleGroupRSSMessage : groupRSSMessage) {
            //需要抓取的RSS的URL
            String url = singleGroupRSSMessage.getRssUrl();
            //获取RSS Feed
            SyndFeed feed = new SyndFeedInput().build(new XmlReader(new URL(url)));
            //获取首条文章
            SyndEntry syndEntry = feed.getEntries().get(0);
            long feedPublishedTimestamp = syndEntry.getPublishedDate().getTime() / 1000;
            //判断是否为可推送消息
            if (feedPublishedTimestamp == nowTimeWithoutSecond || feedPublishedTimestamp >= nowTimeWithoutSecond - 60) {
                //Feed标题
                String feedTitle = feed.getTitle();
                //标题
                String title = syndEntry.getTitle();
                //文章链接
                String link = syndEntry.getLink();
                //发送消息
                GroupMessageSender.sendMessageByGroupId(
                        feedTitle + "更新了！\n" +
                                title + "\n" + link,
                        singleGroupRSSMessage.getGroupId()
                );
            }
        }
    }
}
