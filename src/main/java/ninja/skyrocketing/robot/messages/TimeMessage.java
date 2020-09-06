package ninja.skyrocketing.robot.messages;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.DateUtil;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.PlainText;
import ninja.skyrocketing.robot.entity.MessageEncapsulation;
import ninja.skyrocketing.utils.MessageUtil;
import ninja.skyrocketing.utils.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeMessage {
	/**
	 * 时间：获取当前时间
	 **/
	public static Message timeOfNow(MessageEncapsulation messageEncapsulation) {
		return MessageUtil.StringToMessage(time().contentToString());
	}

	/**
	 * 获取当前时间的函数
	 **/
	public static Message time() {
		LocalDateTime beijingTime = LocalDateTime.now();
//		LocalDateTime ptTime = LocalDateTime.now(ZoneId.of("America/Los_Angeles"));
		ChineseDate chineseDate = new ChineseDate(DateUtil.parseDate(beijingTime.toLocalDate().toString()));
		return new PlainText(
//				TimeUtil.getClockEmoji(beijingTime.getHour()) +
//				" 中国标准时间 (UTC+8)" + "\n" +
				chineseDate.toString() + "\n" +
						beijingTime.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss"))
//				+ "\n" +
//				TimeUtil.getClockEmoji(ptTime.getHour()) +
//				" 太平洋时间 (UTC-7/UTC-8)" + "\n" +
//				ptTime.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日\nHH:mm:ss"))
		);
	}
	
	/**
	 * 考研倒计时
	 **/
	public static Message kaoyanCountDown(MessageEncapsulation messageEncapsulation) {
		LocalDate today = LocalDate.now();
		String days = TimeUtil.CalculateDate(
				new int[]{
						today.getYear(),
						today.getMonthValue(),
						today.getDayOfMonth()
				}, new int[]{
						2020,
						12,
						19
				}).toString();
		return MessageUtil.StringToMessage("距离 2021 考研还有 " + days + " 天！");
	}
	
	/**
	 * 早午晚问候
	 **/
	public static Message morningAndNight(MessageEncapsulation messageEncapsulation) {
		LocalTime now = LocalTime.now();
		int second = now.toSecondOfDay();
		String partsOfADay = TimeUtil.PartsOfADay(second);
		boolean goodNight = messageEncapsulation.getMsg().matches("^(晚安)(鸭|啊|哦|呀|安)?|安{1,2}$");
		boolean goodMorning = messageEncapsulation.getMsg().matches("早+(鸭|啊|哦|安|上好)*");
		boolean goodAfternoon = messageEncapsulation.getMsg().matches("午+(安|好)*|中午好|下午好");
		Message front = MessageUtil.AtSomeone(
				"\n" + TimeUtil.ClockEmoji(now.getHour()) + " " +
						partsOfADay + " " + now.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "\n",
				messageEncapsulation
		);
		switch (partsOfADay) {
			case "凌晨" -> {
				front.plus("凌晨");
				if (goodMorning) {
					return front.plus("这么早就发早，肯定是没睡觉吧！");
				}
				if (goodAfternoon) {
					return front.plus("？你是认真的吗？还是说有时差？");
				}
				if (goodNight) {
					return front.plus("晚安哦，明天（今天）记得早点睡。");
				}
				return null;
			}
			case "拂晓" -> {
				front.plus("拂晓");
				if (goodMorning) {
					return front.plus("快去睡觉吧，不然等着猝死吧！");
				}
				if (goodAfternoon) {
					return front.plus("？你是认真的吗？还是说有时差？");
				}
				if (goodNight) {
					return front.plus("晚安哦，明天（今天）记得早点睡。");
				}
				return null;
			}
			case "早晨" -> {
				front.plus("早晨");
				if (goodMorning) {
					return front.plus("早啊！");
				}
				if (goodAfternoon) {
					return front.plus("你是认真的吗？还是说有时差？");
				}
				if (goodNight) {
					return front.plus("一晚上没睡吗？");
				}
				return null;
			}
			case "午前" -> {
				front.plus("午前");
				if (goodMorning) {
					return front.plus("不早了，明天早点起来吧！");
				}
				if (goodAfternoon) {
					return front.plus("还没到下午，现在早了点。");
				}
				if (goodNight) {
					return front.plus("晚安？有时差还是没睡觉？");
				}
				return null;
			}
			case "午后" -> {
				front.plus("午后");
				if (goodMorning) {
					return front.plus("别发早了，下次记得别熬夜了。");
				}
				if (goodAfternoon) {
					return front.plus("下午好啊！");
				}
				if (goodNight) {
					return front.plus("有时差吗？还是说要去午睡了？");
				}
				return null;
			}
			case "傍晚" -> {
				front.plus("傍晚");
				if (goodMorning) {
					return front.plus("早？早，都可以早。");
				}
				if (goodAfternoon) {
					return front.plus("傍晚了，不要下午好了。");
				}
				if (goodNight) {
					return front.plus("这么早就睡觉吗？");
				}
				return null;
			}
			case "薄暮" -> {
				front.plus("薄暮");
				if (goodMorning) {
					return front.plus("早？早，都可以早。");
				}
				if (goodAfternoon) {
					return front.plus("快要到晚上了哦！");
				}
				if (goodNight) {
					return front.plus("睡这么早吗？还是说是表面晚安？");
				}
				return null;
			}
			case "深夜" -> {
				if (goodMorning) {
					return front.plus("早？早，都可以早。");
				}
				if (goodAfternoon) {
					return front.plus("？你是认真的吗？还是说有时差？");
				}
				if (goodNight) {
					return front.plus("晚安哦！");
				}
				return null;
			}
		}
		return null;
	}
	
	/**
	 * 输出日历
	 **/
	public static Message calendar(MessageEncapsulation messageEncapsulation) {
		int offset, count = 0, sum = 0;
		int mon[] = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		String[] msg = messageEncapsulation.getMsg().split(" ");
		if (msg.length > 1) {
			int year = Integer.parseInt(msg[1]);
			int month = Integer.parseInt(msg[2]);
			if (TimeUtil.IsLeapYear(year))
				mon[2] = 29;
			for (int i = 1; i < month; ++i)
				sum += mon[i];
			for (int k = year - 1; k >= 1900; --k) {
				sum += 365;
				if (TimeUtil.IsLeapYear(k))
					sum += 1;
			}
			while (sum > 7)
				sum %= 7;
			offset = sum + 1;
			StringBuilder result;
			result = new StringBuilder(msg[1] + "年" + msg[2] + "月" + "\n" + "  日\t一\t二\t三\t四\t五\t六\n");
			for (int i = 0; i < offset % 7; ++i) {
				result.append("\t");
				count++;
			}
			for (int j = 1; j <= mon[month]; ++j) {
				String resultDay;
				if (j < 10) {
					resultDay = String.format("%02d", j);
				} else {
					resultDay = String.valueOf(j);
				}
				result.append(resultDay).append("\t");
				count++;
				if (count % 7 == 0)
					result.append("\n");
			}
			return MessageUtil.StringToMessage(result.toString());
		}
		return null;
	}
}