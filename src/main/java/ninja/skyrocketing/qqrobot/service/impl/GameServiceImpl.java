package ninja.skyrocketing.qqrobot.service.impl;

import net.lz1998.cq.utils.CQCode;
import ninja.skyrocketing.qqrobot.pojo.CoolQMessage;
import ninja.skyrocketing.qqrobot.util.RandomUtil;

public class GameServiceImpl {
	//投骰子
	public static String dice(CoolQMessage coolQMessage) {
		int randomNum = RandomUtil.getRandomNum(100);
		String[] dice = {"⚀", "⚁", "⚂", "⚃", "⚄", "⚅"};
		String result = CQCode.at(coolQMessage.getUserId()) + " " + dice[randomNum % 6] + "\n" + "点数为" + ((randomNum % 6) + 1);
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
