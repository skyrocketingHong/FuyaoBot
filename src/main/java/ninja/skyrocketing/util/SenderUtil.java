package ninja.skyrocketing.util;

import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.robot.entity.CoolQMessage;
import ninja.skyrocketing.robot.entity.YamlFile;

public class SenderUtil {
	public static Message Sender(YamlFile yamlFile, CoolQMessage coolQMessage) throws Exception {
		boolean userInBlacklist = yamlFile.getIdList().get("baneduser").contains(coolQMessage.getUserId().toString());
		if (!userInBlacklist) {
			String className = MatchUtil.matchedClass(coolQMessage);
			if (className != null) {
				return InvokeUtil.runByInvoke(className, coolQMessage);
			}
		}
		return null;
	}
}