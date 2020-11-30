package ninja.skyrocketing.bot.fuyao;

import ninja.skyrocketing.bot.fuyao.util.RandomUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FuyaoBotApplicationTests {

	@Test
	void contextLoads() {

	}

	public static void main(String[] args) {
		for (int i = 0; i < 10; ++i) {
			System.out.println(RandomUtil.SecureRandomNum(0, 9999));
		}
	}

}
