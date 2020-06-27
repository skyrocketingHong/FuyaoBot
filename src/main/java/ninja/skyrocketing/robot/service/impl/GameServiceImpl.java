package ninja.skyrocketing.robot.service.impl;

import ninja.skyrocketing.robot.pojo.CoolQMessage;
import ninja.skyrocketing.util.RandomUtil;

public class GameServiceImpl {
	public static String dice(CoolQMessage coolQMessage) {
		int randomNum = RandomUtil.getRandomNum(100);
		String[] dice = new String[]{"⚀", "⚁", "⚂", "⚃", "⚄", "⚅"};
		return coolQMessage.atSomeone() + " " + dice[randomNum % 6] + "\n点数为" + (randomNum % 6 + 1);
	}
	
	public static String rockPaperScissors(CoolQMessage coolQMessage) {
		int randomNum = RandomUtil.getRandomNum(100);
		String[] rockPaperScissorsIcon = new String[]{"✊", "✌", "✋"};
		String[] rockPaperScissorsText = new String[]{"石头", "剪刀", "布"};
		return coolQMessage.atSomeone() + " " + rockPaperScissorsIcon[randomNum % 3] + "\n手势为" + rockPaperScissorsText[randomNum % 3];
	}
}