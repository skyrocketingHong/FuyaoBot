package ninja.skyrocketing.robot.message;

import net.mamoe.mirai.message.data.LightApp;
import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.robot.entity.CoolQMessage;
import ninja.skyrocketing.util.MusicSearchUtil;

import java.io.IOException;

public class MusicMessage {
	public static Message neteaseMusic(CoolQMessage coolQMessage) throws IOException {
		String jsonResult = MusicSearchUtil.neteaseMusic(coolQMessage.getMsg().replaceAll("^网易云点歌\\s*", ""));
		if(jsonResult == null)
			return coolQMessage.atSomeone("没有这首歌");
		else
			return new LightApp(jsonResult);
	}
	public static Message qqMusic(CoolQMessage coolQMessage) throws IOException {
		String jsonResult = MusicSearchUtil.qqMusic(coolQMessage.getMsg().replaceAll("^(QQ音乐|qq音乐|QQ|qq|Q音|q音|开始)*点歌\\s*|^来.*首\\s*", ""));
		if(jsonResult == null)
			return coolQMessage.atSomeone("没有这首歌");
		else
			return new LightApp(jsonResult);
	}
}
