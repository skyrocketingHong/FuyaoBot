package ninja.skyrocketing;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.PicqConfig;
import cc.moecraft.icq.event.IcqListener;
import ninja.skyrocketing.robot.listener.GroupMessageListener;
import ninja.skyrocketing.robot.listener.PrivateMessageListener;
import ninja.skyrocketing.robot.pojo.YamlFile;
import ninja.skyrocketing.util.YamlUtil;

import java.io.IOException;

public class RunRobot {
	public RunRobot() {
	}
	
	public static void main(String[] args) throws IOException {
		YamlFile yamlFile = new YamlFile((new YamlUtil()).getYaml());
		
		PicqBotX bot = new PicqBotX((new PicqConfig(8082)).setDebug(true));
		bot.addAccount("Bot01", "127.0.0.1", 8081);
		bot.getEventManager().registerListeners(new PrivateMessageListener(yamlFile), new GroupMessageListener(yamlFile));
		bot.startBot();
	}
}