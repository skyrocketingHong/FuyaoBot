package ninja.skyrocketing.robot.listener;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.FriendAddEvent;
import net.mamoe.mirai.event.events.NewFriendRequestEvent;
import ninja.skyrocketing.RobotApplication;
import org.jetbrains.annotations.NotNull;

import static ninja.skyrocketing.robot.sender.AdminListenerMessageSender.ErrorMessageSender;

/**
 * @Author skyrocketing Hong
 * @Date 2020-08-20 020 19:55:43
 */

public class FriendEventListener extends SimpleListenerHost {
	//自动同意加好友
	@EventHandler
	public ListeningStatus onAddingFriend(NewFriendRequestEvent event) {
		event.accept();
		return ListeningStatus.LISTENING;
	}
	
	//加好友后自动消息
	@EventHandler
	public ListeningStatus onAddFriend(FriendAddEvent event) {
		event.getFriend().sendMessage(
				"你好啊 \"" + event.getFriend().getNick() + "\"。\n" + "私聊同样可以触发功能哦，欢迎拉到别的群里去。"
		);
		return ListeningStatus.LISTENING;
	}
	
	//处理事件处理时抛出的异常
	@Override
	public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
		ErrorMessageSender(context, exception, RobotApplication.bot);
	}
}
