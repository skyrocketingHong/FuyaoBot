package ninja.skyrocketing.robot.messages;

import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.SingleMessage;
import ninja.skyrocketing.robot.entity.MessageEncapsulation;
import ninja.skyrocketing.utils.MessageUtil;

import java.net.MalformedURLException;

public class QueryMessage {
	/**
	 * 更新日志
	 **/
	public static Message releaseNote(MessageEncapsulation messageEncapsulation) {
		return MessageUtil.stringToMessage("更新日志链接：\n" + "https://skyrocketing.ninja/2020/qqbot-releasenotes/");
	}
	
	/**
	 * 普通功能列表
	 **/
	public static Message getFunctionList(MessageEncapsulation messageEncapsulation) throws MalformedURLException {
		return MessageUtil.getFunctionImage();
	}
	
	/**
	 * 最后发言时间查询
	 **/
	public static Message lastSeenTime(MessageEncapsulation messageEncapsulation) {
		MessageChain msg = messageEncapsulation.getGroupMessageEvent().getMessage();
		String queryIdStr = "";
		for (SingleMessage singleMessage : msg) {
			if (singleMessage.toString().matches("\\[mirai:at:\\d*\\]")) {
				queryIdStr = singleMessage.toString();
			}
		}
		long queryId = Long.parseLong(queryIdStr.replaceAll("\\[mirai:at:|\\]", ""));
		messageEncapsulation.getGroupMessageEvent().getGroup().get(queryId).getId();
		
		return null;
	}
}