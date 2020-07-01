package ninja.skyrocketing.robot.sender;

import cc.moecraft.icq.event.events.message.EventDiscussMessage;
import ninja.skyrocketing.robot.pojo.CoolQMessage;
import ninja.skyrocketing.robot.pojo.YamlFile;
import ninja.skyrocketing.util.SenderUtil;

public class DiscussMessageSender {
	public static String Sender(EventDiscussMessage event, YamlFile yamlFile) throws Exception {
		CoolQMessage coolQMessage = new CoolQMessage(event, yamlFile);
		return SenderUtil.Sender(yamlFile, coolQMessage);
	}
}
