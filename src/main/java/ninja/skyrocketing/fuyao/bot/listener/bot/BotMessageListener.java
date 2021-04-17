package ninja.skyrocketing.fuyao.bot.listener.bot;

import kotlin.coroutines.CoroutineContext;
import lombok.SneakyThrows;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.BotOfflineEvent;
import net.mamoe.mirai.event.events.BotOnlineEvent;
import net.mamoe.mirai.event.events.BotReloginEvent;
import ninja.skyrocketing.fuyao.util.LogUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * @author skyrocketing Hong
 * @date 2021-01-19 11:22:24
 */

public class BotMessageListener extends SimpleListenerHost {
    /**
     * 机器人主动下线
     * */
    @EventHandler
    public ListeningStatus onBotOffline(BotOfflineEvent.Active event) throws InterruptedException, IOException {
        LogUtil.eventLog(event.getReconnect() + " " + event, "机器人主动下线");
        return ListeningStatus.LISTENING;
    }

    /**
     * 机器人被挤下线
     * */
    @EventHandler
    public ListeningStatus onBotOffline(BotOfflineEvent.Force event) throws InterruptedException, IOException {
        event.setReconnect(true);
        LogUtil.eventLog(event.getReconnect() + " " + event, "机器人被挤下线");
        return ListeningStatus.LISTENING;
    }

    /**
     * 机器人被服务器断开或因网络问题而掉线
     * */
    @EventHandler
    public ListeningStatus onBotOffline(BotOfflineEvent.Dropped event) throws InterruptedException, IOException {
        event.setReconnect(true);
        LogUtil.eventLog(event.getReconnect() + " " + event, "机器人被服务器断开或因网络问题而掉线");
        return ListeningStatus.LISTENING;
    }

    /**
     * 服务器主动要求更换另一个服务器
     * */
    @EventHandler
    public ListeningStatus onBotOffline(BotOfflineEvent.RequireReconnect event) throws InterruptedException, IOException {
        event.setReconnect(true);
        LogUtil.eventLog(event.getReconnect() + " " + event, "服务器主动要求更换另一个服务器");
        return ListeningStatus.LISTENING;
    }

    /**
     * 机器人重新登录
     * */
    @EventHandler
    public ListeningStatus onBotReloginEvent(BotReloginEvent event) throws IOException {
        LogUtil.eventLog(event.toString(), "机器人重新登录");
        return ListeningStatus.LISTENING;
    }

    /**
     * 机器人登录完成
     * */
    @EventHandler
    public ListeningStatus onBotOnlineEvent(BotOnlineEvent event) throws IOException {
        LogUtil.eventLog(event.toString(), "机器人登录完成");
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
