package ninja.skyrocketing.robot.listener;

import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.GroupMessageEvent;
import ninja.skyrocketing.robot.entity.YamlFile;
import ninja.skyrocketing.robot.sender.GroupMessageSender;

public class GroupMessageListener extends SimpleListenerHost {
	static YamlFile yamlFile;
	
	public GroupMessageListener(YamlFile file) {
		yamlFile = file;
	}
	
	@EventHandler
	public void onMessage(GroupMessageEvent event) throws Exception {
		if (GroupMessageSender.Sender(event, yamlFile) != null)
			event.getGroup().sendMessage(GroupMessageSender.Sender(event, yamlFile));
	}
}
