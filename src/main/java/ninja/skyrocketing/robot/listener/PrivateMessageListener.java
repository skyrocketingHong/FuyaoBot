package ninja.skyrocketing.robot.listener;

import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.FriendMessageEvent;
import ninja.skyrocketing.robot.entity.YamlFile;
import ninja.skyrocketing.robot.sender.PrivateMessageSender;

public class PrivateMessageListener extends SimpleListenerHost {
	static YamlFile yamlFile;
	
	public PrivateMessageListener(YamlFile file) {
		yamlFile = file;
	}
	
	@EventHandler
	public void onMessage(FriendMessageEvent event) throws Exception {
		if (PrivateMessageSender.Sender(event, yamlFile) != null)
			event.getSender().sendMessage(PrivateMessageSender.Sender(event, yamlFile));
	}
}