package ninja.skyrocketing.robot.service.impl;

import ninja.skyrocketing.robot.pojo.CoolQMessage;
import ninja.skyrocketing.util.MusicSearchUtil;

import java.io.IOException;

public class MusicServiceImpl {
	public static String neteaseMusic(CoolQMessage coolQMessage) throws IOException {
		String id = MusicSearchUtil.neteaseMusic(coolQMessage.getMsg().replaceAll("^网易云点歌\\s*", ""));
		return "[CQ:music,type=163,id=" + id + "]";
	}
	public static String qqMusic(CoolQMessage coolQMessage) throws IOException {
		String id = MusicSearchUtil.qqMusic(coolQMessage.getMsg().replaceAll("^(QQ音乐|qq音乐|QQ|qq|Q音|q音)*点歌\\s*|^来\\w*首\\s*", ""));
		return "[CQ:music,type=qq,id=" + id + "]";
	}
}
