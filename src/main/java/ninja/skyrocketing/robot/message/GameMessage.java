package ninja.skyrocketing.robot.message;

import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.robot.entity.MessageEntity;
import ninja.skyrocketing.util.RandomUtil;
import ninja.skyrocketing.util.TimeUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameMessage {
	public static Message dice(MessageEntity messageEntity) {
		int randomNum = RandomUtil.getRandomNum(100);
		String[] dice = new String[]{"⚀", "⚁", "⚂", "⚃", "⚄", "⚅"};
		return messageEntity.atSomeone(dice[randomNum % 6] + "\n点数为" + (randomNum % 6 + 1));
	}
	
	public static Message rockPaperScissors(MessageEntity messageEntity) {
		int randomNum = RandomUtil.getRandomNum(100);
		String[] rockPaperScissorsIcon = new String[]{"✊", "✌", "✋"};
		String[] rockPaperScissorsText = new String[]{"石头", "剪刀", "布"};
		return messageEntity.atSomeone(rockPaperScissorsIcon[randomNum % 3] + "\n手势为" + rockPaperScissorsText[randomNum % 3]);
	}
	
	public static Message sign(MessageEntity messageEntity) {
		int randomNum = RandomUtil.getRandomNum(100) + 1000;
		Map<Long, Integer> map = messageEntity.getYamlFile().getSignInMap();
		if (map.containsKey(messageEntity.getUserId())) {
			return messageEntity.sendMsg("不要重复签到哦！");
		} else {
			map.put(messageEntity.getUserId(), randomNum);
		}
		return messageEntity.atSomeone("签到成功✔\n" + TimeUtil.getDateTimeString() + " 获取 " + map.get(messageEntity.getUserId()).toString() + " EXP");
	}
	
	public static Message genRandomNum(MessageEntity messageEntity) {
		int num = Integer.parseInt(messageEntity.getMsg().replaceAll("生成随机数\\s*", ""));
		if (num >= 101) {
			return messageEntity.atSomeone(num + "太大了，为避免刷屏拒绝生成！");
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
			return messageEntity.atSomeone("生成的" + num + "个随机数为：\n" + result);
		}
	}
}