package ninja.skyrocketing.robot.message;

import cn.hutool.json.JSONObject;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.robot.entity.MessageEntity;
import ninja.skyrocketing.util.HttpUtil;

import java.io.IOException;

public class QueryMessage {
	public static Message releaseNote(MessageEntity messageEntity) {
		return messageEntity.sendMsg(messageEntity.getYamlFile().getNoteAndFunc().get("note"));
	}
	
	public static Message getFunction(MessageEntity messageEntity) {
		return messageEntity.sendMsg(messageEntity.getYamlFile().getNoteAndFunc().get("func"));
	}
	
	public static Message getFunctionList(MessageEntity messageEntity) {
		return messageEntity.sendMsg(messageEntity.getYamlFile().getNoteAndFunc().get("list"));
	}

//	public static Message lastSeenTime(MessageEntity messageEntity) {
//		Long queryId = Long.parseLong(RegexUtil.extractMessage("(^|\\d)([0-9]{6,13})($^|\\d)", messageEntity.getMsg()).get(0));
//		System.out.println(queryId.toString());
//		if (queryId == null) {
//			String lastSeenTime = TimeUtil.reformatDateTimeOfTimestamp(messageEntity.getGroupMessageEvent().getGroup().get(queryId).get);
//			return messageEntity.atSomeone("\n" +
//					messageEntity.getGroupMessageEvent().getSender().getNameCard() +
//					" (" +
//					queryId.get(0) +
//					") " +
//					"的最后发言时间为\n" +
//					lastSeenTime
//			);
//		} else {
//			return null;
//		}
//	}
	
	public static Message getOverwatchArcadeModes(MessageEntity messageEntity) throws IOException {
		JSONObject owModes = HttpUtil.readJsonFromUrl("https://overwatcharcade.today/api/overwatch/today");
		MessageChainBuilder messages = new MessageChainBuilder();
		for (int i = 1; i < 8; i++) {
			messages.add(i + ". " + owModes.getByPath("modes.tile_" + i + ".name", String.class) + "\n");
		}
		return messages.asMessageChain();
	}
}