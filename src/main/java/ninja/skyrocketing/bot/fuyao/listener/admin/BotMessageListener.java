package ninja.skyrocketing.bot.fuyao.listener.admin;

import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.BotOfflineEvent;

/**
 * @Author skyrocketing Hong
 * @Date 2021-01-19 11:22:24
 */
public class BotMessageListener extends SimpleListenerHost {
    //监听掉线事件
    @EventHandler
    public ListeningStatus onBotOffline(BotOfflineEvent event) throws InterruptedException {
        Thread.sleep(30000);
        event.getBot().login();
        return ListeningStatus.LISTENING;
    }
}
