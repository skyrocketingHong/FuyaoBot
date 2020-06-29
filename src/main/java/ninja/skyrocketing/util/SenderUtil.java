package ninja.skyrocketing.util;

import ninja.skyrocketing.robot.pojo.CoolQMessage;
import ninja.skyrocketing.robot.pojo.YamlFile;

public class SenderUtil {
	public static String Sender(YamlFile yamlFile, CoolQMessage coolQMessage) throws Exception {
		boolean userInBlacklist = yamlFile.getIdList().get("user").contains(coolQMessage.getUserId().toString());
		if (!userInBlacklist) {
			String className = MatchUtil.matchedClass(coolQMessage);
			if (className != null) {
				return InvokeUtil.runByInvoke(className, coolQMessage);
			}
		}
		return null;
	}
}