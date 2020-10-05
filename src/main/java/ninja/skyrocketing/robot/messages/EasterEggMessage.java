package ninja.skyrocketing.robot.messages;

import net.mamoe.mirai.message.data.FlashImage;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.robot.entity.BotConfig;
import ninja.skyrocketing.robot.entity.MessageEncapsulation;
import ninja.skyrocketing.utils.MessageUtil;

public class EasterEggMessage {
	/**
	 * 闪照转发和提醒
	 **/
	public static Message firePhoto(MessageEncapsulation messageEncapsulation) {
		//将闪照转为图片类型
		System.out.println(messageEncapsulation.getGroupMessageEvent().getMessage());
		Image flashImage = ((FlashImage) messageEncapsulation.getGroupMessageEvent().getMessage().get(1)).getImage();
		//生成消息
		MessageChainBuilder messages = LogMessage.logMessage("ERROR", messageEncapsulation);
		messages.add("5. 图片链接: " + messageEncapsulation.getGroupMessageEvent().getBot().queryImageUrl(flashImage) + "\n");
		//向群内转发闪照图片
		for (Long id : BotConfig.getFlashImageGroups()) {
//			messages.add(new At(messageEncapsulation.getGroupMessageEvent().getBot().getGroup(id).getOwner()));
			messageEncapsulation.getGroupMessageEvent().getBot().getGroup(id).sendMessage(messages.asMessageChain());
		}
		//生成群提醒
		return MessageUtil.AtSomeone("发了一张闪照，gkd来康康。", messageEncapsulation);
	}
	
	/**
	 * 红包提醒
	 **/
	public static Message RedEnvelope(MessageEncapsulation messageEncapsulation) {
		MessageChainBuilder messages = LogMessage.logMessage("ERROR", messageEncapsulation);
		messages.add("5. 红包来了，gkd！");
		for (Long id : BotConfig.getFlashImageGroups()) {
//			messages.add(new At(messageEncapsulation.getGroupMessageEvent().getBot().getGroup(id).getOwner()));
			messageEncapsulation.getGroupMessageEvent().getBot().getGroup(id).sendMessage(messages.asMessageChain());
		}
		return MessageUtil.AtSomeone("发了一个红包，gkd来白嫖。", messageEncapsulation);
	}
}