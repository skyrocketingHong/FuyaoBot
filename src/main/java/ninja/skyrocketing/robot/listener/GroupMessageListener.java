package ninja.skyrocketing.robot.listener;

import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.robot.entity.BotConfig;
import ninja.skyrocketing.robot.sender.GroupMessageSender;
import org.springframework.stereotype.Service;

@Service
public class GroupMessageListener extends SimpleListenerHost {
	@EventHandler
	public ListeningStatus onMessage(GroupMessageEvent event) throws Exception {
		Message message = GroupMessageSender.Sender(event);
		if (BotConfig.getBannedGroups().contains(event.getGroup().getId()) &&
				BotConfig.getBannedUsers().contains(event.getSender().getId())) {
			return ListeningStatus.LISTENING;
		} else {
			if (message != null) {
				event.getGroup().sendMessage(message);
				Thread.sleep(1000);
			}
		}
		return ListeningStatus.LISTENING;
	}
}
