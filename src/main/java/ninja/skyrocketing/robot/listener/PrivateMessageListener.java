package ninja.skyrocketing.robot.listener;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import ninja.skyrocketing.robot.message.EverywhereMessage;
import ninja.skyrocketing.robot.pojo.YamlFile;

public class PrivateMessageListener extends IcqListener {
	static YamlFile yamlFile;
	
	public PrivateMessageListener(YamlFile file) {
		yamlFile = file;
	}
	
	@EventHandler
	public static void onPMEvent(EventPrivateMessage event) throws Exception {
		event.respond(EverywhereMessage.Sender(event, yamlFile));
	}
}