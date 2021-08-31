package ninja.skyrocketing.fuyao.bot.sender.group;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.fuyao.FuyaoBotApplication;
import ninja.skyrocketing.fuyao.bot.function.ConfigFunction;
import ninja.skyrocketing.fuyao.bot.pojo.user.UserMessage;
import ninja.skyrocketing.fuyao.util.LogUtil;
import ninja.skyrocketing.fuyao.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author skyrocketing Hong
 * @date 2020-11-28 15:14:44
 */

@Component
public class GroupMessageSender {
    /**
     * 消息发送器
     */
    public static Message sender(GroupMessageEvent event) {
        UserMessage userMessage = new UserMessage(event);
        String className = MessageUtil.matchedClass(userMessage);
        if (className != null) {
            return MessageUtil.runByInvoke(className, userMessage);
        }
        return null;
    }

    /**
     * 根据群号发消息并保存日志
     * @param message Message
     * @param group Group
     * @return boolean
     */
    public static boolean sendMessageByGroupId(Message message, Group group) {
        try {
            group.sendMessage(message);
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(GroupMessageSender.class);
            logger.error("在" + group.getName() + " (" + group.getId() + ") 中发消息时出现错误，错误详情: " + e.getMessage());
            return false;
        }
        LogUtil.messageLog(message.toString(), group.getId(), true, group.getName());
        //添加机器人群名片判断，防止恶意修改
        ConfigFunction.botNameCardCheck(group);
        return true;
    }
    
    /**
     * 根据群号发消息并保存日志返回回执
     * @param message Message
     * @param group Group
     */
    public static MessageReceipt<Group> sendMessageByGroupIdWithReceipt(Message message, Group group) {
        MessageReceipt<Group> messageReceipt;
        try {
            messageReceipt = group.sendMessage(message);
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(GroupMessageSender.class);
            logger.error("在" + group.getName() + " (" + group.getId() + ") 中发消息时出现错误，错误详情: " + e.getMessage());
            return null;
        }
        LogUtil.messageLog(message.toString(), group.getId(), true, group.getName());
        //添加机器人群名片判断，防止恶意修改
        ConfigFunction.botNameCardCheck(group);
        return messageReceipt;
    }
    
    /**
     * 根据群号发消息并保存日志返回回执
     * @param message String
     * @param group Group
     */
    public static MessageReceipt<Group> sendMessageByGroupIdWithReceipt(String message, Group group) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add(message);
        return sendMessageByGroupIdWithReceipt(messageChainBuilder.asMessageChain(), group);
    }
    
    /**
     * 根据群号发消息并保存日志返回回执
     * @param message String
     * @param group Group
     */
    public static MessageReceipt<Group> sendMessageByGroupIdWithReceipt(MessageChainBuilder message, Group group) {
        return sendMessageByGroupIdWithReceipt(message.asMessageChain(), group);
    }
    
    /**
     * 根据群号发消息并保存日志
     * @param message MessageChainBuilder
     * @param group Group
     */
    public static boolean sendMessageByGroupId(MessageChainBuilder message, Group group) {
        Message asMessageChain = message.asMessageChain();
        return sendMessageByGroupId(asMessageChain, group);
    }

    /**
     * 根据群号发消息并保存日志
     * @param message String
     * @param groupId Long
     */
    public static boolean sendMessageByGroupId(String message, Long groupId) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add(message);
        return sendMessageByGroupId(messageChainBuilder, FuyaoBotApplication.bot.getGroup(groupId));
    }
    
    /**
     * 根据群号发消息并保存日志，带撤回
     * @param message MessageChain
     * @param group Group
     * @param recall Integer
     */
    public static void sendMessageByGroupId(MessageChain message, Group group, Long recall) {
        group.sendMessage(message).recallIn(recall);
        LogUtil.messageLog("(" + recall + " 毫秒后撤回) " + message.toString().replaceAll("\n", ""),
                group.getId(),
                true,
                group.getName()
        );
        //添加机器人群名片判断，防止恶意修改
        ConfigFunction.botNameCardCheck(group);
    }
    
    /**
     * 根据群号发消息并保存日志，带撤回
     * @param message MessageChainBuilder
     * @param group Group
     * @param recall Integer
     */
    public static void sendMessageByGroupId(MessageChainBuilder message, Group group, Long recall) {
        sendMessageByGroupId(message.asMessageChain(), group, recall);
    }
}
