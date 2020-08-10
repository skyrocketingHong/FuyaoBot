package ninja.skyrocketing.robot.messages;

import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.LightApp;
import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.robot.entity.MessageEncapsulation;
import ninja.skyrocketing.utils.MusicSearchUtil;

import java.io.IOException;

public class MusicMessage {
	/**
	 * 网易云音乐
	 **/
	public static Message neteaseMusic(MessageEncapsulation messageEntity) throws IOException {
		MessageReceipt<Contact> messageReceipt = messageEntity.getGroupMessageEvent().getGroup().sendMessage("等待API返回数据...");
		String jsonResult = MusicSearchUtil.neteaseMusic(messageEntity.getMsg().replaceAll("^网易云点歌\\s*", ""));
		messageReceipt.recall();
		if (jsonResult == null) {
			return messageEntity.atSomeone("没有这首歌");
		} else {
			return new LightApp(jsonResult);
		}
		
	}
	
	/**
	 * QQ音乐
	 **/
	public static Message qqMusic(MessageEncapsulation messageEntity) throws IOException {
		MessageReceipt<Contact> messageReceipt = messageEntity.getGroupMessageEvent().getGroup().sendMessage("等待API返回数据...");
		String jsonResult = MusicSearchUtil.qqMusic(messageEntity.getMsg().replaceAll("^(QQ音乐|qq音乐|QQ|qq|Q音|q音|开始)*点歌\\s*|^来.*首\\s*", ""));
		messageReceipt.recall();
		if (jsonResult == null) {
			return messageEntity.atSomeone("没有这首歌");
		} else {
			return new LightApp(jsonResult);
		}
	}
}
