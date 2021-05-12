package ninja.skyrocketing.fuyao.bot.function;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import ninja.skyrocketing.fuyao.bot.config.MiraiBotConfig;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupTimelyMessage;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupUser;
import ninja.skyrocketing.fuyao.bot.sender.group.GroupMessageSender;
import ninja.skyrocketing.fuyao.bot.service.group.GroupTimelyMessageService;
import ninja.skyrocketing.fuyao.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * @author skyrocketing Hong
 * @date 2020-12-01 14:02:20
 */

@Component
public class TimelyFunction {
    private static GroupTimelyMessageService groupTimelyMessageService;

    @Autowired
    private TimelyFunction(
            GroupTimelyMessageService groupTimelyMessageService
    ) {
        TimelyFunction.groupTimelyMessageService = groupTimelyMessageService;
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
}
