package ninja.skyrocketing.utils;

import java.util.Random;

public class RandomUtil {
	public static int RandomNum(int seed) {
		Random random = new Random();
		return random.nextInt(seed) + 1;
	}
}
