package ninja.skyrocketing.bot.fuyao.util;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-28 028 20:08:20
 * @Version 1.0
 */
public class TimeUtil {
    public static String DateFormatter(Date date) {
        return DateUtil.format(date, "HH:mm:ss");
    }
}
