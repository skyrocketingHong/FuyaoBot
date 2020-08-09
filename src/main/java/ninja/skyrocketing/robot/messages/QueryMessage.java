package ninja.skyrocketing.robot.messages;

import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import net.mamoe.mirai.message.data.SingleMessage;
import ninja.skyrocketing.robot.entity.BotConfig;
import ninja.skyrocketing.robot.entity.MessageEncapsulation;

public class QueryMessage {
	/**
	 * 更新日志
	 **/
	public static Message releaseNote(MessageEncapsulation messageEntity) {
		return messageEntity.sendMsg("更新日志链接（自己的博客）：\n" + "https://skyrocketing.ninja/2020/qqbot-releasenotes/");
	}
	
	/**
	 * 普通功能列表
	 **/
	public static Message getFunctionList(MessageEncapsulation messageEntity) {
		return MessageUtils.newImage(BotConfig.getConfigMap().get("func_image"));
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