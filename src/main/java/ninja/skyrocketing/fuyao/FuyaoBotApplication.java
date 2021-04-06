package ninja.skyrocketing.fuyao;

import net.mamoe.mirai.Bot;
import ninja.skyrocketing.fuyao.bot.config.MiraiBotConfig;
import ninja.skyrocketing.fuyao.util.FileUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;

/**
 * @author skyrocketing Hong
 * mvn clean && mvn package "-Dmaven.test.skip=true"
 */

@SpringBootApplication
@Configuration
@EnableScheduling
@MapperScan("ninja.skyrocketing.fuyao.bot.mapper.*")
public class FuyaoBotApplication implements CommandLineRunner {
	/**
	 * 生成启动时间
	 * */
	public static Date startDate = new Date();

	/**
	 * 机器人实例
	 * */
	public static Bot bot;

	/**
	 * 主函数
	 * */
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(FuyaoBotApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run();
	}

	/**
	 * 覆盖run方法
	 * */
	@Override
	public void run(String... args) throws Exception {
		//运行机器人
		MiraiBotConfig.runBot(FileUtil.isDev());

		// Spring线程阻塞
		Thread.currentThread().join();
	}
}
