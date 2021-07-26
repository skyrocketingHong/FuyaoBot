package ninja.skyrocketing.fuyao.bot.listener.friend;

import lombok.NoArgsConstructor;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.FriendAddEvent;
import net.mamoe.mirai.event.events.FriendDeleteEvent;
import net.mamoe.mirai.event.events.NewFriendRequestEvent;
import ninja.skyrocketing.fuyao.bot.config.MiraiBotConfig;
import ninja.skyrocketing.fuyao.bot.sender.friend.FriendMessageSender;
import ninja.skyrocketing.fuyao.bot.service.bot.BotConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skyrocketing Hong
 * @date 2021-03-14 12:42:25
 */

@Component
@NoArgsConstructor
public class FriendEventListener extends SimpleListenerHost {
    private static BotConfigService botConfigService;
    @Autowired
    public FriendEventListener(
            BotConfigService botConfigService
    ) {
        FriendEventListener.botConfigService = botConfigService;
    }

    //一个账号请求添加机器人为好友
    @EventHandler
    public ListeningStatus onNewFriendRequestEvent(NewFriendRequestEvent event) {
        event.accept();
        String msg = botConfigService.getConfigValueByKey("reply");
        FriendMessageSender.sendMessageByFriendId(msg, event.getBot().getFriend(event.getFromId()));
        MiraiBotConfig.NewRelationshipMap.put("new_friend", MiraiBotConfig.NewRelationshipMap.get("new_friend") + 1);
        return ListeningStatus.LISTENING;
    }
    @EventHandler
    public ListeningStatus onFriendAddEvent(FriendAddEvent event) {
        return ListeningStatus.LISTENING;
    }

    //好友已被删除（后期完成用户经验和金币后实现）
    @EventHandler
    public ListeningStatus onFriendDeleteEvent(FriendDeleteEvent event) {
        return ListeningStatus.LISTENING;
    }
}
