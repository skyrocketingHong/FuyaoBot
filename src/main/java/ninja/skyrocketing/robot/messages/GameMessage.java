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
import ninja.skyrocketing.utils.MessageUtil;
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
	public static Message dice(MessageEncapsulation messageEncapsulation) {
		int randomNum = RandomUtil.RandomNum(100);
		String[] dice = new String[]{"⚀", "⚁", "⚂", "⚃", "⚄", "⚅"};
		return MessageUtil.AtSomeone("\n" + dice[randomNum % 6] + " 点数为" + (randomNum % 6 + 1), messageEncapsulation);
	}
	
	/**
	 * 石头剪刀布
	 **/
	public static Message rockPaperScissors(MessageEncapsulation messageEncapsulation) {
		int randomNum = RandomUtil.RandomNum(100);
		String[] rockPaperScissorsIcon = new String[]{"✊", "✌", "✋"};
		String[] rockPaperScissorsText = new String[]{"石头", "剪刀", "布"};
		return MessageUtil.AtSomeone("\n" + rockPaperScissorsIcon[randomNum % 3] + " 手势为" + rockPaperScissorsText[randomNum % 3], messageEncapsulation);
	}
	
	/**
	 * 签到
	 **/
	public static Message sign(MessageEncapsulation messageEncapsulation) {
		Date date = new Date();
		UserExpIds userExpIdsTmp = new UserExpIds(messageEncapsulation.getUserId(), messageEncapsulation.getGroupId());
		if (!BotConfig.getUserExpMap().containsKey(userExpIdsTmp)) {
			int randomNum = RandomUtil.RandomNum(10) + 10;
			UserExp userExp = new UserExp(messageEncapsulation.getUserId(), messageEncapsulation.getGroupId(), randomNum, date);
			BotConfig.setUserExpMap(userExp);
			return MessageUtil.AtSomeone("\n" +
							"🟢 首次签到成功 获取 " + randomNum + " EXP" + "\n" +
							TimeUtil.ClockEmoji(BotConfig.getUserExpMap().get(userExpIdsTmp).getNextSignDate().getHours()) + " 下次签到时间 " + DateUtil.format(BotConfig.getUserExpMap().get(userExpIdsTmp).getNextSignDate(), "HH:mm:ss") + "\n" +
							"🚩 其他指令 1.\"EXP查询\" 2.\"EXP排名\" (仅限群聊使用)",
					messageEncapsulation
			);
		} else {
			if (DateUtil.between(date, BotConfig.getUserExpMap().get(userExpIdsTmp).getSignDate(), HOUR) >= 6) {
				int randomNum = RandomUtil.RandomNum(10) + 10;
				int expTmp = BotConfig.getUserExpMap().get(userExpIdsTmp).getExp();
				UserExp userExp = new UserExp(messageEncapsulation.getUserId(), messageEncapsulation.getGroupId(), randomNum + expTmp, date);
				BotConfig.setUserExpMap(userExp);
				return MessageUtil.AtSomeone("\n" +
								"🟢 签到成功 获取 " + randomNum + " EXP" + "\n" +
								TimeUtil.ClockEmoji(BotConfig.getUserExpMap().get(userExpIdsTmp).getNextSignDate().getHours()) + " 下次签到时间 " + DateUtil.format(BotConfig.getUserExpMap().get(userExpIdsTmp).getNextSignDate(), "HH:mm:ss") + "\n" +
								"🚩 其他指令 1.\"EXP查询\" 2.\"EXP排名\" (仅限群聊使用)",
						messageEncapsulation
				);
			} else {
				return MessageUtil.AtSomeone("\n" +
								"🔴 签到失败 (每群每6小时可签到一次)" + "\n" +
								TimeUtil.ClockEmoji(BotConfig.getUserExpMap().get(userExpIdsTmp).getNextSignDate().getHours()) + " 下次签到时间 " + DateUtil.format(BotConfig.getUserExpMap().get(userExpIdsTmp).getNextSignDate(), "HH:mm:ss") + "\n" +
								"🚩 其他指令 1.\"EXP查询\" 2.\"EXP排名\" (仅限群聊使用)",
						messageEncapsulation
				);
			}
		}
	}
	
	/**
	 * 签到查询
	 **/
	public static Message signExpQueryById(MessageEncapsulation messageEncapsulation) {
		UserExpIds userExpIdsTmp = new UserExpIds(messageEncapsulation.getUserId(), messageEncapsulation.getGroupId());
		return MessageUtil.AtSomeone("\n" +
						"⚙ 总 EXP 为 " + BotConfig.getUserExpMap().get(userExpIdsTmp).getExp() + "\n" +
						TimeUtil.ClockEmoji(BotConfig.getUserExpMap().get(userExpIdsTmp).getNextSignDate().getHours()) + " 下次签到时间 " + DateUtil.format(BotConfig.getUserExpMap().get(userExpIdsTmp).getNextSignDate(), "HH:mm:ss") + "\n" +
						"🚩 其他指令 1.\"签到\" 2.\"EXP排名\" (仅限群聊使用)",
				messageEncapsulation
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
		messageChainBuilder.add("🚩 其他指令 1.\"EXP查询\" 2.\"签到\" (仅限群聊使用)");
		return messageChainBuilder.asMessageChain();
	}
	
	/**
	 * 获取一定数量的随机数
	 **/
	public static Message genRandomNum(MessageEncapsulation messageEncapsulation) {
		String str = messageEncapsulation.getMsg().replaceAll("生成随机数\\s*|^((do)|(sudo)) get randomnum\\s*", "");
		if (str == null) {
			return MessageUtil.AtSomeone("\n" + "没有指定数量。", messageEncapsulation);
		} else {
			int num = Integer.parseInt(str);
			if (num >= 101) {
				return MessageUtil.AtSomeone("\n" + num + "太大了，为避免刷屏拒绝生成！", messageEncapsulation);
			} else {
				Set<Integer> numSet = new HashSet<>();
				int temp;
				StringBuilder result = new StringBuilder();
				while (numSet.size() < num) {
					temp = RandomUtil.RandomNum(num);
					if (numSet.add(temp)) {
						result.append(temp).append(" ");
					}
				}
				numSet.clear();
				return MessageUtil.AtSomeone("\n" + "生成的" + num + "个随机数为：\n" + result, messageEncapsulation);
			}
		}
	}
	
	/**
	 * 守望先锋街机模式查询
	 **/
	public static Message getOverwatchArcadeModes(MessageEncapsulation messageEncapsulation) throws IOException, ParseException {
		MessageReceipt<Contact> messageReceipt = MessageUtil.WaitingForAPI(messageEncapsulation);
		JSONObject owModes = HttpUtil.ReadJsonFromUrl("https://overwatcharcade.today/api/overwatch/today");
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