package ninja.skyrocketing.robot.messages;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.robot.entity.BotConfig;
import ninja.skyrocketing.robot.entity.MessageEncapsulation;
import ninja.skyrocketing.robot.entity.datebase.UserExp;
import ninja.skyrocketing.robot.entity.datebase.UserExpIds;
import ninja.skyrocketing.utils.HttpUtil;
import ninja.skyrocketing.utils.RandomUtil;
import ninja.skyrocketing.utils.TimeUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static cn.hutool.core.date.DateUnit.HOUR;

public class GameMessage {
	/**
	 * 投骰子
	 **/
	public static Message dice(MessageEncapsulation messageEntity) {
		int randomNum = RandomUtil.getRandomNum(100);
		String[] dice = new String[]{"⚀", "⚁", "⚂", "⚃", "⚄", "⚅"};
		return messageEntity.atSomeone("\n" + dice[randomNum % 6] + " 点数为" + (randomNum % 6 + 1));
	}
	
	/**
	 * 石头剪刀布
	 **/
	public static Message rockPaperScissors(MessageEncapsulation messageEntity) {
		int randomNum = RandomUtil.getRandomNum(100);
		String[] rockPaperScissorsIcon = new String[]{"✊", "✌", "✋"};
		String[] rockPaperScissorsText = new String[]{"石头", "剪刀", "布"};
		return messageEntity.atSomeone("\n" + rockPaperScissorsIcon[randomNum % 3] + " 手势为" + rockPaperScissorsText[randomNum % 3]);
	}
	
	/**
	 * 签到
	 **/
	public static Message sign(MessageEncapsulation messageEncapsulation) {
		Date date = new Date();
		UserExpIds userExpIdsTmp = new UserExpIds(messageEncapsulation.getUserId(), messageEncapsulation.getGroupId());
		if (!BotConfig.getUserExpMap().containsKey(userExpIdsTmp)) {
			int randomNum = RandomUtil.getRandomNum(10) + 10;
			UserExp userExp = new UserExp(messageEncapsulation.getUserId(), messageEncapsulation.getGroupId(), randomNum, date);
			BotConfig.setUserExpMap(userExp);
			return messageEncapsulation.atSomeone("\n" +
					"🟢 首次签到成功 获取 " + randomNum + " EXP" + "\n" +
					TimeUtil.getClockEmoji(BotConfig.getUserExpMap().get(userExpIdsTmp).getNextSignDate().getHours()) + " 下次签到时间 " + DateUtil.format(BotConfig.getUserExpMap().get(userExpIdsTmp).getNextSignDate(), "HH:mm:ss") + "\n" +
					"🚩 其他指令 \"EXP查询\" \"EXP排名\""
			);
		} else {
			if (DateUtil.between(date, BotConfig.getUserExpMap().get(userExpIdsTmp).getSignDate(), HOUR) >= 6) {
				int randomNum = RandomUtil.getRandomNum(10) + 10;
				int expTmp = BotConfig.getUserExpMap().get(userExpIdsTmp).getExp();
				UserExp userExp = new UserExp(messageEncapsulation.getUserId(), messageEncapsulation.getGroupId(), randomNum + expTmp, date);
				BotConfig.setUserExpMap(userExp);
				return messageEncapsulation.atSomeone("\n" +
						"🟢 签到成功 获取 " + randomNum + " EXP" + "\n" +
						TimeUtil.getClockEmoji(BotConfig.getUserExpMap().get(userExpIdsTmp).getNextSignDate().getHours()) + " 下次签到时间 " + DateUtil.format(BotConfig.getUserExpMap().get(userExpIdsTmp).getNextSignDate(), "HH:mm:ss") + "\n" +
						"🚩 其他指令 \"EXP查询\" \"EXP排名\""
				);
			} else {
				return messageEncapsulation.atSomeone("\n" +
						"🔴 签到失败 (每群每6小时可签到一次)" + "\n" +
						TimeUtil.getClockEmoji(BotConfig.getUserExpMap().get(userExpIdsTmp).getNextSignDate().getHours()) + " 下次签到时间 " + DateUtil.format(BotConfig.getUserExpMap().get(userExpIdsTmp).getNextSignDate(), "HH:mm:ss") + "\n" +
						"🚩 其他指令 \"EXP查询\" \"EXP排名\""
				);
			}
		}
	}
	
	/**
	 * 签到查询
	 **/
	public static Message signExpQueryById(MessageEncapsulation messageEncapsulation) {
		UserExpIds userExpIdsTmp = new UserExpIds(messageEncapsulation.getUserId(), messageEncapsulation.getGroupId());
		return messageEncapsulation.atSomeone("\n" +
				"⚙ 总 EXP 为 " + BotConfig.getUserExpMap().get(userExpIdsTmp).getExp() + "\n" +
				TimeUtil.getClockEmoji(BotConfig.getUserExpMap().get(userExpIdsTmp).getNextSignDate().getHours()) + " 下次签到时间 " + DateUtil.format(BotConfig.getUserExpMap().get(userExpIdsTmp).getNextSignDate(), "MM月dd日 HH:mm") + "\n" +
				"🚩 其他指令 \"签到\" \"EXP排名\""
		);
	}
	
	/**
	 * 经验值排名（前五名）
	 **/
	public static Message expRanking(MessageEncapsulation messageEncapsulation) {
		List<UserExpIds> userExpIdsList = BotConfig.userExp.findUserExpByGroupId(messageEncapsulation.getGroupId());
		MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
		messageChainBuilder.add("🚀 本群 EXP 前五名" + "\n");
		for (int i = 0; i < userExpIdsList.size() && i < 5; i++) {
			String nameCard;
			try {
				nameCard = messageEncapsulation.getGroupMessageEvent().getGroup().get(userExpIdsList.get(i).getUserId()).getNameCard();
			} catch (NoSuchElementException e) {
				UserExpIds userExpIds = new UserExpIds(userExpIdsList.get(i).getUserId(), messageEncapsulation.getGroupMessageEvent().getGroup().getId());
				BotConfig.userExp.deleteByUserExpIds(userExpIds);
				continue;
			}
			messageChainBuilder.add((i + 1) + ". " + nameCard + "\n");
		}
		messageChainBuilder.add("🚩 其他指令 \"EXP查询\" \"签到\"");
		return messageChainBuilder.asMessageChain();
	}
	
	/**
	 * 获取一定数量的随机数
	 **/
	public static Message genRandomNum(MessageEncapsulation messageEntity) {
		String str = messageEntity.getMsg().replaceAll("生成随机数\\s*|^((do)|(sudo)) get randomnum\\s*", "");
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
		MessageReceipt<Contact> messageReceipt = messageEntity.getGroupMessageEvent().getGroup().sendMessage("等待API返回数据...");
		JSONObject owModes = HttpUtil.readJsonFromUrl("https://overwatcharcade.today/api/overwatch/today");
		SimpleDateFormat updateDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
		MessageChainBuilder messages = new MessageChainBuilder();
		messages.add("今日守望先锋街机模式列表\n更新时间：" +
				DateTime.of(updateDateTime.parse(owModes.getByPath("created_at", String.class))) + "\n");
		for (int i = 1; i < 8; i++) {
			messages.add(i + ". " + owModes.getByPath("modes.tile_" + i + ".name", String.class) + "\n");
		}
		messageReceipt.recall();
		return messages.asMessageChain();
	}
}