package ninja.skyrocketing.bot.fuyao;

import net.mamoe.mirai.Bot;
import ninja.skyrocketing.bot.fuyao.config.MiraiBotConfig;
import ninja.skyrocketing.bot.fuyao.pojo.bot.BotReplyMessage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@SpringBootApplication
@Configuration
@MapperScan("ninja.skyrocketing.bot.fuyao.mapper.*")
public class FuyaoBotApplication implements CommandLineRunner {
	//是否为开发环境
	public static boolean DevMode = false;

	public static List<BotReplyMessage> botReplyMessageList;

	//机器人实例
	public static Bot bot;

	//主函数，不运行Springboot的web模块
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(FuyaoBotApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run();
	}

	//覆盖run方法
	@Override
	public void run(String... args) throws Exception {
		//运行机器人
		MiraiBotConfig.RunBot(DevMode);

		// Spring线程阻塞
		Thread.currentThread().join();
	}
}
