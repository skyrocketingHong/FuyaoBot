package ninja.skyrocketing.bot.fuyao.util;

import java.util.Random;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-28 028 16:53:39
 * @Version 1.0
 */

public class RandomUtil {
    public static int RandomNum(int seed) {
        Random random = new Random();
        return random.nextInt(seed) + 1;
    }
}
