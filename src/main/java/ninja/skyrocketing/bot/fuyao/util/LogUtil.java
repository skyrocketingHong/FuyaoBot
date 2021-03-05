package ninja.skyrocketing.bot.fuyao.util;

import ninja.skyrocketing.bot.fuyao.config.MiraiBotConfig;
import ninja.skyrocketing.bot.fuyao.util.FileUtil;
import ninja.skyrocketing.bot.fuyao.util.TimeUtil;

import java.io.*;

/**
 * @Author skyrocketing Hong
 * @Date 2021-03-03 21:45:00
 */

public class LogUtil {
    /** 发送消息至群的日志
     * @param message String 消息
     * @param id Long QQ群号
     */
    public static void GroupMessageLog(String message, Long id) throws IOException {
        //log文件路径
        String content = "[" + TimeUtil.NowDateTime() + "]" + "\r\n" +
                "[接收方号码] " + id + "\r\n" +
                "[发送内容] " + message + "\r\n\r\n";
        RandomAccessFile randomFile = new RandomAccessFile(MiraiBotConfig.logFile, "rw");
        long fileLength = randomFile.length();
        randomFile.seek(fileLength);
        randomFile.write(content.getBytes());
        randomFile.close();
    }

    /** 机器人无消息发送时的日志
     * @param event String 事件
     * @param type String 类型
     */
    public static void GroupEventFile(String event, String type) throws IOException {
        //log文件路径
        String logPath = MiraiBotConfig.jarPath +
                FileUtil.separator + "cache" +
                FileUtil.separator + "log";
        File logFile = new File(logPath);
        String content = "[" + TimeUtil.NowDateTime() + "]" + "\r\n" +
                "[事件类型] " + type + "\r\n" +
                "[事件内容] " + event + "\r\n\r\n";
        RandomAccessFile randomFile = new RandomAccessFile(logFile, "rw");
        long fileLength = randomFile.length();
        randomFile.seek(fileLength);
        randomFile.write(content.getBytes());
        randomFile.close();
    }
}
