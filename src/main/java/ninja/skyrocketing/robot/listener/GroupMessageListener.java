package ninja.skyrocketing.robot.listener;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import ninja.skyrocketing.robot.message.EverywhereMessage;
import ninja.skyrocketing.robot.pojo.YamlFile;

public class GroupMessageListener extends IcqListener {
	static YamlFile yamlFile;
	
	public GroupMessageListener(YamlFile file) {
		yamlFile = file;
	}
	
	@EventHandler
	public void onGroupEvent(EventGroupMessage event) throws Exception {
		event.respond(EverywhereMessage.Sender(event, yamlFile));
	}
}
