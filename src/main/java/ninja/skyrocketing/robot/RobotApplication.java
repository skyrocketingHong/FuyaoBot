package ninja.skyrocketing.robot;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.utils.BotConfiguration;
import net.mamoe.mirai.utils.SystemDeviceInfoKt;
import ninja.skyrocketing.robot.entity.YamlFileEntity;
import ninja.skyrocketing.robot.listener.GroupMemberAdminEvent;
import ninja.skyrocketing.robot.listener.GroupMessageListener;
import ninja.skyrocketing.robot.listener.PrivateMessageListener;
import ninja.skyrocketing.util.TimeUtil;
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
	public static Long QQ;
	public static String PASSWORD;
	static YamlFileEntity yamlFileEntity;
	
	static {
		try {
			yamlFileEntity = new YamlFileEntity((new YamlUtil()).getYaml());
		} catch (IOException e) {
			e.printStackTrace();
		}
		QQ = yamlFileEntity.getBotQQ();
		PASSWORD = yamlFileEntity.getBotPassword();
		// 机器人
		bot = BotFactoryJvm.newBot(QQ, PASSWORD, new BotConfiguration() {
			{
				// 设备缓存信息
				//setProtocol(MiraiProtocol.ANDROID_PHONE);
				setDeviceInfo(context -> SystemDeviceInfoKt.loadAsDeviceInfo(new File("deviceInfo.json"), context));
			}
		});
		// 登录
		bot.login();
	}
	
	//mvn package "-Dmaven.test.skip=true"
	public static void main(String[] args) {
		//事件监听器注册
		Events.registerEvents(bot, new GroupMessageListener(yamlFileEntity));
		Events.registerEvents(bot, new PrivateMessageListener(yamlFileEntity));
		Events.registerEvents(bot, new GroupMemberAdminEvent(yamlFileEntity));
		
		//启动成功后给管理群发消息
		bot.getGroup(Long.parseLong(yamlFileEntity.getIdList().get("admingroup").get(0)))
				.sendMessage(
						"⚠ 机器人启动提醒 ⚠" + "\n" +
								"① 启动时间：" + TimeUtil.getDateTimeString()
				);
		
		//挂载该机器人的线程
		bot.join();
	}
}
