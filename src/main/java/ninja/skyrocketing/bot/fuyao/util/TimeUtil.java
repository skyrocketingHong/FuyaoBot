package ninja.skyrocketing.bot.fuyao.util;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-28 20:08:20
 */
public class TimeUtil {
    //格式化时间
    public static String TimeFormatter(Date date) {
        return DateUtil.format(date, "HH:mm:ss.SSS");
    }

    //格式化日期
    public static String DateFormatter(Date date) {
        return DateUtil.format(date, "yyyy年MM月dd日");
    }

    //直接获取格式化后的当前时间
    public static String NowDateTime(Date date) {
        return DateFormatter(date) + " " + TimeFormatter(date);
    }
    public static String NowDateTime() {
        return NowDateTime(new Date());
    }

    //将日期时间作为文件名
    public static String DateTimeFileName() {
        return DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
    }

    //将日期作为文件名
    public static String DateFileName() {
        return DateUtil.format(new Date(), "yyyyMMdd");
    }
}
