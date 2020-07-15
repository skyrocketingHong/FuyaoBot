package ninja.skyrocketing.robot.messages;

import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.robot.entity.BotConfig;
import ninja.skyrocketing.robot.entity.MessageEncapsulation;

public class AdminMessage {
	/**
	 * 刷新数据库缓存
	 * sudo refresh
	 **/
	public static Message refreshConfigFile(MessageEncapsulation messageEncapsulation) {
		if (BotConfig.getAdminUsers().contains(messageEncapsulation.getUserId())) {
			BotConfig.refresh();
			return messageEncapsulation.sendMsg("Refresh done.");
		} else {
			return messageEncapsulation.sendMsg(messageEncapsulation.getUserId() + " is not in the sudoers file. This incident will be reported.");
		}
	}
	
	/**
	 * 获取复读阈值
	 * sudo get random rate
	 **/
	public static Message showRandomRate(MessageEncapsulation messageEncapsulation) {
		if (BotConfig.getAdminUsers().contains(messageEncapsulation.getUserId())) {
			return messageEncapsulation.sendMsg("random rate: " + (100 - Long.parseLong(BotConfig.getConfigMap().get("random"))) / 100.00 + ".");
		} else {
			return messageEncapsulation.sendMsg(messageEncapsulation.getUserId() + " is not in the sudoers file. This incident will be reported.");
		}
	}
}
