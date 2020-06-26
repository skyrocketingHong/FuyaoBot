package ninja.skyrocketing.robot.listener;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;

import java.util.Map;

public class PrivateMessageListener extends IcqListener {
	static Map[] replyMaps;
	
	public PrivateMessageListener() {
	
	}
	
	public PrivateMessageListener(Map[] map) {
		replyMaps = map;
	}
	
	@EventHandler
	public static void onPMEvent(EventPrivateMessage event) {
	
	}
}
