package ninja.skyrocketing.robot.listener;

import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.robot.entity.YamlFileEntity;
import ninja.skyrocketing.robot.message.LoggerMessage;
import ninja.skyrocketing.robot.sender.GroupMessageSender;

public class GroupMessageListener extends SimpleListenerHost {
	static YamlFileEntity yamlFileEntity;
	
	public GroupMessageListener(YamlFileEntity file) {
		yamlFileEntity = file;
	}
	
	@EventHandler
	public void onMessage(GroupMessageEvent event) throws Exception {
		Message message = GroupMessageSender.Sender(event, yamlFileEntity);
		if (message != null) {
			LoggerMessage.logger(event, message, yamlFileEntity);
			event.getGroup().sendMessage(message);
		}
	}
}
