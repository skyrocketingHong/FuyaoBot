package ninja.skyrocketing.qqrobot.service.impl;

import ninja.skyrocketing.qqrobot.pojo.CoolQMessage;

public class EasterEggServiceImpl {
	//kkjj
	public static String kkjj(CoolQMessage coolQMessage) {
		String result = coolQMessage.atSomeone() + " " + "kkjj";
		coolQMessage.sendGroupMessage(result);
		return result;
	}
	
	//闪照
	public static String firePhoto(CoolQMessage coolQMessage) {
		String result = "hxd萌，有闪照🌶，gkd来康康" + "\n（如果是假闪照我也没办法）";
		coolQMessage.sendGroupMessage(result);
		return result;
	}
}
