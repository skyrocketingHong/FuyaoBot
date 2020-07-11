package ninja.skyrocketing.util;

import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.robot.entity.MessageEntity;
import ninja.skyrocketing.robot.entity.YamlFileEntity;

public class SenderUtil {
	public static Message Sender(YamlFileEntity yamlFileEntity, MessageEntity messageEntity) throws Exception {
		boolean userInBlacklist = yamlFileEntity.getIdList().get("baneduser").contains(messageEntity.getUserId().toString());
		if (!userInBlacklist) {
			String className = MatchUtil.matchedClass(messageEntity);
			if (className != null) {
				return InvokeUtil.runByInvoke(className, messageEntity);
			}
		}
		return null;
	}
}