package ninja.skyrocketing.bot.fuyao;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.utils.BotConfiguration;
import ninja.skyrocketing.bot.fuyao.service.bot.BotConfigService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("ninja.skyrocketing.bot.fuyao.mapper")
public class FuyaoBotApplication implements CommandLineRunner {
	public static Bot bot;
	@Autowired
	private BotConfigService botConfigService;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(FuyaoBotApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run();
	}

	@Override
	public void run(String... args) throws Exception {
		return;
	}
}
