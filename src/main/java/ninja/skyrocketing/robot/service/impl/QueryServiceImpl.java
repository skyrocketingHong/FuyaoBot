package ninja.skyrocketing.robot.service.impl;

import ninja.skyrocketing.robot.pojo.CoolQMessage;
import ninja.skyrocketing.util.RegexUtil;
import ninja.skyrocketing.util.TimeUtil;

import java.util.List;

public class QueryServiceImpl {
	public static String releaseNote(CoolQMessage coolQMessage) {
		return coolQMessage.getYamlFile().getNoteAndFunc().get("note");
	}
	
	public static String getFunction(CoolQMessage coolQMessage) {
		return coolQMessage.getYamlFile().getNoteAndFunc().get("func");
	}
	
	public static String lastSeenTime(CoolQMessage coolQMessage) {
		List<String> queryId = RegexUtil.extractMessage("(^|\\d)([0-9]{6,13})($^|\\d)", coolQMessage.getMsg());
		if (!queryId.isEmpty()) {
			String[] lastSeenTime = TimeUtil.getDateTimeOfTimestamp(coolQMessage.getEventG().getTime()).toString().split("T");
			return coolQMessage.atSomeone() + "\n" + queryId.get(0) + "的最后发言时间为\n" + lastSeenTime[0] + " " + lastSeenTime[1];
		} else {
			return null;
		}
	}
}