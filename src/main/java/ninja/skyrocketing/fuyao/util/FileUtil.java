package ninja.skyrocketing.fuyao.util;

import cn.hutool.http.HttpUtil;
import net.mamoe.mirai.message.data.Image;
import ninja.skyrocketing.fuyao.bot.config.MiraiBotConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.system.ApplicationHome;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * @author skyrocketing Hong
 * @date 2021-01-17 22:16:13
 */
public class FileUtil {
    //全局文件名分隔符
    public static String separator = File.separator;

    /**
     * 获取jar包的路径
     */
    public static String getPath() {
        ApplicationHome applicationHome = new ApplicationHome(FileUtil.class);
        File file = applicationHome.getDir();
        return file.getAbsolutePath();
    }
    
    /**
     * 根据QQ号下载头像并保存在jar包同级目录中
     * */
    public static File getAvatarImageFile(long qqId) throws IOException {
        //将QQ号和获取头像的链接拼接起来
        String avatarURL = "http://q1.qlogo.cn/g?b=qq&nk=" + qqId + "&s=640";
        //拼接头像的路径和文件名
        String avatarFilePath = MiraiBotConfig.JAR_PATH +
                separator + "cache" +
                separator + "Member Avatar" +
                separator + TimeUtil.dateFileName() +
                separator + qqId + ".jpg";
        //下载并保存头像。当头像存在时则不下载，直接返回
        File avatarImageFile = new File(avatarFilePath);
        HttpUtil.downloadFile(avatarURL, avatarImageFile);
        return avatarImageFile;
    }

    /** 图片id转链接
     * @param image Image Mirai类型图片
     */
    //http://gchat.qpic.cn/gchatpic_new/0/0-0-{图片ID}/0?term=2
    public static String imageIdToURL(Image image) {
        return "http://gchat.qpic.cn/gchatpic_new/0/0-0-" + image.getImageId().replaceAll("[-{}]|\\.jpg", "") + "/0?term=2";
    }

    /**
    * 图片横向拼接
    * */
    public static void jointPic(List<File> files, File path) throws IOException {
        int allWidth = 0;	//图片总宽度
        int allHeight = 0;	//图片总高度
        List<BufferedImage> imgs = new ArrayList<>();
        for(int i=0; i<files.size(); i++){
            imgs.add(ImageIO.read(files.get(i)));
            //横向
            if (i==0) {
                allHeight = imgs.get(0).getHeight();
            }
            allWidth += imgs.get(i).getWidth();
        }
        BufferedImage combined = new BufferedImage(allWidth, allHeight, BufferedImage.TYPE_INT_ARGB);
        //paint both images, preserving the alpha channels
        Graphics g = combined.getGraphics();
        //横向合成
        int width = 0;
        for (BufferedImage img : imgs) {
            g.drawImage(img, width, 0, null);
            width += img.getWidth();
        }
        ImageIO.write(combined, "png", path);
    }
    
    /**
     * 文本追加写入
     * */
    public static void contentWriter(String content, String logFilePath) {
        try {
            RandomAccessFile randomFile = new RandomAccessFile(logFilePath, "rw");
            long fileLength = randomFile.length();
            randomFile.seek(fileLength);
            randomFile.write(content.getBytes());
            randomFile.close();
        } catch (Exception e) {
            Logger log =  LoggerFactory.getLogger(FileUtil.class);
            log.error("文件路径不存在，错误详情: " + e.getMessage());
        }
    }
}
