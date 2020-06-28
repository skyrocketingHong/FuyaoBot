package ninja.skyrocketing.robot.listener;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import ninja.skyrocketing.robot.message.EverywhereMessage;
import ninja.skyrocketing.robot.pojo.YamlFile;

import java.time.LocalDateTime;

public class PrivateMessageListener extends IcqListener {
	static YamlFile yamlFile;
	
	public PrivateMessageListener(YamlFile file) {
		yamlFile = file;
	}
	
	@EventHandler
	public static void onPMEvent(EventPrivateMessage event) throws Exception {
		String msg = EverywhereMessage.Sender(event, yamlFile);
		String now = LocalDateTime.now().toString();
		System.out.println("私聊 回复消息日志 [" + now + "] [userId:" + event.getSenderId() + " msg:" + event.getMessage() + " replyMsg:" + msg.replaceAll("\n|\r|\t", "") + "]");
		event.respond(msg);
	}
}