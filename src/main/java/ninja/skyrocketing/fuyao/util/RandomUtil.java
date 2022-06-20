package ninja.skyrocketing.fuyao.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author skyrocketing Hong
 * @date 2020-11-28 16:53:39
 */

public class RandomUtil {
    //生成[min - max)之内的强加密随机数，实例默认构造函数不使用随机种子。
    public static int secureRandomNum(int min, int max) throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        return secureRandom.nextInt(max - min) + min;
    }
    
    //生成[0 - max)之内的强加密随机数，实例默认构造函数不使用随机种子。
    public static int secureRandomNum(int max) throws NoSuchAlgorithmException {
        return secureRandomNum(0, max);
    }
}
