package ninja.skyrocketing.robot.message;

import net.mamoe.mirai.message.data.LightApp;
import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.robot.entity.CoolQMessage;
import ninja.skyrocketing.util.MusicSearchUtil;

import java.io.IOException;

public class MusicServiceMessage {
	public static Message neteaseMusic(CoolQMessage coolQMessage) throws IOException {
		String json = MusicSearchUtil.neteaseMusic(coolQMessage.getMsg().replaceAll("^网易云点歌\\s*", ""));
		return new LightApp(json);
	}
	public static Message qqMusic(CoolQMessage coolQMessage) throws IOException {
		String json = MusicSearchUtil.qqMusic(coolQMessage.getMsg().replaceAll("^(QQ音乐|qq音乐|QQ|qq|Q音|q音)*点歌\\s*|^来.*首\\s*", ""));
		return new LightApp(json);
	}
}
