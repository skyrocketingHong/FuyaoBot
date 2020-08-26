package ninja.skyrocketing.utils;

import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.*;
import ninja.skyrocketing.RobotApplication;
import ninja.skyrocketing.robot.entity.BotConfig;
import ninja.skyrocketing.robot.entity.MessageEncapsulation;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @Author skyrocketing Hong
 * @Date 2020-08-22 022 18:54:26
 */
public class MessageUtil {
	//当群名片为空时返回昵称
	public static String getNameOfMember(Member member) {
		return member.getNameCard().isEmpty() ? member.getNick() : member.getNameCard();
	}
	
	//@群员
	public static Message atSomeone(String resultMsg, MessageEncapsulation messageEncapsulation) {
		if (messageEncapsulation.getGroupId() == 1L) {
			MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
			messageChainBuilder.add("@" + messageEncapsulation.getFriendMessageEvent().getSender().getNick());
			return messageChainBuilder.asMessageChain().plus(resultMsg);
		}
		return new At(messageEncapsulation.getGroupMessageEvent().getSender()).plus(resultMsg);
	}
	
	//String转Message
	public static Message stringToMessage(String resultMsg) {
		MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
		messageChainBuilder.add(resultMsg);
		return messageChainBuilder.asMessageChain();
	}
	
	//非管理员用户提示
	public static Message notSudo(MessageEncapsulation messageEncapsulation) {
		return new PlainText(messageEncapsulation.getGroupId() + " is not in the sudoers file. This incident will be reported.");
	}
	
	//等待API返回数据
	public static MessageReceipt<Contact> waitingForAPI(MessageEncapsulation messageEncapsulation) {
		return messageEncapsulation.getGroupId() == 1L ?
				messageEncapsulation.getFriendMessageEvent().getSender().sendMessage("等待API返回数据...") :
				messageEncapsulation.getGroupMessageEvent().getGroup().sendMessage("等待API返回数据...");
	}
	
	//获取所有功能图片
	public static Image getFunctionImage() throws MalformedURLException {
		return RobotApplication.bot.getSelfQQ().uploadImage(new URL(BotConfig.getConfigMap().get("func_image")));
	}
}
