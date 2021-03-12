package ninja.skyrocketing.bot.fuyao.util;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import net.mamoe.mirai.message.data.MusicKind;
import net.mamoe.mirai.message.data.MusicShare;
import ninja.skyrocketing.bot.fuyao.FuyaoBotApplication;

import java.io.IOException;

/**
 * @Author skyrocketing Hong
 * @Date 2021-03-12 09:10:49
 */

public class MusicSearchUtil {
    public static MusicShare NeteaseMusic(String str) throws IOException {
        return MusicQuery(str, true);
    }

    public static MusicShare QQMusic(String str) throws IOException {
        return MusicQuery(str, false);
    }

    public static MusicShare MusicQuery(String str, boolean is163) throws IOException {
        MusicKind musicKind;
        String musicSummary, jumpUrl, musicUrl, musicJpg, musicTitle, apiUrl;
        String searchStr = HttpUtil.CHNCharterAndSpaceReplace(str);
        if (is163) {
            apiUrl = "https://music.163.com/api/search/get/web?csrf_token=hlpretag=&hlposttag=&type=1&offset=0&total=true&limit=1&s=";
            JSONObject jsonMusic = HttpUtil.ReadJsonFromURL(apiUrl + searchStr);
            musicTitle = jsonMusic.getByPath("result.songs[0].name", String.class);
            System.out.println(musicTitle);
            if (musicTitle == null) {
                return null;
            } else {
                String musicId = jsonMusic.getByPath("result.songs[0].id", String.class);
                musicSummary = jsonMusic.getByPath("result.songs[0].artists[0].name", String.class);
                jumpUrl = "http://music.163.com/song/" + musicId;
                musicUrl = "http://music.163.com/song/media/outer/url?id=" + musicId;
                // 获取封面
                JSONObject jsonAlbum = HttpUtil.ReadJsonFromURL("https://music.163.com/api/song/detail/?id=" + musicId + "&ids=%5B" + musicId + "%5D");
                musicJpg = jsonAlbum.getByPath("songs[0].album.picUrl", String.class);
            }
            musicKind = MusicKind.NeteaseCloudMusic;
        } else {
            apiUrl = "https://c.y.qq.com/soso/fcgi-bin/client_search_cp?p=1&n=1&format=json&w=";
            JSONObject jsonMusic = HttpUtil.ReadJsonFromURL(apiUrl + searchStr);
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
            musicKind = MusicKind.QQMusic;
        }
        return new MusicShare(
                musicKind, musicTitle, musicSummary, jumpUrl, musicJpg, musicUrl, "[分享]" + musicSummary + " - " + musicTitle
        );
    }
}
