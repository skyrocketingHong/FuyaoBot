package ninja.skyrocketing.bot.fuyao;

import net.mamoe.mirai.Bot;
import ninja.skyrocketing.bot.fuyao.config.MiraiBotConfig;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupRepeaterMessage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@Configuration
@EnableScheduling
@MapperScan("ninja.skyrocketing.bot.fuyao.mapper.*")
//mvn package "-Dmaven.test.skip=true"
public class FuyaoBotApplication implements CommandLineRunner {
	//生成启动时间
	public static Date startDate = new Date();

	//是否为开发环境
	public static boolean DevMode = false;

	//全局复读消息变量
	public static Map<Long, GroupRepeaterMessage> GroupsRepeaterMessagesMap = new HashMap<>();

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
