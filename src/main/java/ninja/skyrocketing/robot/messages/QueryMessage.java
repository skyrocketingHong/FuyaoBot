package ninja.skyrocketing.robot.messages;

import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.SingleMessage;
import ninja.skyrocketing.robot.entity.BotConfig;
import ninja.skyrocketing.robot.entity.MessageEncapsulation;
import ninja.skyrocketing.robot.entity.datebase.Trigger;

public class QueryMessage {
	/**
	 * 更新日志
	 **/
	public static Message releaseNote(MessageEncapsulation messageEntity) {
		return messageEntity.sendMsg("更新日志链接（GitHub）：\n" + "https://github.com/skyrocketingHong/QQRobot/blob/master/README.md");
	}
	
	/**
	 * 普通功能列表
	 **/
	public static Message getFunctionList(MessageEncapsulation messageEntity) {
		MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
		int count = 0;
		messageChainBuilder.add("扶摇的憨憨机器人的功能列表\n（使用的时候不用带加号）\n");
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
	 **/
	public static Message lastSeenTime(MessageEncapsulation messageEntity) {
		MessageChain msg = messageEntity.getGroupMessageEvent().getMessage();
		String queryIdStr = "";
		for (SingleMessage singleMessage : msg) {
			if (singleMessage.toString().matches("\\[mirai:at:\\d*\\]")) {
				queryIdStr = singleMessage.toString();
			}
		}
		long queryId = Long.parseLong(queryIdStr.replaceAll("\\[mirai:at:|\\]", ""));
		messageEntity.getGroupMessageEvent().getGroup().get(queryId).getId();
		
		return null;
	}
}