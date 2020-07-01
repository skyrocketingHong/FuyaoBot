package ninja.skyrocketing;

import cc.moecraft.icq.PicqBotX;
import cc.moecraft.icq.PicqConfig;
import ninja.skyrocketing.robot.listener.DiscussMessageListener;
import ninja.skyrocketing.robot.listener.GroupMessageListener;
import ninja.skyrocketing.robot.listener.PrivateMessageListener;
import ninja.skyrocketing.robot.pojo.YamlFile;
import ninja.skyrocketing.util.YamlUtil;

import java.io.IOException;

import static cc.moecraft.logger.environments.ColorSupportLevel.DISABLED;

//mvn package "-Dmaven.test.skip=true"
public class RunRobot {
	public static void main(String[] args) throws IOException {
		YamlFile yamlFile = new YamlFile((new YamlUtil()).getYaml());

		PicqConfig config = new PicqConfig(8082).setDebug(false).setColorSupportLevel(DISABLED).setLogPath("");
		PicqBotX bot = new PicqBotX(config);

		bot.addAccount("Bot01", "127.0.0.1", 8081);

		bot.getEventManager().registerListeners(
				new PrivateMessageListener(yamlFile),
				new GroupMessageListener(yamlFile),
				new DiscussMessageListener(yamlFile)
		);

		bot.startBot();

		bot.getAccountManager().refreshCache();
	}
}