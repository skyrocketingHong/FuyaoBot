package ninja.skyrocketing.bot.fuyao.util;

import cn.hutool.http.HttpUtil;
import net.mamoe.mirai.message.data.Image;
import org.springframework.boot.system.ApplicationHome;

import java.io.File;
import java.io.IOException;

/**
 * @Author skyrocketing Hong
 * @Date 2021-01-17 22:16:13
 */
public class FileUtil {
    static String separator = File.separator;

    //获取jar包的路径
    public static String GetPath() {
        ApplicationHome applicationHome = new ApplicationHome(FileUtil.class);
        File file = applicationHome.getDir();
        return file.getAbsolutePath();
    }

    //根据QQ号下载头像并保存在jar包同级目录中
    public static File GetAvatarImageFile(long qqId) throws IOException {
        //将QQ号和获取头像的链接拼接起来
        String avatarURL = "http://q1.qlogo.cn/g?b=qq&nk=" + qqId + "&s=640";
        //拼接头像的路径和文件名
        String avatarFilePath = GetPath() +
                separator + "cache" +
                separator + "Member Avatar" +
                separator + "qqId" + ".jpg";
        //下载并保存头像。当头像存在时则不下载，直接返回
        File avatarImageFile = new File(avatarFilePath);
        HttpUtil.downloadFile(avatarURL, avatarImageFile);
        return avatarImageFile;
    }

    //图片id转链接
    //http://gchat.qpic.cn/gchatpic_new/0/0-0-{图片ID}/0?term=2
    public static String ImageIdToURL(Image image) {
        return "http://gchat.qpic.cn/gchatpic_new/0/0-0-" + image.getImageId().replaceAll("[-{}]|\\.jpg", "") + "/0?term=2";
    }
}
