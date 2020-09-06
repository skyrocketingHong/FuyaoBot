package ninja.skyrocketing.utils;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpUtil {
	private static String ReadAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
	
	public static JSONObject ReadJsonFromUrl(String url) throws IOException, JSONException {
		try (InputStream is = new URL(url).openStream()) {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
			String jsonText = ReadAll(rd);
			return new JSONObject(jsonText);
		}
	}
	
	public static JSONArray ReadJsonArrayFromUrl(String url) throws IOException, JSONException {
		try (InputStream is = new URL(url).openStream()) {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
			String jsonText = ReadAll(rd);
			return new JSONArray(jsonText);
		}
	}
	
	public static String ChnCharterReplace(String str) throws UnsupportedEncodingException {
		return java.net.URLEncoder.encode(str, "utf-8");
	}
	
	public static String SpaceReplace(String str) {
		return str.replaceAll("\\s", "+");
	}
	
	public static String ChnAndSpaceReplace(String str) throws UnsupportedEncodingException {
		return ChnCharterReplace(SpaceReplace(str));
	}
}
