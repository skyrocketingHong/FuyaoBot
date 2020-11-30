package ninja.skyrocketing.bot.fuyao.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-28 16:53:39
 */

public class RandomUtil {
    //生成0-seed之类的整数，不含seed
    public static int RandomNum(int seed) {
        Random random = new Random();
        return random.nextInt(seed);
    }

    //生成强加密随机数，实例默认构造函数不使用随机种子
    public static int SecureRandomNum(int min, int max) {
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.nextInt(max - min) + min;
    }
}
