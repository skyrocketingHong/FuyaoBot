package ninja.skyrocketing.bot.fuyao.function.timely;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import ninja.skyrocketing.bot.fuyao.FuyaoBotApplication;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupTimelyMessage;
import ninja.skyrocketing.bot.fuyao.service.group.GroupTimelyMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author skyrocketing Hong
 * @Date 2020-12-01 14:02:20
 */

@Component
public class Timely {
    private static GroupTimelyMessageService groupTimelyMessageService;

    @Autowired
    private Timely(
            GroupTimelyMessageService groupTimelyMessageService
    ) {
        Timely.groupTimelyMessageService = groupTimelyMessageService;
    }

    @Scheduled(cron = "0 */1 * * * ?")
    public static void TimelyMessage() {
        //获取实时时间
        Date nowDate = DateUtil.date();
        //从数据库中获取所有定时消息并迭代
        for (GroupTimelyMessage groupTimelyMessage : groupTimelyMessageService.GetAllTimelyMessage()) {
            //如果数据库中的定时消息和当前时间在以分钟为单位时相等，则发送对应的消息
            if (DateUtil.between(nowDate, groupTimelyMessage.getSendTime(), DateUnit.MINUTE) == 0) {
                //发送消息
                SendTimelyMessage(groupTimelyMessage.getMessageString(), groupTimelyMessage.getGroupId());
                //从数据库中移除已经发送了的消息
                int status = groupTimelyMessageService.DeleteSentMessageById(
                        groupTimelyMessage.getGroupId(), groupTimelyMessage.getUserId()
                );
                if (status != 1) {
                    SendTimelyMessage("❌ 数据库连接有问题，请联系开发者", groupTimelyMessage.getGroupId());
                }
            }
        }
    }

    public static void SendTimelyMessage(String message, Long groupId) {
        FuyaoBotApplication.bot.getGroup(groupId).sendMessage(message);
    }
}
