package ninja.skyrocketing.fuyao.util;

import ninja.skyrocketing.fuyao.bot.config.MiraiBotConfig;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author skyrocketing Hong
 * @date 2021-03-03 21:45:00
 */

public class LogUtil {
    /** 发送消息的日志
     * @param message String 消息
     * @param id Long 群号或用户号码
     * @param isGroup boolean 是否是群
     * @param name String 接收方的名字
     */
    public static void messageLog(String message, Long id, boolean isGroup, String name) throws IOException {
        //log文件路径
        String logFilePath = MiraiBotConfig.LOG_FILE + TimeUtil.dateFileName() + ".log";
        File logFile = new File(logFilePath);
        String type;
        //根据类型添加不同的信息
        if (isGroup) {
            type = "[接收方类型] QQ 群\r\n" + "[接收方名称] " + name + "\r\n";
        } else {
            type = "[接收方类型] QQ 好友\r\n" + "[接收方名称] \"" + name + "\"\r\n";
        }
        //构造字符串
        String content = "[" + TimeUtil.nowDateTime() + "]" + "\r\n" +
                type +
                "[接收方号码] " + id + "\r\n" +
                "[发送内容] " + message + "\r\n\r\n";
        RandomAccessFile randomFile = new RandomAccessFile(logFile, "rw");
        long fileLength = randomFile.length();
        randomFile.seek(fileLength);
        randomFile.write(content.getBytes());
        randomFile.close();
    }

    /** 机器人无消息发送时的日志
     * @param event String 事件
     * @param type String 类型
     */
    public static void eventLog(String event, String type) throws IOException {
        //log文件路径
        String logFilePath = MiraiBotConfig.LOG_FILE + TimeUtil.dateFileName() + ".log";
        File logFile = new File(logFilePath);
        //构造字符串
        String content = "[" + TimeUtil.nowDateTime() + "]" + "\r\n" +
                "[事件类型] " + type + "\r\n" +
                "[事件内容] " + event + "\r\n\r\n";
        RandomAccessFile randomFile = new RandomAccessFile(logFile, "rw");
        long fileLength = randomFile.length();
        randomFile.seek(fileLength);
        randomFile.write(content.getBytes());
        randomFile.close();
    }
}
