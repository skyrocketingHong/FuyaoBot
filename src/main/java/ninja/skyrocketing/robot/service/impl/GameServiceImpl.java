package ninja.skyrocketing.robot.service.impl;

import ninja.skyrocketing.robot.pojo.CoolQMessage;
import ninja.skyrocketing.util.RandomUtil;

public class GameServiceImpl {
	//投骰子
	public static String dice(CoolQMessage coolQMessage) {
		int randomNum = RandomUtil.getRandomNum(100);
		String[] dice = {"⚀", "⚁", "⚂", "⚃", "⚄", "⚅"};
		String result = coolQMessage.atSomeone() + " " + dice[randomNum % 6] + "\n" + "点数为" + ((randomNum % 6) + 1);
		coolQMessage.sendGroupMessage(result);
		return result;
	}
	
	//石头剪刀布
	public static String rockPaperScissors(CoolQMessage coolQMessage) {
		int randomNum = RandomUtil.getRandomNum(100);
		String[] rockPaperScissorsIcon = {"✊", "✌", "✋"};
		String[] rockPaperScissorsText = {"石头", "剪刀", "布"};
		String result = coolQMessage.atSomeone() + " " + rockPaperScissorsIcon[randomNum % 3] + "\n" + "手势为" + rockPaperScissorsText[randomNum % 3];
		coolQMessage.sendGroupMessage(result);
		return result;
	}
}
