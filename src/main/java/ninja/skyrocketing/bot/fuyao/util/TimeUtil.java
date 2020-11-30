package ninja.skyrocketing.bot.fuyao.util;

import cn.hutool.core.date.DateUtil;

import java.util.Date;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-28 20:08:20
 */
public class TimeUtil {
    public static String DateFormatter(Date date) {
        return DateUtil.format(date, "HH:mm:ss");
    }
}
