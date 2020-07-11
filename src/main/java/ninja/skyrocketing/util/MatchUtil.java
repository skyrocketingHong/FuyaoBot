package ninja.skyrocketing.util;

import ninja.skyrocketing.robot.entity.MessageEntity;
import ninja.skyrocketing.robot.entity.YamlFileEntity;

public class MatchUtil {
	public static String[] matchedMsgAndClass(String str) {
		return str.split("@");
	}
	
	public static String matchedClass(MessageEntity messageEntity) {
		YamlFileEntity yamlFileEntity = messageEntity.getYamlFile();
		String msg = messageEntity.getMsg();
		if (yamlFileEntity.getReplyEqualsMap().containsKey(msg)) {
			return yamlFileEntity.getReplyEqualsMap().get(msg);
		} else {
			for (int i = 0; i < yamlFileEntity.getMatchesList().size(); i++) {
				String[] matchedMsgAndClass = matchedMsgAndClass(yamlFileEntity.getMatchesList().get(i));
				if (msg.matches(matchedMsgAndClass[0])) {
					return matchedMsgAndClass[1];
				}
			}
		}
		return null;
	}
}
