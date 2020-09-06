package ninja.skyrocketing.robot.messages;

import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.robot.entity.BotConfig;
import ninja.skyrocketing.robot.entity.MessageEncapsulation;
import ninja.skyrocketing.utils.MessageUtil;
import ninja.skyrocketing.utils.RandomUtil;

public class RepeaterMessage {
	/**
	 * kkjj
	 **/
	public static Message kkjj(MessageEncapsulation messageEncapsulation) {
		return kk(messageEncapsulation);
	}
	
	/**
	 * kk
	 **/
	public static Message kk(MessageEncapsulation messageEncapsulation) {
		return MessageUtil.StringToMessage("kk？gkd发！");
	}
	
	/**
	 * 人工智障
	 **/
	public static Message stupidAI(MessageEncapsulation messageEncapsulation) {
		int randomNum = RandomUtil.RandomNum(100);
		if (randomNum > Integer.parseInt(BotConfig.getConfigMap().get("random"))) {
			return MessageUtil.StringToMessage(messageEncapsulation.getMsg().replaceAll("吗[?？]?$", "！"));
		}
		return null;
	}
	
	/**
	 * 100%复读
	 **/
	public static Message repeaterCommand(MessageEncapsulation messageEncapsulation) {
		MessageChain messageChain =
				messageEncapsulation.getGroupId() == 1L ?
						messageEncapsulation.getFriendMessageEvent().getMessage() : messageEncapsulation.getGroupMessageEvent().getMessage();
		if (messageChain.size() <= 2) {
			String msg = messageChain.contentToString();
			if (msg.matches("复读")) {
				return null;
			}
			return new MessageChainBuilder().asMessageChain().plus(msg.replaceFirst("复读", ""));
		} else {
			MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
			for (int i = 2; i < messageChain.size(); i++) {
				System.out.println(messageChain.get(i));
				messageChainBuilder.append(messageChain.get(i));
			}
			return messageChainBuilder.asMessageChain();
		}
	}
}