package ninja.skyrocketing.bot.fuyao.util;

import cn.hutool.http.HttpUtil;
import org.springframework.boot.system.ApplicationHome;

import java.io.File;
import java.io.IOException;

/**
 * @Author skyrocketing Hong
 * @Date 2021-01-17 22:16:13
 */
public class FileUtil {
    //获取jar包的路径
    public static String GetPath() {
        ApplicationHome applicationHome = new ApplicationHome(FileUtil.class);
        File file = applicationHome.getDir();
        return file.getAbsolutePath();
    }

    //根据QQ号下载头像并保存在jar包同级目录中
    public static File GetAvatarImageFile(long qqId) throws IOException {
        //将QQ号和获取头像的链接拼接起来
        String avatarUrl = "http://q1.qlogo.cn/g?b=qq&nk=" + qqId + "&s=640";
        //拼接头像的路径和文件名
        String avatarFile = GetPath() + "\\Member avatar\\" + qqId + ".jpg";
        //下载并保存头像。当头像存在时则不下载，直接返回
        File avatarImage = new File(avatarFile);
        if (!avatarImage.exists()) {
            HttpUtil.downloadFile(avatarUrl, avatarImage);
        }
        return avatarImage;
    }
}
