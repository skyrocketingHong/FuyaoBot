package ninja.skyrocketing.robot.message;

import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.robot.entity.CoolQMessage;
import ninja.skyrocketing.util.RegexUtil;
import ninja.skyrocketing.util.TimeUtil;

import java.util.List;

public class QueryMessage {
	public static Message releaseNote(CoolQMessage coolQMessage) {
		return coolQMessage.sendMsg(coolQMessage.getYamlFile().getNoteAndFunc().get("note"));
	}
	
	public static Message getFunction(CoolQMessage coolQMessage) {
		return coolQMessage.sendMsg(coolQMessage.getYamlFile().getNoteAndFunc().get("func"));
	}
	
	public static Message lastSeenTime(CoolQMessage coolQMessage) {
		List<String> queryId = RegexUtil.extractMessage("(^|\\d)([0-9]{6,13})($^|\\d)", coolQMessage.getMsg());
		if (!queryId.isEmpty()) {
			String[] lastSeenTime = TimeUtil.getDateTimeOfTimestamp(coolQMessage.getGroupMessageEvent().getTime()).toString().split("T");
			return coolQMessage.atSomeone( "\n" + queryId.get(0) + "的最后发言时间为\n" + lastSeenTime[0] + " " + lastSeenTime[1]);
		} else {
			return null;
		}
	}
}