package ninja.skyrocketing.qqrobot.service.impl;

import net.lz1998.cq.utils.CQCode;
import ninja.skyrocketing.qqrobot.pojo.CoolQMessage;

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
		if (coolQMessage.getMsg().matches("^(晚安)(鸭|啊|哦|呀|安)?|安{1,2}$")) {
			result = CQCode.at(coolQMessage.getUserId()) + "\n" + "晚安哦" + "\n" + "おやすみ";
		} else {
			result = CQCode.at(coolQMessage.getUserId()) + "\n" + "早啊" + "\n" + "おはよう";
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
			String result = coolQMessage.getMsg().substring(2);
			coolQMessage.sendGroupMessage(result);
			return result;
		}
		return null;
	}
}
