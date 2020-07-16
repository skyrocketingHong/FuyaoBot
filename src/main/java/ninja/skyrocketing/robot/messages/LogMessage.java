package ninja.skyrocketing.robot.messages;

import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.robot.entity.BotConfig;
import ninja.skyrocketing.robot.entity.MessageEncapsulation;
import ninja.skyrocketing.utils.TimeUtil;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-14 014 22:31:33
 * @Version 1.0
 */
public class LogMessage {
	public static MessageChainBuilder logMessage(String level, MessageEncapsulation... messageEncapsulation) {
		MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
		messageChainBuilder.add("[" + level + "] ");
		if (level.equals("FATAL")) {
			/**
			 * 机器人被禁言、踢出、撤回
			 * **/
			messageChainBuilder.add("机器人相关事件: ");
			return messageChainBuilder;
		} else if (level.equals("ERROR")) {
			/**
			 * 红包、闪照
			 * **/
			messageChainBuilder.add("红包、闪照消息\n");
			messageChainBuilder.add("0. 时间: " + TimeUtil.reformatDateTimeOfTimestamp(messageEncapsulation[0].getGroupMessageEvent().getTime()) + "\n" +
					"1. 发送群: " + messageEncapsulation[0].getGroupMessageEvent().getGroup().getName() + "\n" +
					"2. 发送群群号: " + messageEncapsulation[0].getGroupMessageEvent().getGroup().getId() + "\n" +
					"3. 发送人: " + messageEncapsulation[0].getGroupMessageEvent().getSender().getNameCard() + "\n" +
					"4. 发送人QQ号: " + messageEncapsulation[0].getGroupMessageEvent().getSender().getId() + "\n"
			);
			return messageChainBuilder;
		} else if (level.equals("WARN")) {
			/**
			 * 机器人被触发要求复读消息
			 * **/
			messageChainBuilder.add("复读指令被触发\n");
			messageChainBuilder.add("0. 时间" + TimeUtil.reformatDateTimeOfTimestamp(messageEncapsulation[0].getGroupMessageEvent().getTime()) + "\n" +
					"1. 触发群: " + messageEncapsulation[0].getGroupMessageEvent().getGroup().getName() + "\n" +
					"2. 触发群群号: " + messageEncapsulation[0].getGroupMessageEvent().getGroup().getId() + "\n" +
					"3. 触发人: " + messageEncapsulation[0].getGroupMessageEvent().getSender().getNameCard() + "\n" +
					"4. 触发人QQ号: " + messageEncapsulation[0].getGroupMessageEvent().getSender().getId() + "\n" +
					"5. 触发消息: " + messageEncapsulation[0].getGroupMessageEvent().getMessage().toString().replaceAll("\\s|\\n", " ")
			);
			for (Long id : BotConfig.getAdminGroups()) {
				messageEncapsulation[0].getGroupMessageEvent().getBot().getGroup(id).sendMessage(messageChainBuilder.asMessageChain());
			}
			return messageChainBuilder;
		} else if (level.equals("INFO")) {
			/**
			 * 机器人功能被触发
			 * **/
			messageChainBuilder.add("机器人功能被触发\n");
			messageChainBuilder.add("0. 时间" + TimeUtil.reformatDateTimeOfTimestamp(messageEncapsulation[0].getGroupMessageEvent().getTime()) + "\n" +
					"1. 触发群: " + messageEncapsulation[0].getGroupMessageEvent().getGroup().getName() + "\n" +
					"2. 触发群群号: " + messageEncapsulation[0].getGroupMessageEvent().getGroup().getId() + "\n" +
					"3. 触发人: " + messageEncapsulation[0].getGroupMessageEvent().getSender().getNameCard() + "\n" +
					"4. 触发人QQ号: " + messageEncapsulation[0].getGroupMessageEvent().getSender().getId() + "\n" +
					"5. 触发消息: " + messageEncapsulation[0].getGroupMessageEvent().getMessage().contentToString().replaceAll("\\s|\\n", " ")
			);
			for (Long id : BotConfig.getAdminGroups()) {
				messageEncapsulation[0].getGroupMessageEvent().getBot().getGroup(id).sendMessage(messageChainBuilder.asMessageChain());
			}
			return messageChainBuilder;
		} else {
			messageChainBuilder.add("UNKNOWN TYPE");
			return messageChainBuilder;
		}
	}
}
