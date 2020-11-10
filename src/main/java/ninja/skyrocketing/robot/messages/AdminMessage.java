package ninja.skyrocketing.robot.messages;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.RobotApplication;
import ninja.skyrocketing.robot.entity.BotConfig;
import ninja.skyrocketing.robot.entity.MessageEncapsulation;
import ninja.skyrocketing.robot.entity.datebase.Trigger;
import ninja.skyrocketing.robot.entity.datebase.UserExpIds;
import ninja.skyrocketing.utils.MessageUtil;
import ninja.skyrocketing.utils.TimeUtil;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.NoSuchElementException;
import java.util.Optional;

public class AdminMessage {
	/**
	 * 刷新数据库缓存
	 * sudo refresh
	 **/
	public static Message refreshConfigFile(MessageEncapsulation messageEncapsulation) {
		if (BotConfig.getAdminUsers().contains(messageEncapsulation.getUserId())) {
			BotConfig.refresh();
			return MessageUtil.StringToMessage("Refresh done.");
		} else {
			return MessageUtil.NotSudo(messageEncapsulation);
		}
	}
	
	/**
	 * 获取复读阈值
	 * sudo get list config.randomrate
	 * sudo get config randomrate
	 **/
	public static Message getRandomRate(MessageEncapsulation messageEncapsulation) {
		if (BotConfig.getAdminUsers().contains(messageEncapsulation.getUserId())) {
			return MessageUtil.StringToMessage("Random rate is " +
					(100 - Long.parseLong(BotConfig.getConfigMap().get("random"))) / 100.00 + " now.");
		} else {
			return MessageUtil.NotSudo(messageEncapsulation);
		}
	}
	
	/**
	 * 设置复读阈值
	 * sudo set list config.randomrate {num} [0,100]
	 * sudo set config {name} {num}
	 **/
	public static Message setRandomRate(MessageEncapsulation messageEncapsulation) {
		if (BotConfig.getAdminUsers().contains(messageEncapsulation.getUserId())) {
			String num = messageEncapsulation.getMsg().substring(32);
			System.out.println(Integer.parseInt(num));
			if (Integer.parseInt(num) <= 0 || Integer.parseInt(num) >= 100) {
				return MessageUtil.StringToMessage("Random rate must between 0 (include) and 100 (include).");
			} else {
				BotConfig.setConfigMap(1, "random", num);
				return getRandomRate(messageEncapsulation);
			}
		} else {
			return MessageUtil.NotSudo(messageEncapsulation);
		}
	}
	
	/**
	 * 获取触发器列表
	 * sudo get list trigger
	 **/
	public static Message getFunction(MessageEncapsulation messageEncapsulation) {
		if (BotConfig.getAdminUsers().contains(messageEncapsulation.getUserId())) {
			MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
			messageChainBuilder.add("所有功能及关键字正则表达式列表" + "\n");
			for (Trigger trigger : BotConfig.getTriggers()) {
				messageChainBuilder.add(trigger.getId() + ". " + trigger.getName() + "\n" +
						trigger.isEnableToString() + "\t" +
						trigger.getKeywordRegex() + "\n"
				);
			}
			return messageChainBuilder.asMessageChain();
		} else {
			return MessageUtil.NotSudo(messageEncapsulation);
		}
	}
	
	/**
	 * 使用消息修改功能启用开关
	 * sudo set trigger {id} {true|false}
	 **/
	public static Message setTriggerTrueOrFalse(MessageEncapsulation messageEncapsulation) {
		if (BotConfig.getAdminUsers().contains(messageEncapsulation.getUserId())) {
			MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
			String[] commandSplit = messageEncapsulation.getMsg().split("\\s");
			
			Integer id = Integer.parseInt(commandSplit[3]);
			boolean isEnable = Boolean.parseBoolean(commandSplit[4]);
			
			Optional<Trigger> triggerById = BotConfig.trigger.findById(id);
			Trigger trigger = triggerById.get();
			
			trigger.setId(id);
			trigger.setEnable(isEnable);
			
			BotConfig.trigger.save(trigger);
			BotConfig.refreshTriggerList();
			
			messageChainBuilder.add(trigger.getName() + "修改为" + trigger.isEnableToString());
			
			return messageChainBuilder.asMessageChain();
		} else {
			return MessageUtil.NotSudo(messageEncapsulation);
		}
	}
	
	/**
	 * 清理数据库签到退群人员
	 * sudo clean userexp
	 **/
	public static Message cleanUserExp(MessageEncapsulation messageEncapsulation) {
		int i = 0;
		for (UserExpIds tmpUserExpIds : BotConfig.getUserExpMap().keySet()) {
			try {
				messageEncapsulation.getGroupMessageEvent().getBot().getGroup(tmpUserExpIds.getGroupId()).get(tmpUserExpIds.getUserId()).getNameCard();
			} catch (NoSuchElementException e) {
				System.out.println(e);
				UserExpIds userExpIds = new UserExpIds(tmpUserExpIds.getUserId(), tmpUserExpIds.getGroupId());
				System.out.println(userExpIds);
				BotConfig.userExp.deleteByUserExpIds(userExpIds);
				++i;
			}
		}
		return MessageUtil.StringToMessage("已清理" + i);
	}
	
	/**
	 * 获取机器人运行信息
	 * sudo get botinfo
	 **/
	public static Message getBotInfo(MessageEncapsulation messageEncapsulation) {
		if (BotConfig.getAdminUsers().contains(messageEncapsulation.getUserId())) {
			//获取运行内存
			MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
			MemoryMXBean bean = ManagementFactory.getMemoryMXBean();
			MemoryUsage memoryUsage = bean.getHeapMemoryUsage();
			messageChainBuilder.add("占用内存: " + memoryUsage.getUsed() + "\n");

			//获取运行时间
			String now = TimeUtil.DateTimeString();
			messageChainBuilder.add("稳定运行: " +
					DateTime.of(RobotApplication.startTime,
							"YYYY年MM月dd日 HH:mm:ss").between(
							DateTime.of(now,
									"YYYY年MM月dd日 HH:mm:ss"),
							DateUnit.MINUTE) +
					"分钟" + "\n");

			//获取加群数量
			int groupNums = RobotApplication.bot.getGroups().size();
			messageChainBuilder.add("已加群 " + groupNums + " 个\n");
			//获取好友人数
			int friendNums = RobotApplication.bot.getFriends().size();
			messageChainBuilder.add("已好友 " + groupNums + " 个");

			return messageChainBuilder.asMessageChain();
		} else {
			return MessageUtil.NotSudo(messageEncapsulation);
		}
	}
}
