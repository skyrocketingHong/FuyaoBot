package ninja.skyrocketing.robot.service.impl;

import ninja.skyrocketing.robot.pojo.CoolQMessage;

import java.time.LocalTime;

public class RepeaterServiceImpl {
	//人工智障
	public static String stupidAI(CoolQMessage coolQMessage) {
		String result = coolQMessage.getMsg().replaceAll("吗[?？]?$", "！");
		coolQMessage.sendGroupMessage(result);
		return result;
	}
	
	//早晚安
	public static String morningAndNight(CoolQMessage coolQMessage) {
		String result;
		LocalTime now = LocalTime.now();
		if (coolQMessage.getMsg().matches("^(晚安)(鸭|啊|哦|呀|安)?|安{1,2}$")) {
			if(now.isBefore(LocalTime.parse("01:00:00")) && now.isAfter(LocalTime.parse("21:00:00")))
				result = coolQMessage.atSomeone() + "\n" + "晚安哦" + "\n" + "おやすみ";
			else
				result = coolQMessage.atSomeone() + "\n" + "这个点怎么就晚安了？";
		}
		else {
			result = coolQMessage.atSomeone() + "\n" + "早啊" + "\n" + "おはよう";
		}
		coolQMessage.sendGroupMessage(result);
		return result;
	}
	
	//啊这，就这？
	public static String justThis(CoolQMessage coolQMessage) {
		coolQMessage.sendGroupSelfMessage();
		return coolQMessage.getMsg();
	}
	
	//随机复读
	public static String randomRepeater(CoolQMessage coolQMessage) {
		coolQMessage.sendGroupSelfMessage();
		return coolQMessage.getMsg();
	}
	
	//复读命令
	public static String repeaterCommand(CoolQMessage coolQMessage) {
		if(coolQMessage.getMsg().length() > 2){
			System.out.println(coolQMessage.getMsg());
			String result = coolQMessage.getMsg().substring(2);
			coolQMessage.sendGroupMessage(result);
			return result;
		}
		return null;
	}
}
