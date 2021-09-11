package ninja.skyrocketing.fuyao.bot.sender.friend;

import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.fuyao.FuyaoBotApplication;
import ninja.skyrocketing.fuyao.bot.pojo.user.UserMessage;
import ninja.skyrocketing.fuyao.util.LogUtil;
import ninja.skyrocketing.fuyao.util.MessageUtil;
import org.springframework.stereotype.Component;

/**
 * @author skyrocketing Hong
 * @date 2021-03-14 14:07:14
 */

@Component
public class FriendMessageSender {
    /**
     * 消息发送器
     */
    public static Message sender(FriendMessageEvent event) {
        UserMessage userMessage = new UserMessage(event);
        String className = MessageUtil.matchedClass(userMessage);
        if (className != null) {
            return MessageUtil.runByInvoke(className, userMessage);
        }
        return null;
    }

    /**
     * 根据Friend发消息并保存日志
     * @param message Message
     * @param friend Friend
     */
    public static void sendMessageByFriendId(Message message, Friend friend) {
        friend.sendMessage(message);
        LogUtil.messageLog(message.toString(), friend.getId(), false, friend.getNick());
    }

    /**
     * 根据Friend发消息并保存日志
     * @param message MessageChainBuilder
     * @param friend Friend
     */
    public static void sendMessageByFriendId(MessageChainBuilder message, Friend friend) {
        Message asMessageChain = message.asMessageChain();
        sendMessageByFriendId(asMessageChain, friend);
    }

    /**
     * 根据Friend发消息并保存日志
     * @param message String
     * @param friend Friend
     */
    public static void sendMessageByFriendId(String message, Friend friend) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add(message);
        sendMessageByFriendId(messageChainBuilder, friend);
    }
    
    /**
     * 根据QQ发消息并保存日志
     * @param message String
     * @param id Long
     */
    public static void sendMessageByFriendId(String message, Long id) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add(message);
        sendMessageByFriendId(messageChainBuilder, FuyaoBotApplication.bot.getFriend(id));
    }
    
    /**
     * 根据QQ发消息并保存日志
     * @param message MessageChainBuilder
     * @param id Long
     */
    public static void sendMessageByFriendId(MessageChainBuilder message, Long id) {
        sendMessageByFriendId(message, FuyaoBotApplication.bot.getFriend(id));
    }
    
    /**
     * 根据QQ发消息并保存日志
     * @param message Message
     * @param id Long
     */
    public static void sendMessageByFriendId(Message message, Long id) {
        sendMessageByFriendId(message, FuyaoBotApplication.bot.getFriend(id));
    }
}