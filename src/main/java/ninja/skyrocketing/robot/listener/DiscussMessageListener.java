package ninja.skyrocketing.robot.listener;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventDiscussMessage;
import ninja.skyrocketing.robot.pojo.YamlFile;
import ninja.skyrocketing.robot.sender.DiscussMessageSender;

import java.time.LocalDateTime;

public class DiscussMessageListener extends IcqListener {
	static YamlFile yamlFile;
	
	public DiscussMessageListener(YamlFile file) {
		yamlFile = file;
	}
	
	@EventHandler
	public void onDiscussEvent(EventDiscussMessage event) throws Exception {
		String msg = DiscussMessageSender.Sender(event, yamlFile);
		String now = LocalDateTime.now().toString();
		System.out.println("讨论组 回复消息日志 [" + now + "] [userId:" + event.getSenderId() + " groupId:" + event.getGroup().getId() + " groupName:" + event.getGroup().getInfo().getGroupName() + " msg:" + event.getMessage() + " replyMsg:" + msg.replaceAll("\n|\r|\t", "") + "]");
		event.respond(msg);
	}
}
