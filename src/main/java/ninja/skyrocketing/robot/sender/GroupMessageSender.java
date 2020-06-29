package ninja.skyrocketing.robot.sender;

import cc.moecraft.icq.event.events.message.EventGroupMessage;
import ninja.skyrocketing.robot.pojo.CoolQMessage;
import ninja.skyrocketing.robot.pojo.YamlFile;
import ninja.skyrocketing.util.SenderUtil;

public class GroupMessageSender {
	public static String Sender(EventGroupMessage event, YamlFile yamlFile) throws Exception {
		CoolQMessage coolQMessage = new CoolQMessage(event.getMessage(), event.getGroupId(), event.getSenderId(), event, yamlFile);
		return SenderUtil.Sender(yamlFile, coolQMessage);
	}
}
