package ninja.skyrocketing.bot.fuyao.sender.group;

import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.bot.fuyao.FuyaoBotApplication;
import ninja.skyrocketing.bot.fuyao.pojo.bot.BotFunctionTrigger;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupMessage;
import ninja.skyrocketing.bot.fuyao.service.bot.BotFunctionTriggerService;
import ninja.skyrocketing.bot.fuyao.util.InvokeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        //提前判断闪照，并将消息转换为数据库中对应的实现类的关键词
        if (msg.contains("[闪照]")) {
            if (groupMessage.getGroupMessageEvent().getMessage().toString().matches(".*mirai:flash:.*")) {
                msg = groupMessage.getGroupMessageEvent().getMessage().toString();
            } else {
                return null;
            }
        }
        //提前判断红包，并将消息转换为数据库中对应的实现类的关键词
        if (msg.equals("[QQ红包]请使用新版手机QQ查收红包。") || msg.equals("[QQ红包]你收到一个红包，请在新版手机QQ查看。")) {
            msg = groupMessage.getGroupMessageEvent().getMessage().toString();
        }
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
     * 根据群号发消息
     * **/
    public static boolean SendMessageByGroupId(Message message, Long groupId) {
        MessageReceipt<Contact> messageReceipt = FuyaoBotApplication.bot.getGroup(groupId).sendMessage(message);
        return messageReceipt.isToGroup();
    }

    public static boolean SendMessageByGroupId(String message, Long groupId) {
        MessageReceipt<Contact> messageReceipt = FuyaoBotApplication.bot.getGroup(groupId).sendMessage(message);
        return messageReceipt.isToGroup();
    }
}
