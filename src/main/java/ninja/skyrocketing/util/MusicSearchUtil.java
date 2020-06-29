package ninja.skyrocketing.util;

import cn.hutool.json.JSONObject;

import java.io.IOException;

public class MusicSearchUtil {
	public static String neteaseMusic(String str) throws IOException {
		return musicQuery(str, true);
	}

	public static String qqMusic(String str) throws IOException {
		return musicQuery(str, false);
	}

	public static String musicQuery(String str, boolean type) throws IOException {
		String apiUrl = "https://c.y.qq.com/soso/fcgi-bin/client_search_cp?p=1&n=1&format=json&w=";
		String mapPath = "data.song.list[0].songid";
		if (type) {
			apiUrl = "https://music.163.com/api/search/get/web?csrf_token=hlpretag=&hlposttag=&type=1&offset=0&total=true&limit=1&s=";
			mapPath = "result.songs[0].id";
		}
		String searchStr = HttpUtil.chnAndSpaceReplace(str);
		JSONObject json = HttpUtil.readJsonFromUrl(apiUrl + searchStr);
		Object result = json.getByPath(mapPath, Integer.class);
		if (result == null)
			return "🈚";
		return result.toString();
	}
}
