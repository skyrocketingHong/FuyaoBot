package ninja.skyrocketing.robot.service.impl;

import ninja.skyrocketing.robot.pojo.CoolQMessage;
import ninja.skyrocketing.util.RandomUtil;
import ninja.skyrocketing.util.TimeUtil;

import java.util.*;

public class GameServiceImpl {
	public static String dice(CoolQMessage coolQMessage) {
		int randomNum = RandomUtil.getRandomNum(100);
		String[] dice = new String[]{"⚀", "⚁", "⚂", "⚃", "⚄", "⚅"};
		return coolQMessage.atSomeone() + dice[randomNum % 6] + "\n点数为" + (randomNum % 6 + 1);
	}
	
	public static String rockPaperScissors(CoolQMessage coolQMessage) {
		int randomNum = RandomUtil.getRandomNum(100);
		String[] rockPaperScissorsIcon = new String[]{"✊", "✌", "✋"};
		String[] rockPaperScissorsText = new String[]{"石头", "剪刀", "布"};
		return coolQMessage.atSomeone() + rockPaperScissorsIcon[randomNum % 3] + "\n手势为" + rockPaperScissorsText[randomNum % 3];
	}
	
	public static String sign(CoolQMessage coolQMessage) {
		int randomNum = RandomUtil.getRandomNum(100) + 1000;
		Map<Long, Integer> map =  coolQMessage.getYamlFile().getSignInMap();
		if (map.containsKey(coolQMessage.getUserId())){
			return "不要重复签到哦！";
		} else {
			map.put(coolQMessage.getUserId(), randomNum);
		}
		return coolQMessage.atSomeone() + "签到成功✔\n" + TimeUtil.getDateTimeString() + " 获取 " + map.get(coolQMessage.getUserId()).toString() + " EXP";
	}
	
	public static String genRandomNum(CoolQMessage coolQMessage) {
		int num = Integer.parseInt(coolQMessage.getMsg().replaceAll("生成随机数\\s*", ""));
		if (num >= 101) {
			return coolQMessage.atSomeone() + num + "太大了，为避免刷屏拒绝生成！";
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
			return coolQMessage.atSomeone() + "生成的" + num + "个随机数为：\n" + result.toString();
		}
	}
}