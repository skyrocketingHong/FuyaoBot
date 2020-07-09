package ninja.skyrocketing.robot.sender;

import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.robot.entity.CoolQMessage;
import ninja.skyrocketing.robot.entity.YamlFile;
import ninja.skyrocketing.util.SenderUtil;

public class GroupMessageSender {
	public static Message Sender(GroupMessageEvent event, YamlFile yamlFile) throws Exception {
		CoolQMessage coolQMessage = new CoolQMessage(event, yamlFile);
		return SenderUtil.Sender(yamlFile, coolQMessage);
	}
}
