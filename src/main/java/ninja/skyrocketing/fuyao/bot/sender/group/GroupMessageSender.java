package ninja.skyrocketing.fuyao.bot.sender.group;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.fuyao.FuyaoBotApplication;
import ninja.skyrocketing.fuyao.bot.pojo.bot.BotFunctionTrigger;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupMessage;
import ninja.skyrocketing.fuyao.bot.service.bot.BotFunctionTriggerService;
import ninja.skyrocketing.fuyao.util.InvokeUtil;
import ninja.skyrocketing.fuyao.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author skyrocketing Hong
 * @date 2020-11-28 15:14:44
 */

@Component
public class GroupMessageSender {
    private static BotFunctionTriggerService botFunctionTriggerService;
    @Autowired
    public GroupMessageSender(BotFunctionTriggerService botFunctionTriggerService) {
        GroupMessageSender.botFunctionTriggerService = botFunctionTriggerService;
    }

    /**
     * 消息发送器
     */
    public static Message sender(GroupMessageEvent event) throws Exception {
        GroupMessage groupMessage = new GroupMessage(event);
        String className = matchedClass(groupMessage);
        if (className != null) {
            return runByInvoke(className, groupMessage);
        }
        return null;
    }

    /**
     * 根据消息获取对应的实现类
     */
    public static String matchedClass(GroupMessage groupMessage) {
        String msg = groupMessage.getMessage();
        for (BotFunctionTrigger botFunctionTrigger : botFunctionTriggerService.getAllTrigger()) {
            if (msg.matches(botFunctionTrigger.getKeyword())) {
                if (botFunctionTrigger.getEnabled()) {
                    return botFunctionTrigger.getImplClass();
                } else {
                    return "function.FunctionDisabledMessage.FunctionDisabled";
                }
            }
        }
        return null;
    }

    /**
     * 根据实现类字符串执行对应的代码
     */
    public static Message runByInvoke(String str, GroupMessage groupMessage) throws Exception {
        return InvokeUtil.runByInvoke(str, groupMessage);
    }

    /**
     * 根据群号发消息并保存日志
     * @param message Message
     * @param group Group
     */
    public static void sendMessageByGroupId(Message message, Group group) throws IOException {
        group.sendMessage(message);
        LogUtil.messageLog(message.toString(), group.getId(), true, group.getName());
    }

    /**
     * 根据群号发消息并保存日志
     * @param message MessageChainBuilder
     * @param group Group
     */
    public static void sendMessageByGroupId(MessageChainBuilder message, Group group) throws IOException {
        Message asMessageChain = message.asMessageChain();
        sendMessageByGroupId(asMessageChain, group);
    }

    /**
     * 根据群号发消息并保存日志
     * @param message String
     * @param groupId Long
     */
    public static void sendMessageByGroupId(String message, Long groupId) throws IOException {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add(message);
        sendMessageByGroupId(messageChainBuilder, FuyaoBotApplication.bot.getGroup(groupId));
    }
    /**
     * 根据群号发消息并保存日志，带撤回
     * @param message MessageChainBuilder
     * @param group Group
     * @param recall Integer
     */
    public static void sendMessageByGroupId(MessageChainBuilder message, Group group, Integer recall) throws IOException {
        group.sendMessage(message.asMessageChain()).recallIn(recall);
        LogUtil.messageLog("(" + recall + " 毫秒后撤回) " + message.toString().replaceAll("\n", ""),
                group.getId(),
                true,
                group.getName()
        );
    }
}
