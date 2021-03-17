package ninja.skyrocketing.fuyao.bot.sender.friend;

import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.fuyao.util.LogUtil;

import java.io.IOException;

/**
 * @author skyrocketing Hong
 * @date 2021-03-14 14:07:14
 */

public class FriendMessageSender {
    /**
     * 根据群号发消息并保存日志
     * @param message Message
     * @param friend Friend
     */
    public static void sendMessageByFriendId(Message message, Friend friend) throws IOException {
        friend.sendMessage(message);
        LogUtil.messageLog(message.toString(), friend.getId(), false, friend.getNick());
    }

    /**
     * 根据群号发消息并保存日志
     * @param message MessageChainBuilder
     * @param friend Friend
     */
    public static void sendMessageByFriendId(MessageChainBuilder message, Friend friend) throws IOException {
        Message asMessageChain = message.asMessageChain();
        sendMessageByFriendId(asMessageChain, friend);
    }

    /**
     * 根据群号发消息并保存日志
     * @param message String
     * @param friend Friend
     */
    public static void sendMessageByFriendId(String message, Friend friend) throws IOException {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add(message);
        sendMessageByFriendId(messageChainBuilder, friend);
    }
}
