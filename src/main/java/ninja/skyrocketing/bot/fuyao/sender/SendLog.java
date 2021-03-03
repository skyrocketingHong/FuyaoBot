package ninja.skyrocketing.bot.fuyao.sender;

import ninja.skyrocketing.bot.fuyao.util.FileUtil;
import ninja.skyrocketing.bot.fuyao.util.TimeUtil;

import java.io.*;

/**
 * @Author skyrocketing Hong
 * @Date 2021-03-03 21:45:00
 */
public class SendLog {
    public static void WriteLogToFile (String message, Long id) throws IOException {
        //log文件路径
        String logPath = FileUtil.GetPath() +
                FileUtil.separator + "cache" +
                FileUtil.separator + "log";
        File logFile = new File(logPath);
        String content = "[" + TimeUtil.NowDateTime() + "]" + "\r\n" +
                "[接收方号码] " + id + "\r\n" +
                "[发送内容] " + message + "\r\n\r\n";
        RandomAccessFile randomFile = new RandomAccessFile(logFile, "rw");
        long fileLength = randomFile.length();
        randomFile.seek(fileLength);
        randomFile.write(content.getBytes());
        randomFile.close();
    }
}
