package ninja.skyrocketing.robot.listener;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.robot.entity.BotConfig;
import ninja.skyrocketing.robot.sender.GroupMessageSender;
import org.jetbrains.annotations.NotNull;

public class GroupMessageListener extends SimpleListenerHost {
	@EventHandler
	public ListeningStatus onMessage(GroupMessageEvent event) throws Exception {
		Message message = GroupMessageSender.Sender(event);
		if (BotConfig.getBannedGroups().contains(event.getGroup().getId()) ||
				BotConfig.getBannedUsers().contains(event.getSender().getId())) {
			return ListeningStatus.LISTENING;
		} else {
			if (event.getMessage().toString().matches(".*\\[mirai:at:" + event.getBot().getId() + "\\].*") &&
					!event.getMessage().toString().matches(".*\\[mirai:quote:\\d*,\\d*\\].*")) {
				System.out.println(event.getMessage().toString());
				event.getGroup().sendMessage("@我是没用的\n发送 \"get list func\" 获取功能列表\n发送 \"get list releasenote\" 获取更新日志");
				return ListeningStatus.LISTENING;
			}
			if (message != null) {
				event.getGroup().sendMessage(message);
				return ListeningStatus.LISTENING;
			}
		}
		return ListeningStatus.LISTENING;
	}
	
	@Override
	public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
		// 处理事件处理时抛出的异常
		System.out.println(context + " " + exception);
	}
}
