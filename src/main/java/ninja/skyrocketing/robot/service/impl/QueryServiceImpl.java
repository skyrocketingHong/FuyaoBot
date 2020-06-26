package ninja.skyrocketing.robot.service.impl;

import ninja.skyrocketing.robot.pojo.CoolQMessage;
import ninja.skyrocketing.util.RegexUtil;
import ninja.skyrocketing.util.TimeUtil;

import java.util.List;

public class QueryServiceImpl {
	//更新日志
	public static String releaseNote(CoolQMessage coolQMessage) {
		String note = coolQMessage.getYamlFile().getNoteAndFunc().get("note");
		coolQMessage.sendGroupMessage(note);
		return note;
	}
	
	//获取功能
	public static String getFunction(CoolQMessage coolQMessage) {
		String func = coolQMessage.getYamlFile().getNoteAndFunc().get("func");
		coolQMessage.sendGroupMessage(func);
		return func;
	}
	
	//最后发言时间
	public static String lastSeenTime(CoolQMessage coolQMessage) {
		//(^|\d)([0-9]{6,13})($^|\d)
		//(^|lastSentTime=)([0-9]{6,13})($^|\d)
		List<String> queryId = RegexUtil.extractMessage("(^|\\d)([0-9]{6,13})($^|\\d)", coolQMessage.getMsg());
		if (!queryId.isEmpty()) {
			String[] lastSeenTime = TimeUtil.getDateTimeOfTimestamp(coolQMessage.getEvent().getTime()).toString().split("T");
			String resultMessage = coolQMessage.atSomeone() + "\n" + queryId.get(0) + "的最后发言时间为\n" + lastSeenTime[0] + " " + lastSeenTime[1];
			coolQMessage.sendGroupMessage(resultMessage);
			return resultMessage;
		}
		return null;
	}
}
