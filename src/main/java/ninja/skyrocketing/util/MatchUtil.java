package ninja.skyrocketing.util;

import ninja.skyrocketing.robot.entity.CoolQMessage;
import ninja.skyrocketing.robot.entity.YamlFile;

public class MatchUtil {
	public static String[] matchedMsgAndClass(String str) {
		return str.split("@");
	}

	public static String matchedClass(CoolQMessage coolQMessage) {
		YamlFile yamlFile = coolQMessage.getYamlFile();
		String msg = coolQMessage.getMsg();
		if (yamlFile.getReplyEqualsMap().containsKey(msg)) {
			return yamlFile.getReplyEqualsMap().get(msg);
		} else {
			for (int i = 1; i < yamlFile.getMatchesList().size(); i++) {
				String[] matchedMsgAndClass = matchedMsgAndClass(yamlFile.getMatchesList().get(i));
				if (msg.matches(matchedMsgAndClass[0])) {
					return matchedMsgAndClass[1];
				}
			}
		}
		return null;
	}
}
