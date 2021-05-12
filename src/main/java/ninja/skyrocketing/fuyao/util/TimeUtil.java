package ninja.skyrocketing.fuyao.util;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * @author skyrocketing Hong
 * @date 2020-11-28 20:08:20
 */
public class TimeUtil {
    //格式化时间
    public static String timeFormatter(Date date) {
        return DateUtil.format(date, "HH:mm:ss.SSS");
    }

    //格式化日期
    public static String dateFormatter(Date date) {
        return DateUtil.format(date, "yyyy年MM月dd日");
    }

    //直接获取格式化后的当前时间
    public static String nowDateTime(Date date) {
        return dateFormatter(date) + " " + timeFormatter(date);
    }
    public static String nowDateTime() {
        return nowDateTime(new Date());
    }

    //将日期时间作为文件名
    public static String dateTimeFileName() {
        return DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
    }

    //将日期作为文件名
    public static String dateFileName() {
        return DateUtil.format(new Date(), "yyyyMMdd");
    }

    //获取Emoji时钟图标
    public static String getClockEmoji(int hour) {
        String[] clock = new String[]{"\ud83d\udd5b", "\ud83d\udd50", "\ud83d\udd51", "\ud83d\udd52", "\ud83d\udd53", "\ud83d\udd54", "\ud83d\udd55", "\ud83d\udd56", "\ud83d\udd57", "\ud83d\udd58", "\ud83d\udd59", "\ud83d\udd5a"};
        return clock[hour % 12];
    }

    //判断时间是否在时间段内
    public static boolean secondBetween(int second, int begin, int end) {
        return second >= begin && second <= end;
    }

    //返回各时间段对应的名称
    public static String getPartsOfADay(int second) {
        /*
         * 上午
         * 0:00-2:59    凌晨  0-10799
         * 3:00-5:59    拂晓  10800-21599
         * 6:00-9:59    早晨  21600-35999
         * 10:00-11:59  午前  36000-43199
         * 下午
         * 12:00-14:59  午后  43200-53999
         * 15:00-17:59  傍晚  54000-64799
         * 18:00-20:59  薄暮  64800-75599
         * 21:00-23:59  深夜  75600-86399
         */

        if (secondBetween(second, 0, 10799)) {
            return " 凌晨 ";
        } else if (secondBetween(second, 10800, 21599)) {
            return " 拂晓 ";
        } else if (secondBetween(second, 21600, 35999)) {
            return " 早晨 ";
        } else if (secondBetween(second, 36000, 43199)) {
            return " 午前 ";
        } else if (secondBetween(second, 43200, 53999)) {
            return " 午后 ";
        } else if (secondBetween(second, 54000, 64799)) {
            return " 傍晚 ";
        } else if (secondBetween(second, 54800, 75999)) {
            return " 薄暮 ";
        } else if (secondBetween(second, 75600, 86399)) {
            return " 深夜 ";
        } else {
            return "error";
        }
    }

    /**
    * 返回当前时间的时间戳
    * */
    public static Long getTimestamp() {
        return System.currentTimeMillis() / 1000;
    }
}
