package ninja.skyrocketing.util;

import java.util.Random;

public class RandomUtil {
	public static int getRandomNum(int seed) {
		Random random = new Random();
		return random.nextInt(seed) + 1;
	}
}
