package ninja.skyrocketing.robot.messages;

import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import ninja.skyrocketing.robot.entity.BotConfig;
import ninja.skyrocketing.robot.entity.MessageEncapsulation;
import ninja.skyrocketing.robot.entity.datebase.Trigger;

public class QueryMessage {
	/**
	 * 更新日志
	 * **/
//	public static Message releaseNote(MessageEncapsulation messageEntity) {
//		return messageEntity.sendMsg(messageEntity.getYamlFile().getNoteAndFunc().get("note"));
//	}
	
	/**
	 * 详细功能列表
	 **/
	public static Message getFunction(MessageEncapsulation messageEntity) {
		if (BotConfig.getAdminUsers().contains(messageEntity.getUserId())) {
			MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
			messageChainBuilder.add(BotConfig.getTriggers().toString());
			return messageChainBuilder.asMessageChain();
		} else {
			return new PlainText("非管理员，无法使用“" + messageEntity.getMsg() + "”命令！");
		}
	}
	
	/**
	 * 普通功能列表
	 **/
	public static Message getFunctionList(MessageEncapsulation messageEntity) {
		MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
		int count = 0;
		messageChainBuilder.add("扶摇的憨憨机器人的功能列表（使用的时候不用带加号）\n");
		for (Trigger trigger : BotConfig.getTriggers()) {
			if (trigger.isShown()) {
				count++;
				messageChainBuilder.add(count + ". " + trigger.getName() + ": " + trigger.getOperate() + "\n");
			}
		}
		return messageChainBuilder.asMessageChain();
	}
	
	/**
	 * 最后发言时间查询
	 * **/
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
}