package ninja.skyrocketing.util;

import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpUtil {
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
	
	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		try (InputStream is = new URL(url).openStream()) {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
			String jsonText = readAll(rd);
			return new JSONObject(jsonText);
		}
	}
	
	public static String chnCharterReplace(String str) throws UnsupportedEncodingException {
		return java.net.URLEncoder.encode(str, "utf-8");
	}
	
	public static String spaceReplace(String str) {
		return str.replaceAll("\\s", "+");
	}
	
	public static String chnAndSpaceReplace(String str) throws UnsupportedEncodingException {
		return chnCharterReplace(spaceReplace(str));
	}
}
