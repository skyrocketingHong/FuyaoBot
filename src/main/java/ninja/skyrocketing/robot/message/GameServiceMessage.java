package ninja.skyrocketing.robot.message;

import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.robot.entity.CoolQMessage;
import ninja.skyrocketing.util.RandomUtil;
import ninja.skyrocketing.util.TimeUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameServiceMessage {
	public static Message dice(CoolQMessage coolQMessage) {
		int randomNum = RandomUtil.getRandomNum(100);
		String[] dice = new String[]{"⚀", "⚁", "⚂", "⚃", "⚄", "⚅"};
		
		return coolQMessage.atSomeone(dice[randomNum % 6] + "\n点数为" + (randomNum % 6 + 1));
	}
	
	public static Message rockPaperScissors(CoolQMessage coolQMessage) {
		int randomNum = RandomUtil.getRandomNum(100);
		String[] rockPaperScissorsIcon = new String[]{"✊", "✌", "✋"};
		String[] rockPaperScissorsText = new String[]{"石头", "剪刀", "布"};
		return coolQMessage.atSomeone(rockPaperScissorsIcon[randomNum % 3] + "\n手势为" + rockPaperScissorsText[randomNum % 3]);
	}
	
	public static Message sign(CoolQMessage coolQMessage) {
		int randomNum = RandomUtil.getRandomNum(100) + 1000;
		Map<Long, Integer> map =  coolQMessage.getYamlFile().getSignInMap();
		if (map.containsKey(coolQMessage.getUserId())){
			return coolQMessage.sendMsg("不要重复签到哦！");
		} else {
			map.put(coolQMessage.getUserId(), randomNum);
		}
		return coolQMessage.atSomeone("签到成功✔\n" + TimeUtil.getDateTimeString() + " 获取 " + map.get(coolQMessage.getUserId()).toString() + " EXP");
	}
	
	public static Message genRandomNum(CoolQMessage coolQMessage) {
		int num = Integer.parseInt(coolQMessage.getMsg().replaceAll("生成随机数\\s*", ""));
		if (num >= 101) {
			return coolQMessage.atSomeone(num + "太大了，为避免刷屏拒绝生成！");
		} else {
			Set<Integer> numSet = new HashSet<>();
			int temp;
			StringBuilder result = new StringBuilder();
			while (numSet.size() < num) {
				temp = RandomUtil.getRandomNum(num);
				if (numSet.add(temp)) {
					result.append(temp).append(" ");
				}
			}
			numSet.clear();
			return coolQMessage.atSomeone("生成的" + num + "个随机数为：\n" + result);
		}
	}
}