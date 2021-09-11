package ninja.skyrocketing.fuyao.bot.listener.friend;

import kotlin.coroutines.CoroutineContext;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.fuyao.bot.sender.friend.FriendMessageSender;
import ninja.skyrocketing.fuyao.bot.service.bot.BotConfigService;
import ninja.skyrocketing.fuyao.util.LogUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skyrocketing Hong
 * @date 2021-03-14 14:36:26
 */

@Component
@NoArgsConstructor
public class FriendMessageListener extends SimpleListenerHost {
    private static BotConfigService botConfigService;
    @Autowired
    public FriendMessageListener(
            BotConfigService botConfigService
    ) {
        FriendMessageListener.botConfigService = botConfigService;
    }
    @EventHandler
    public static ListeningStatus onFriendMessage(FriendMessageEvent event) {
        Message messageInFriend = event.getMessage();
        String messageInFriendToString = messageInFriend.toString();
        String messageInFriendContentToString = messageInFriend.contentToString();
        //满足触发命令
        if (messageInFriendContentToString.matches("^[~～/].+")) {
            //调用消息对应的实现类，并保存返回值（对应的回复）
            Message message = FriendMessageSender.sender(event);
            if (message != null) {
                MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
                messageChainBuilder.add(message);
                FriendMessageSender.sendMessageByFriendId(messageChainBuilder, event.getFriend());
            } else {
                FriendMessageSender.sendMessageByFriendId("😞 没有此功能或私聊模式下暂不支持此功能", event.getFriend());
            }
        }
        //不满足触发命令的通用回复
        else {
            FriendMessageSender.sendMessageByFriendId(botConfigService.getConfigValueByKey("friend_reply"), event.getFriend());
        }
        return ListeningStatus.LISTENING;
    }
    
    /**
     * 处理事件处理时抛出的异常
     * */
    @SneakyThrows
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        LogUtil.eventLog(context + "\n" + exception, "抛出异常");
    }
}
