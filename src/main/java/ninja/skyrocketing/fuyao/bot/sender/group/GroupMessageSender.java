package ninja.skyrocketing.fuyao.bot.sender.group;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.fuyao.FuyaoBotApplication;
import ninja.skyrocketing.fuyao.bot.function.ConfigFunction;
import ninja.skyrocketing.fuyao.bot.pojo.bot.BotFunctionTrigger;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupMessage;
import ninja.skyrocketing.fuyao.bot.service.bot.BotAdminUserService;
import ninja.skyrocketing.fuyao.bot.service.bot.BotFunctionTriggerService;
import ninja.skyrocketing.fuyao.util.InvokeUtil;
import ninja.skyrocketing.fuyao.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skyrocketing Hong
 * @date 2020-11-28 15:14:44
 */

@Component
public class GroupMessageSender {
    private static BotFunctionTriggerService botFunctionTriggerService;
    private static BotAdminUserService botAdminUserService;
    @Autowired
    public GroupMessageSender(
            BotFunctionTriggerService botFunctionTriggerService,
            BotAdminUserService botAdminUserService
    ) {
        GroupMessageSender.botFunctionTriggerService = botFunctionTriggerService;
        GroupMessageSender.botAdminUserService = botAdminUserService;
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
        //保存消息便于便利
        String msg = groupMessage.getMessage();
        //遍历所有功能
        for (BotFunctionTrigger botFunctionTrigger : botFunctionTriggerService.getAllTrigger()) {
            //如果包含对应关键词，触发消息
            if (msg.matches(botFunctionTrigger.getKeyword())) {
                //存储功能名
                groupMessage.setFunctionName(botFunctionTrigger.getTriggerName());
                //判断功能是否开启
                if (botFunctionTrigger.getEnabled()) {
                    //判断是否为管理员功能
                    if(botFunctionTrigger.getIsAdmin()) {
                        //判断用户是否为管理员
                        if (botAdminUserService.isAdmin(groupMessage.getGroupUser().getUserId())) {
                            return botFunctionTrigger.getImplClass();
                        }
                        //非管理员提醒
                        return "function.FunctionDisabledMessage.adminFunction";
                    }
                    return botFunctionTrigger.getImplClass();
                } else {
                    //禁用消息提醒
                    return "function.FunctionDisabledMessage.FunctionDisabled";
                }
            }
        }
        return null;
    }

    /**
     * 根据实现类字符串执行对应的代码
     */
    public static Message runByInvoke(String str, GroupMessage groupMessage) {
        try {
            return InvokeUtil.runByInvoke(str, groupMessage);
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(GroupMessageSender.class);
            logger.error("查找运行类时错误，错误详情: " + e.getMessage());
            return null;
        }
    }

    /**
     * 根据群号发消息并保存日志
     * @param message Message
     * @param group Group
     * @return boolean
     */
    public static boolean sendMessageByGroupId(Message message, Group group) {
        if (group.getSettings().isMuteAll() || group.getBotMuteRemaining() > 0) {
            Logger logger = LoggerFactory.getLogger(GroupMessageSender.class);
            logger.error("在" + group.getName() + " (" + group.getId() + ") 中发消息时出现错误，错误详情: " + "群被全员禁言或机器人被禁言");
            return false;
        }
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
