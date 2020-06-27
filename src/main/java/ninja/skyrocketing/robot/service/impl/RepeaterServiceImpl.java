package ninja.skyrocketing.robot.service.impl;

import java.time.LocalTime;
import ninja.skyrocketing.robot.pojo.CoolQMessage;

public class RepeaterServiceImpl {
	public static String stupidAI(CoolQMessage coolQMessage) {
		return coolQMessage.getMsg().replaceAll("吗[?？]?$", "！");
	}
	
	public static String morningAndNight(CoolQMessage coolQMessage) {
		LocalTime now = LocalTime.now();
		return coolQMessage.getMsg().matches("^(晚安)(鸭|啊|哦|呀|安)?|安{1,2}$") ? coolQMessage.atSomeone() + "\n晚安哦\nおやすみ" : coolQMessage.atSomeone() + "\n早啊\nおはよう";
	}
	
	public static String justThis(CoolQMessage coolQMessage) {
		return coolQMessage.getMsg();
	}
	
	public static String randomRepeater(CoolQMessage coolQMessage) {
		return coolQMessage.getMsg();
	}
	
	public static String repeaterCommand(CoolQMessage coolQMessage) {
		if (coolQMessage.getMsg().length() > 2) {
			return coolQMessage.getMsg().substring(2);
		} else {
			return null;
		}
	}
}