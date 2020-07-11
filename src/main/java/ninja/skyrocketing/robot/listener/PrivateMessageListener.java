package ninja.skyrocketing.robot.listener;

import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.robot.entity.YamlFileEntity;
import ninja.skyrocketing.robot.sender.PrivateMessageSender;

public class PrivateMessageListener extends SimpleListenerHost {
	static YamlFileEntity yamlFileEntity;
	
	public PrivateMessageListener(YamlFileEntity file) {
		yamlFileEntity = file;
	}
	
	@EventHandler
	public void onMessage(FriendMessageEvent event) throws Exception {
		Message message = PrivateMessageSender.Sender(event, yamlFileEntity);
		if (message != null)
			event.getSender().sendMessage(message);
	}
}