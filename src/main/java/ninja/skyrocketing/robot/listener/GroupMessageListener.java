package ninja.skyrocketing.robot.listener;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import ninja.skyrocketing.robot.message.EverywhereMessage;
import ninja.skyrocketing.robot.pojo.YamlFile;

import java.time.LocalDateTime;

public class GroupMessageListener extends IcqListener {
	static YamlFile yamlFile;
	
	public GroupMessageListener(YamlFile file) {
		yamlFile = file;
	}
	
	@EventHandler
	public void onGroupEvent(EventGroupMessage event) throws Exception {
		String msg = EverywhereMessage.Sender(event, yamlFile);
		String now = LocalDateTime.now().toString();
		System.out.println("群聊 回复消息日志 [" + now + "] [userId:" + event.getSenderId() + " groupId:" + event.getGroupId() + " groupName:" + event.getGroup().getInfo().getGroupName() + " msg:" + event.getMessage() + " replyMsg:" + msg.replaceAll("\n|\r|\t", "") + "]");
		event.respond(msg);
	}
}
