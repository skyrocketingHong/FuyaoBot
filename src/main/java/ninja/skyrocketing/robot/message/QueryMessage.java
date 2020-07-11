package ninja.skyrocketing.robot.message;

import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.robot.entity.MessageEntity;
import ninja.skyrocketing.util.RegexUtil;
import ninja.skyrocketing.util.TimeUtil;

import java.util.List;

public class QueryMessage {
	public static Message releaseNote(MessageEntity messageEntity) {
		return messageEntity.sendMsg(messageEntity.getYamlFile().getNoteAndFunc().get("note"));
	}
	
	public static Message getFunction(MessageEntity messageEntity) {
		return messageEntity.sendMsg(messageEntity.getYamlFile().getNoteAndFunc().get("func"));
	}
	
	public static Message lastSeenTime(MessageEntity messageEntity) {
		List<String> queryId = RegexUtil.extractMessage("(^|\\d)([0-9]{6,13})($^|\\d)", messageEntity.getMsg());
		if (!queryId.isEmpty()) {
			String lastSeenTime = TimeUtil.reformatDateTimeOfTimestamp(messageEntity.getGroupMessageEvent().getTime());
			return messageEntity.atSomeone("\n" +
					messageEntity.getGroupMessageEvent().getSender().getNameCard() +
					" (" +
					queryId.get(0) +
					") " +
					"的最后发言时间为\n" +
					lastSeenTime
			);
		} else {
			return null;
		}
	}
}