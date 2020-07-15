package ninja.skyrocketing.robot.messages;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.robot.entity.BotConfig;
import ninja.skyrocketing.robot.entity.MessageEncapsulation;
import ninja.skyrocketing.robot.entity.datebase.UserExp;
import ninja.skyrocketing.utils.HttpUtil;
import ninja.skyrocketing.utils.RandomUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static cn.hutool.core.date.DateUnit.HOUR;

public class GameMessage {
	/**
	 * 投骰子
	 **/
	public static Message dice(MessageEncapsulation messageEntity) {
		int randomNum = RandomUtil.getRandomNum(100);
		String[] dice = new String[]{"⚀", "⚁", "⚂", "⚃", "⚄", "⚅"};
		return messageEntity.atSomeone(dice[randomNum % 6] + "\n点数为" + (randomNum % 6 + 1));
	}
	
	/**
	 * 石头剪刀布
	 **/
	public static Message rockPaperScissors(MessageEncapsulation messageEntity) {
		int randomNum = RandomUtil.getRandomNum(100);
		String[] rockPaperScissorsIcon = new String[]{"✊", "✌", "✋"};
		String[] rockPaperScissorsText = new String[]{"石头", "剪刀", "布"};
		return messageEntity.atSomeone(rockPaperScissorsIcon[randomNum % 3] + "\n手势为" + rockPaperScissorsText[randomNum % 3]);
	}
	
	/**
	 * 签到（无查询）
	 **/
	public static Message sign(MessageEncapsulation messageEntity) {
		Date date = new Date();
		if (!BotConfig.getUserExpMap().containsKey(messageEntity.getUserId())) {
			int randomNum = RandomUtil.getRandomNum(10) + 10;
			UserExp userExp = new UserExp(messageEntity.getUserId(), messageEntity.getGroupId(), randomNum, date);
			BotConfig.setUserExpMap(userExp);
			return messageEntity.atSomeone("\n" +
					DateUtil.format(date, "YYYY年MM月dd日 HH:mm:ss") +
					"\n签到成功✔\n" +
					"获取 " + randomNum + " EXP" + "\n" +
					"总经验值为 " + randomNum + " EXP"
			);
		} else {
			if (DateUtil.between(date, BotConfig.getUserExpMap().get(messageEntity.getUserId()).getSignDate(), HOUR) >= 24) {
				int randomNum = RandomUtil.getRandomNum(10) + 10;
				int expTmp = BotConfig.getUserExpMap().get(messageEntity.getUserId()).getExp();
				UserExp userExp = new UserExp(messageEntity.getUserId(), messageEntity.getGroupId(), randomNum + expTmp, date);
				BotConfig.setUserExpMap(userExp);
				return messageEntity.atSomeone("\n" +
						DateUtil.format(date, "YYYY年MM月dd日 HH:mm:ss") +
						"\n签到成功✔\n" +
						"获取 " + randomNum + " EXP" + "\n" +
						"总经验值为 " + (randomNum + expTmp) + " EXP"
				);
			} else {
				return messageEntity.atSomeone("\n24小时内只能签到一次。\n上次签到时间" +
						DateUtil.format(BotConfig.getUserExpMap().get(messageEntity.getUserId()).getSignDate(), "YYYY年MM月dd日 HH:mm:ss:sss"));
			}
		}
	}
	
	/**
	 * 获取一定数量的随机数
	 **/
	public static Message genRandomNum(MessageEncapsulation messageEntity) {
		String str = messageEntity.getMsg().replaceAll("生成随机数\\s*", "");
		if (str == null) {
			return messageEntity.atSomeone("没有指定数量。");
		} else {
			int num = Integer.parseInt(str);
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
	
	/**
	 * 守望先锋街机模式查询
	 **/
	public static Message getOverwatchArcadeModes(MessageEncapsulation messageEntity) throws IOException, ParseException {
		JSONObject owModes = HttpUtil.readJsonFromUrl("https://overwatcharcade.today/api/overwatch/today");
		SimpleDateFormat updateDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
		MessageChainBuilder messages = new MessageChainBuilder();
		messages.add("守望先锋街机模式列表\n更新时间：" +
				DateTime.of(updateDateTime.parse(owModes.getByPath("created_at", String.class))) + "\n");
		for (int i = 1; i < 8; i++) {
			messages.add(i + ". " + owModes.getByPath("modes.tile_" + i + ".name", String.class) + "\n");
		}
		return messages.asMessageChain();
	}
}