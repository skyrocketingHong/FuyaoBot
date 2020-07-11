package ninja.skyrocketing.robot.message;

import net.mamoe.mirai.Bot;
import ninja.skyrocketing.robot.entity.YamlFileEntity;

import java.util.TimerTask;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-11 011 18:28:35
 * @Version 1.0
 */
public class TimerMessage extends TimerTask {
	static Bot botEntity;
	static YamlFileEntity yamlFileEntity;
	
	public TimerMessage(Bot bot, YamlFileEntity yaml) {
		botEntity = bot;
		yamlFileEntity = yaml;
	}
	
	@Override
	public void run() {
		int recallDelay = 1000 * 60;
		botEntity.getFriend(Long.parseLong(yamlFileEntity.getIdList().get("admingroup").get(0))).sendMessage("⚠ 机器人整点报时开始提醒 ⚠");
		for (String groupId : yamlFileEntity.getIdList().get("timegroup")) {
			botEntity.getGroup(Long.parseLong(groupId))
					.sendMessage("整点报时\n" +
							TimeMessage.time() + "\n" +
							"本消息将在 " + recallDelay + " ms 后撤回"
					)
					.recallIn(recallDelay);
		}
		
		try {
			Thread.sleep(10000);    //设置让它休眠一段时间，测试下一次运行时间是否还是整点
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
