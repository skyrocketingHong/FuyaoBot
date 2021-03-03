package ninja.skyrocketing.bot.fuyao.sender.group;

import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.action.AsyncRecallResult;
import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.bot.fuyao.FuyaoBotApplication;
import ninja.skyrocketing.bot.fuyao.pojo.bot.BotFunctionTrigger;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupMessage;
import ninja.skyrocketing.bot.fuyao.sender.SendLog;
import ninja.skyrocketing.bot.fuyao.service.bot.BotFunctionTriggerService;
import ninja.skyrocketing.bot.fuyao.util.InvokeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-28 15:14:44
 */

@Component
public class GroupMessageSender {
    public static BotFunctionTriggerService botFunctionTriggerService;
    @Autowired
    public GroupMessageSender(BotFunctionTriggerService botFunctionTriggerService) {
        GroupMessageSender.botFunctionTriggerService = botFunctionTriggerService;
    }

    /**
     * 消息发送器
     * **/
    public static Message Sender(GroupMessageEvent event) throws Exception {
        GroupMessage groupMessage = new GroupMessage(event);
        String className = MatchedClass(groupMessage);
        if (className != null) {
            return RunByInvoke(className, groupMessage);
        }
        return null;
    }

    /**
     * 根据消息获取对应的实现类
     * **/
    public static String MatchedClass(GroupMessage groupMessage) {
        String msg = groupMessage.getMessage();
        for (BotFunctionTrigger botFunctionTrigger : botFunctionTriggerService.GetAllTrigger()) {
            if (msg.matches(botFunctionTrigger.getKeyword())) {
                if (botFunctionTrigger.getEnabled()) {
                    return botFunctionTrigger.getImplClass();
                } else {
                    return "FuncOffMessage.FuncOff";
                }
            }
        }
        return null;
    }

    /**
     * 根据实现类字符串执行对应的代码
     **/
    public static Message RunByInvoke(String str, GroupMessage groupMessage) throws Exception {
        return InvokeUtil.RunByInvoke(str, groupMessage);
    }

    /**
     * 根据群号发消息并保存日志
     * **/
    public static void SendMessageByGroupId(Message message, Long groupId) throws IOException {
        FuyaoBotApplication.bot.getGroup(groupId).sendMessage(message);
        SendLog.WriteLogToFile(message.toString().replaceAll("\n", ""), groupId);
    }
    public static void SendMessageByGroupId(String message, Long groupId) throws IOException {
        FuyaoBotApplication.bot.getGroup(groupId).sendMessage(message);
        SendLog.WriteLogToFile(message.replaceAll("\n", ""), groupId);
    }
    public static void SendMessageByGroupId(Message message, Long groupId, Integer recall) throws IOException {
        FuyaoBotApplication.bot.getGroup(groupId).sendMessage(message).recallIn(recall);
        SendLog.WriteLogToFile("(" + recall + " 毫秒后撤回) " + message.toString().replaceAll("\n", ""), groupId);
    }
}
