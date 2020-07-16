package ninja.skyrocketing.robot.messages;

import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.robot.entity.BotConfig;
import ninja.skyrocketing.robot.entity.MessageEncapsulation;
import ninja.skyrocketing.utils.RandomUtil;
import ninja.skyrocketing.utils.TimeUtil;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RepeaterMessage {
	/**
	 * kkjj
	 **/
	public static Message kkjj(MessageEncapsulation messageEntity) {
		return kk(messageEntity);
	}
	
	/**
	 * kk
	 **/
	public static Message kk(MessageEncapsulation messageEntity) {
		return messageEntity.sendMsg("kk？gkd发！");
	}
	
	/**
	 * 人工智障
	 **/
	public static Message stupidAI(MessageEncapsulation messageEntity) {
		int randomNum = RandomUtil.getRandomNum(100);
		if (randomNum > Integer.parseInt(BotConfig.getConfigMap().get("random"))) {
			return messageEntity.sendMsg(messageEntity.getMsg().replaceAll("吗[?？]?$", "！"));
		}
		return null;
	}
	
	/**
	 * 早午晚问候
	 **/
	public static Message morningAndNight(MessageEncapsulation messageEntity) {
		LocalTime now = LocalTime.now();
		int hour = now.getHour();
		boolean goodNight = messageEntity.getMsg().matches("^(晚安)(鸭|啊|哦|呀|安)?|安{1,2}$");
		boolean goodMorning = messageEntity.getMsg().matches("早+(鸭|啊|哦|安|上好)*");
		boolean goodAfternoon = messageEntity.getMsg().matches("午+(安|好)*|中午好|下午好");
		Message front = messageEntity.atSomeone("\n" + TimeUtil.getClockEmoji(hour) +
				"当前时间" + now.format(DateTimeFormatter.ofPattern("HH:mm")) + "\n");
		switch (hour) {
			case 0:
			case 1:
			case 2: {
				if (goodMorning) {
					return front.plus("这么早就发早，肯定是没睡觉吧！");
				}
				if (goodAfternoon) {
					return front.plus("？你是认真的吗？还是说有时差？");
				}
				if (goodNight) {
					return front.plus("晚安哦，明天（今天）记得早点睡。\nおやすみなさい！");
				}
				return null;
			}
			case 3:
			case 4: {
				if (goodMorning) {
					return front.plus("gkd去睡觉，别早了，再不睡就猝死了！");
				}
				if (goodAfternoon) {
					return front.plus("？你是认真的吗？还是说有时差？");
				}
				if (goodNight) {
					return front.plus("别晚安了，再不睡就要发早安了。");
				}
				return null;
			}
			case 5:
			case 6: {
				if (goodMorning) {
					return front.plus("早啊，起这么早？\nおはようございます！");
				}
				if (goodAfternoon) {
					return front.plus("我没记错的话，现在还是早上吧。");
				}
				if (goodNight) {
					return front.plus("晚安？又是一晚上没睡吧。");
				}
				return null;
			}
			case 7:
			case 8:
			case 9: {
				if (goodMorning) {
					return front.plus("早啊！\nおはようございます！");
				}
				if (goodAfternoon) {
					return front.plus("我没记错的话，现在还是早上吧。");
				}
				if (goodNight) {
					return front.plus("晚安？又是一晚上没睡吧。");
				}
				return null;
			}
			case 10:
			case 11: {
				if (goodMorning) {
					return front.plus("不早了，快中午了，明天早点起来吧。");
				}
				if (goodAfternoon) {
					return front.plus("啊这，还没到中午。");
				}
				if (goodNight) {
					return front.plus("晚安？又是一晚上没睡吧。");
				}
				return null;
			}
			case 12:
			case 13:
			case 14:
			case 15:
			case 16:
			case 17: {
				if (goodMorning) {
					return front.plus("不早了，现在是下午了。");
				}
				if (goodAfternoon) {
					return front.plus("下午好啊！");
				}
				if (goodNight) {
					return front.plus("刚刚午睡起来？");
				}
				return null;
			}
			case 18:
			case 19:
			case 20: {
				if (goodMorning) {
					return front.plus("不早了，都到晚上了。");
				}
				if (goodAfternoon) {
					return front.plus("别午了，都到晚上了。");
				}
				if (goodNight) {
					return front.plus("？表面晚安？");
				}
				return null;
			}
			case 21:
			case 22:
			case 23: {
				if (goodMorning) {
					return front.plus("不早了，要睡了。");
				}
				if (goodAfternoon) {
					return front.plus("别中午了，都到晚上了。");
				}
				if (goodNight) {
					return front.plus("晚安哦。\nおやすみなさい！");
				}
				return null;
			}
		}
		return null;
	}
	
	/**
	 * 100%复读
	 **/
	public static Message repeaterCommand(MessageEncapsulation messageEntity) {
		MessageChain messageChain = messageEntity.getGroupMessageEvent().getMessage();
		if (messageChain.size() <= 2) {
			String msg = messageChain.contentToString();
			if (msg.matches("复读")) {
				return null;
			}
			return messageEntity.sendMsg(msg.replaceFirst("复读", ""));
		} else {
			MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
			for (int i = 2; i < messageChain.size(); i++) {
				System.out.println(messageChain.get(i));
				messageChainBuilder.append(messageChain.get(i));
			}
			return messageChainBuilder.asMessageChain();
		}
	}
}