package ninja.skyrocketing.robot;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.SystemDeviceInfoKt;
import ninja.skyrocketing.robot.entity.YamlFile;
import ninja.skyrocketing.robot.listener.GroupMessageListener;
import ninja.skyrocketing.robot.listener.PrivateMessageListener;
import ninja.skyrocketing.util.YamlUtil;

import java.io.File;
import java.io.IOException;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-03 003 11:52:14
 * @Version 1.0
 */
public class RobotApplication {
	public static Bot bot;
	static YamlFile yamlFile;
	public static Long QQ;
	public static String PASSWORD;
	
	static {
		try {
			yamlFile = new YamlFile((new YamlUtil()).getYaml());
		} catch (IOException e) {
			e.printStackTrace();
		}
		QQ = yamlFile.getBotQQ();
		PASSWORD = yamlFile.getBotPassword();
		// 机器人
		bot = BotFactoryJvm.newBot(QQ, PASSWORD, new BotConfiguration() {{
			// 设备缓存信息
			//setProtocol(MiraiProtocol.ANDROID_PHONE);
			setDeviceInfo(context -> SystemDeviceInfoKt.loadAsDeviceInfo(new File("deviceInfo.json"), context));
		}});
		// 登录
		bot.login();
	}
	
	//mvn package "-Dmaven.test.skip=true"
	public static void main(String[] args) {
		//事件监听器注册
		Events.registerEvents(bot, new GroupMessageListener(yamlFile));
		Events.registerEvents(bot, new PrivateMessageListener(yamlFile));
		
		//挂载该机器人的协程
		bot.join();
	}
	
}
