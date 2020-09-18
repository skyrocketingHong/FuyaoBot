package ninja.skyrocketing.robot.listener;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.RobotApplication;
import ninja.skyrocketing.robot.entity.BotConfig;
import ninja.skyrocketing.robot.sender.FriendMessageSender;
import ninja.skyrocketing.utils.MessageUtil;
import org.jetbrains.annotations.NotNull;

import static ninja.skyrocketing.robot.sender.AdminListenerMessageSender.ErrorMessageSender;

/**
 * @Author skyrocketing Hong
 * @Date 2020-08-22 022 11:16:39
 */
public class FriendMessageListener extends SimpleListenerHost {
	@EventHandler
	public ListeningStatus onMessage(FriendMessageEvent event) throws Exception {
		if (!BotConfig.getBannedUsers().contains(event.getSender().getId())) {
			if (event.getMessage().contentToString().equals("get list func")) {
				event.getSender().sendMessage(MessageUtil.FunctionImage());
			} else if (event.getMessage().contentToString().matches("^(EXP|exp)排名$")) {
				event.getSender().sendMessage("EXP排名仅限群聊中使用");
			} else {
				Message message = FriendMessageSender.Sender(event);
				if (message != null) {
					event.getSender().sendMessage(message);
				} else if (event.getMessage().contentToString().matches("^戳一戳我$|^戳我$|^拍一拍我$|^戳自己$|^拍自己$")) {
					return ListeningStatus.LISTENING;
				} else {
					event.getSender().sendMessage("不要发没做的功能指令哦。\n" +
							"发送 \"get list func\" 获取功能列表\n" +
							"发送 \"get list releasenote\" 获取更新日志");
				}
			}
			return ListeningStatus.LISTENING;
		}
		return ListeningStatus.LISTENING;
	}
	
	//处理事件处理时抛出的异常
	@Override
	public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
		ErrorMessageSender(context, exception, RobotApplication.bot);
	}
}
