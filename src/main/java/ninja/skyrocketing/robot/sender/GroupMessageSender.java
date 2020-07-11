package ninja.skyrocketing.robot.sender;

import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.robot.entity.MessageEntity;
import ninja.skyrocketing.robot.entity.YamlFileEntity;
import ninja.skyrocketing.util.SenderUtil;

public class GroupMessageSender {
	public static Message Sender(GroupMessageEvent event, YamlFileEntity yamlFileEntity) throws Exception {
		MessageEntity messageEntity = new MessageEntity(event, yamlFileEntity);
		return SenderUtil.Sender(yamlFileEntity, messageEntity);
	}
}
