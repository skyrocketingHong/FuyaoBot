package ninja.skyrocketing.robot.listener;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.RobotApplication;
import ninja.skyrocketing.robot.entity.BotConfig;
import ninja.skyrocketing.robot.sender.GroupMessageSender;
import org.jetbrains.annotations.NotNull;

import static ninja.skyrocketing.robot.sender.AdminListenerMessageSender.ErrorMessageSender;

public class GroupMessageListener extends SimpleListenerHost {
	@EventHandler
	public ListeningStatus onMessage(GroupMessageEvent event) throws Exception {
		if (BotConfig.getBannedGroups().contains(event.getGroup().getId()) ||
				BotConfig.getBannedUsers().contains(event.getSender().getId())) {
			return ListeningStatus.LISTENING;
		} else {
			Message message = GroupMessageSender.Sender(event);
			if (event.getMessage().toString().matches(".*\\[mirai:at:" + event.getBot().getId() + ",.*\\].*") &&
					!event.getMessage().toString().matches(".*\\[mirai:quote:\\d*,\\d*\\].*")) {
				event.getGroup().sendMessage("别@我，没做人工智障回复。\n" +
						"发送 \"get func\" 或 \"扶摇bot功能列表\" 获取功能列表\n" +
						"发送 \"get releasenote\" 或 \"扶摇bot更新日志\" 获取更新日志");
				return ListeningStatus.LISTENING;
			}
			if (message != null) {
				event.getGroup().sendMessage(message);
				return ListeningStatus.LISTENING;
			}
			
		}
		return ListeningStatus.LISTENING;
	}
	
	//处理事件处理时抛出的异常
	@Override
	public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
		ErrorMessageSender(context, exception, RobotApplication.bot);
	}
}
