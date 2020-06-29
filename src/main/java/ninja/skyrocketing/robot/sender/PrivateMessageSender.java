package ninja.skyrocketing.robot.sender;

import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import ninja.skyrocketing.robot.pojo.CoolQMessage;
import ninja.skyrocketing.robot.pojo.YamlFile;
import ninja.skyrocketing.util.SenderUtil;

public class PrivateMessage {
	public static String Sender(EventPrivateMessage event, YamlFile yamlFile) throws Exception {
		CoolQMessage coolQMessage = new CoolQMessage(event.getMessage(), event.getSenderId(), event, yamlFile);
		return SenderUtil.Sender(yamlFile, coolQMessage);
	}
}
