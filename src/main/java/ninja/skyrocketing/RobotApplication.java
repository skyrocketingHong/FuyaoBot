package ninja.skyrocketing;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.SystemDeviceInfoKt;
import ninja.skyrocketing.robot.dao.*;
import ninja.skyrocketing.robot.entity.BotConfig;
import ninja.skyrocketing.robot.listener.GroupMemberAdminEvent;
import ninja.skyrocketing.robot.listener.GroupMessageListener;
import ninja.skyrocketing.utils.TimeUtil;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;
import java.io.File;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-03 003 11:52:14
 * @Version 1.0
 */

@SpringBootApplication
public class RobotApplication implements CommandLineRunner {
	public static Bot bot;
	public static String startTime = TimeUtil.getDateTimeString();
	
	@Resource
	private ConfigDao configDao;
	@Resource
	private GroupIdDao groupIdDao;
	@Resource
	private MessageQueueDao messageQueueDao;
	@Resource
	private TriggerDao triggerDao;
	@Resource
	private UserExpDao userExpDao;
	@Resource
	private UserIdDao userIdDao;
	@Resource
	private OverwatchModeDao overwatchModeDao;
	@Resource
	private FuckDao fuckDao;
	
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(RobotApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run();
	}
	
	@Override
	public void run(String... args) throws Exception {
		new BotConfig(configDao, groupIdDao, messageQueueDao,
				triggerDao, userExpDao, userIdDao,
				overwatchModeDao, fuckDao);
		
		String qqPassword;
		long qqId;
		
		// 判断是否为开发环境
		if (DEV_STATUS.IS_DEV) {
			qqId = Long.parseLong(BotConfig.getConfigMap().get("bot_qq_dev"));
			qqPassword = BotConfig.getConfigMap().get("bot_qq_password_dev");
		} else {
			qqId = Long.parseLong(BotConfig.getConfigMap().get("bot_qq"));
			qqPassword = BotConfig.getConfigMap().get("bot_qq_password");
		}
		
		// 机器人
		bot = BotFactoryJvm.newBot(
				qqId,
				qqPassword,
				new BotConfiguration() {{
					// 设备缓存信息
					//setProtocol(MiraiProtocol.ANDROID_PHONE);
					setDeviceInfo(context -> SystemDeviceInfoKt.loadAsDeviceInfo(new File("deviceInfo.json"), context));
				}}
		);
		
		// 登录
		bot.login();
		
		// 注册监听事件
		Events.registerEvents(bot, new GroupMessageListener());
		Events.registerEvents(bot, new GroupMemberAdminEvent());
		
		// 发送启动成功提示消息
		String endTime = TimeUtil.getDateTimeString();
		for (Long groupId : BotConfig.getAdminGroups()) {
			bot.getGroup(groupId).sendMessage("[INFO] " + " 启动成功" + "\n" +
					"开始启动时间: " + startTime + "\n" +
					"完成启动时间: " + endTime + "\n" +
					"启动耗时: " + DateTime.of(startTime, "YYYY年MM月dd日 HH:mm:ss").between(DateTime.of(endTime, "YYYY年MM月dd日 HH:mm:ss"), DateUnit.SECOND) + "s"
			);
		}
		
		// 挂载该机器人的线程
		bot.join();
		
		// Spring线程阻塞
		Thread.currentThread().join();
	}
}
