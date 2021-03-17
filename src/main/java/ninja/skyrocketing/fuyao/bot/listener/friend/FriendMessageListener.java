package ninja.skyrocketing.fuyao.bot.listener.friend;

import lombok.NoArgsConstructor;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import org.springframework.stereotype.Component;

/**
 * @author skyrocketing Hong
 * @date 2021-03-14 14:36:26
 */

@Component
@NoArgsConstructor
public class FriendMessageListener extends SimpleListenerHost {
    @EventHandler
    public static ListeningStatus onFriendMessage(FriendMessageEvent event) {

        return ListeningStatus.LISTENING;
    }
}
