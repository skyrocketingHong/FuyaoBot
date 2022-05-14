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
import ninja.skyrocketing.fuyao.util.RandomUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import static ninja.skyrocketing.fuyao.util.QueryUtil.nbnhhshQuery;

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
    public static ListeningStatus onFriendMessage(FriendMessageEvent event) throws NoSuchAlgorithmException {
        Message messageInFriend = event.getMessage();
        String messageInFriendToString = messageInFriend.toString();
        String messageInFriendContentToString = messageInFriend.contentToString();
        if (messageInFriendContentToString.matches("(((http|ftp|https)://)?)[-a-zA-Z0-9@:%_+.~#?&//=]{2,256}\\.[a-z]{2,4}\\b([-a-zA-Z0-9@:%_+.~#?&//=]*)?")
        ){
            //不满足触发命令不回复，如果发送的是违禁消息就删除好友
            event.getFriend().delete();
        }
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
        //拦截字母消息
        else if (messageInFriendContentToString.matches("[a-zA-Z]{2,}")) {
            List<String> queryList = nbnhhshQuery(messageInFriendContentToString);
            if (queryList.isEmpty() || queryList == null) {
                return ListeningStatus.LISTENING;
            }
            if (queryList.size() == 1) {
                FriendMessageSender.sendMessageByFriendId(queryList.get(0), event.getFriend());
            } else {
                int randomNum = RandomUtil.secureRandomNum(0, queryList.size());
                FriendMessageSender.sendMessageByFriendId(queryList.get(randomNum), event.getFriend());
            }
            return ListeningStatus.LISTENING;
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
