package ninja.skyrocketing.utils;

import cn.hutool.json.JSONObject;

import java.io.IOException;

public class MusicSearchUtil {
	public static String neteaseMusic(String str) throws IOException {
		return musicQuery(str, true);
	}
	
	public static String qqMusic(String str) throws IOException {
		return musicQuery(str, false);
	}
	
	public static String musicQuery(String str, boolean is163) throws IOException {
		String musicSummary, jumpUrl, musicUrl, musicJpg, musicTitle, apiUrl, tag;
		String searchStr = HttpUtil.chnAndSpaceReplace(str);
		if (is163) {
			apiUrl = "https://music.163.com/api/search/get/web?csrf_token=hlpretag=&hlposttag=&type=1&offset=0&total=true&limit=1&s=";
			tag = "网易云音乐";
			
			JSONObject jsonMusic = HttpUtil.readJsonFromUrl(apiUrl + searchStr);
			musicTitle = jsonMusic.getByPath("result.songs[0].name", String.class);
			System.out.println(musicTitle);
			if (musicTitle == null) {
				return null;
			} else {
				String musicId = jsonMusic.getByPath("result.songs[0].id", String.class);
				musicSummary = jsonMusic.getByPath("result.songs[0].artists[0].name", String.class);
				jumpUrl = "http://music.163.com/song/" + musicId;
				musicUrl = "http://music.163.com/song/media/outer/url?id=" + musicId;
				//获取封面
				JSONObject jsonAlbum = HttpUtil.readJsonFromUrl("https://music.163.com/api/song/detail/?id=" + musicId + "&ids=%5B" + musicId + "%5D");
				musicJpg = jsonAlbum.getByPath("songs[0].album.picUrl", String.class);
			}
		} else {
			apiUrl = "https://c.y.qq.com/soso/fcgi-bin/client_search_cp?p=1&n=1&format=json&w=";
			tag = "QQ音乐";
			
			JSONObject jsonMusic = HttpUtil.readJsonFromUrl(apiUrl + searchStr);
			musicTitle = jsonMusic.getByPath("data.song.list[0].songname", String.class);
			if (musicTitle == null) {
				return null;
			} else {
				musicSummary = jsonMusic.getByPath("data.song.list[0].singer[0].name", String.class);
				jumpUrl = "https://i.y.qq.com/v8/playsong.html?songtype=0&songmid=" +
						jsonMusic.getByPath("data.song.list[0].songmid");
				musicUrl = "https://i.y.qq.com/v8/playsong.html?songtype=0&songmid=" +
						jsonMusic.getByPath("data.song.list[0].songmid");
				musicJpg = "https://y.gtimg.cn/music/photo_new/T002R150x150M000" +
						jsonMusic.getByPath("data.song.list[0].albummid") +
						".jpg?max_age=2592000";
			}
		}
		return "{\"app\":\"com.tencent.structmsg\",\"config\":{\"autosize\":true,\"forward\":true,\"type\":\"normal\"},\"desc\":\"音乐\",\"extra\":{\"app_type\":1},\"meta\":{\"music\":{\"action\":\"\",\"android_pkg_name\":\"\",\"app_type\":1,\"appid\":100497308,\"desc\":\"" + musicSummary + "\",\"jumpUrl\":\"" + jumpUrl + "\",\"musicUrl\":\"" + musicUrl + "\",\"preview\":\"" + musicJpg + "\",\"sourceMsgId\":\"0\",\"source_icon\":\"\",\"source_url\":\"\",\"tag\":\"" + tag + "\",\"title\":\"" + musicTitle + "\"}},\"prompt\":\"" + "[分享]" + musicTitle + "\",\"ver\":\"0.0.0.1\",\"view\":\"music\"}";
	}
}
