package ninja.skyrocketing.fuyao.util;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import ninja.skyrocketing.fuyao.bot.function.TimelyFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author skyrocketing Hong
 * @date 2021-03-05 16:36:51
 */

public class HttpUtil {
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromURL(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        }
    }

    public static JSONArray readJsonArrayFromUrl(String url) throws IOException, JSONException {
        try (InputStream is = new URL(url).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            return new JSONArray(jsonText);
        }
    }

    public static String chnCharterReplace(String str) throws UnsupportedEncodingException {
        return java.net.URLEncoder.encode(str, StandardCharsets.UTF_8);
    }

    public static String spaceReplace(String str) {
        return str.replaceAll("\\s", "+");
    }

    public static String chnCharterAndSpaceReplace(String str) throws UnsupportedEncodingException {
        return chnCharterReplace(spaceReplace(str));
    }
    
    /**
     * 获取RSS信息
     * */
    public static SyndFeed getRSSFeed(String rssUrl) {
        SyndFeed feed = null;
        int i = 0;
        while (i < 3 && feed == null) {
            try {
                feed = new SyndFeedInput().build(new XmlReader(new URL(rssUrl)));
            } catch (Exception e) {
                ++i;
                Logger log =  LoggerFactory.getLogger(TimelyFunction.class);
                log.error("获取 \"" + rssUrl + "\" 时出现错误，错误详情: " + e.getMessage());
            }
        }
        return feed;
    }
}
