package ninja.skyrocketing.robot.service.impl;

import ninja.skyrocketing.robot.pojo.CoolQMessage;
import ninja.skyrocketing.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RepeaterServiceImpl {
	public static String stupidAI(CoolQMessage coolQMessage) {
		return coolQMessage.getMsg().replaceAll("吗[?？]?$", "！");
	}
	
	public static String morningAndNight(CoolQMessage coolQMessage) {
		LocalTime now = LocalTime.now();
		int hour = now.getHour();
		boolean goodNight = coolQMessage.getMsg().matches("^(晚安)(鸭|啊|哦|呀|安)?|安{1,2}$");
		boolean goodMorning = coolQMessage.getMsg().matches("早+(鸭|啊|哦|安|上好)*");
		boolean goodAfternoon = coolQMessage.getMsg().matches("午+(安|好)*|中午好|下午好");
		String front = coolQMessage.atSomeone() + "\n" + TimeUtil.getClockEmoji(hour) +
				"当前时间" + now.format(DateTimeFormatter.ofPattern("HH:mm")) + "\n";
		switch (hour) {
			case 0:
			case 1:
			case 2: {
				if(goodMorning) {
					return front + "这么早就发早，肯定是没睡觉吧！";
				}
				if (goodAfternoon) {
					return front + "？你是认真的吗？还是说有时差？";
				}
				if (goodNight) {
					return front + "晚安哦，明天（今天）记得早点睡。\nおやすみなさい❕";
				}
				return null;
			}
			case 3:
			case 4: {
				if(goodMorning) {
					return front + "gkd去睡觉，别早了，再不睡就猝死了！";
				}
				if (goodAfternoon) {
					return front + "？你是认真的吗？还是说有时差？";
				}
				if (goodNight) {
					return front + "别晚安了，再不睡就要发早安了。";
				}
				return null;
			}
			case 5:
			case 6:{
				if(goodMorning) {
					return front + "早啊，起这么早？\nおはようございます❕";
				}
				if(goodAfternoon) {
					return front + "我没记错的话，现在还是早上吧。";
				}
				if (goodNight) {
					return front + "晚安？又是一晚上没睡吧。";
				}
				return null;
			}
			case 7:
			case 8:
			case 9: {
				if(goodMorning) {
					return front + "早啊！\nおはようございます❕";
				}
				if(goodAfternoon) {
					return front + "我没记错的话，现在还是早上吧。";
				}
				if (goodNight) {
					return front + "晚安？又是一晚上没睡吧。";
				}
				return null;
			}
			case 10:
			case 11: {
				if(goodMorning) {
					return front + "不早了，快中午了，明天早点起来吧。";
				}
				if(goodAfternoon) {
					return front + "快了快了，马上就到了。";
				}
				if (goodNight) {
					return front + "晚安？又是一晚上没睡吧。";
				}
				return null;
			}
			case 12:
			case 13:
			case 14:
			case 15:
			case 16:
			case 17: {
				if(goodMorning) {
					return front + "不早了，现在是下午了。";
				}
				if(goodAfternoon) {
					return front + "下午好啊！";
				}
				if (goodNight) {
					return front + "刚刚午睡起来？";
				}
				return null;
			}
			case 18:
			case 19:
			case 20: {
				if(goodMorning) {
					return front + "不早了，都到晚上了。";
				}
				if(goodAfternoon) {
					return front + "别午了，都到晚上了。";
				}
				if (goodNight) {
					return front + "？表面晚安？";
				}
				return null;
			}
			case 21:
			case 22:
			case 23: {
				if(goodMorning) {
					return front + "不早了，要睡了。";
				}
				if(goodAfternoon) {
					return front + "别中午了，都到晚上了。";
				}
				if (goodNight) {
					return front + "晚安哦。\nおやすみなさい❕";
				}
				return null;
			}
		}
		return null;
	}
	
	public static String justThis(CoolQMessage coolQMessage) {
		return coolQMessage.getMsg();
	}
	
	public static String randomRepeater(CoolQMessage coolQMessage) {
		return coolQMessage.getMsg();
	}
	
	public static String repeaterCommand(CoolQMessage coolQMessage) {
		if (coolQMessage.getMsg().length() > 2) {
			return coolQMessage.getMsg().substring(2);
		} else {
			return null;
		}
	}
}