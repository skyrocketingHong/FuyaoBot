package ninja.skyrocketing.util;

import ninja.skyrocketing.robot.pojo.YamlFile;

public class MatchUtil {
	public static String[] matchedMsgAndClass(String str) {
		return str.split("@");
	}

	public static String matchedClass(YamlFile yamlFile, String msg) {
		if (yamlFile.getReplyEqualsMap().containsKey(msg)) {
			return yamlFile.getReplyEqualsMap().get(msg);
		} else {
			for (int i = 0; i < yamlFile.getAllMatchList().size(); i++) {
				for (String s : yamlFile.getAllMatchList().get(i)) {
					String[] matchedMsgAndClass = matchedMsgAndClass(s);
					if (msg.contains(matchedMsgAndClass[0])) {
						if (i == 0)
							return matchedMsgAndClass[1];
					}
					if (msg.matches(matchedMsgAndClass[0])) {
						if (i == 1)
							return matchedMsgAndClass[1];
						if (i == 2) {
							int randomNum = RandomUtil.getRandomNum(100);
							if (randomNum > 90)
								return matchedMsgAndClass[1];
						}
					}
				}
			}
		}
		return null;
	}
}
