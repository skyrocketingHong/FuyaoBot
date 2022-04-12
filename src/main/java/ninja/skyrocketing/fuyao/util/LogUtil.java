package ninja.skyrocketing.fuyao.util;

import ninja.skyrocketing.fuyao.bot.config.GlobalVariables;

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
    public static void messageLog(String message, Long id, boolean isGroup, String name) {
        //log文件路径
        String logFilePath = GlobalVariables.getGlobalVariables().getLogFilePath() + TimeUtil.dateFileName() + ".log";
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
        //写入文件
        FileUtil.contentWriter(content, logFilePath);
    }

    /** 机器人无消息发送时的日志
     * @param event String 事件
     * @param type String 类型
     */
    public static void eventLog(String event, String type) {
        //log文件路径
        String logFilePath = GlobalVariables.getGlobalVariables().getLogFilePath() + TimeUtil.dateFileName() + ".log";
        //构造字符串
        String content = "[" + TimeUtil.nowDateTime() + "]" + "\r\n" +
                "[事件类型] " + type + "\r\n" +
                "[事件内容] " + event + "\r\n\r\n";
        //写入文件
        FileUtil.contentWriter(content, logFilePath);
    }
}
