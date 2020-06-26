package ninja.skyrocketing.util;

import ninja.skyrocketing.robot.pojo.YamlFile;

public class MatchUtil {
	public static String[] matchedMsgAndClass(String str) {
		return str.split("@");
	}
	public static String matchedClass(YamlFile yamlFile, String msg, Integer integer) {
		if (yamlFile.getReplyEqualsMap().containsKey(msg)) {
			return yamlFile.getReplyEqualsMap().get(msg);
		} else {
			for (int i = 0; i < yamlFile.getAllMatchList().size(); i++) {
				for (String s : yamlFile.getAllMatchList().get(i)){
					String[] matchedMsgAndClass = matchedMsgAndClass(s);
					if (i == 0 && msg.contains(matchedMsgAndClass[0]))
						return matchedMsgAndClass[1];
					if (i == 1 && msg.matches(matchedMsgAndClass[0]))
						return matchedMsgAndClass[1];
					if (i == 2 && msg.matches(matchedMsgAndClass[0]) && integer > 90)
						return matchedMsgAndClass[1];
				}
			}
		}
		return null;
	}
}
