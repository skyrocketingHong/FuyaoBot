package ninja.skyrocketing.robot.messages;

import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.LightApp;
import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.robot.entity.MessageEncapsulation;
import ninja.skyrocketing.utils.MessageUtil;
import ninja.skyrocketing.utils.MusicSearchUtil;

import java.io.IOException;

public class MusicMessage {
	/**
	 * 网易云音乐
	 **/
	public static Message neteaseMusic(MessageEncapsulation messageEncapsulation) throws IOException {
		MessageReceipt<Contact> messageReceipt = MessageUtil.waitingForAPI(messageEncapsulation);
		String jsonResult = MusicSearchUtil.neteaseMusic(messageEncapsulation.getMsg().replaceAll("^网易云点歌\\s*", ""));
		messageReceipt.recall();
		if (jsonResult == null) {
			return MessageUtil.atSomeone("没有这首歌", messageEncapsulation);
		} else {
			return new LightApp(jsonResult);
		}
		
	}
	
	/**
	 * QQ音乐
	 **/
	public static Message qqMusic(MessageEncapsulation messageEncapsulation) throws IOException {
		MessageReceipt<Contact> messageReceipt = MessageUtil.waitingForAPI(messageEncapsulation);
		String jsonResult = MusicSearchUtil.qqMusic(messageEncapsulation.getMsg().replaceAll("^(QQ音乐|qq音乐|QQ|qq|Q音|q音|开始)*点歌\\s*|^来.*首\\s*", ""));
		messageReceipt.recall();
		if (jsonResult == null) {
			return MessageUtil.atSomeone("没有这首歌", messageEncapsulation);
		} else {
			return new LightApp(jsonResult);
		}
	}
}
