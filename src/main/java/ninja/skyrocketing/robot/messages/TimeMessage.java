package ninja.skyrocketing.robot.messages;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.DateUtil;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.PlainText;
import ninja.skyrocketing.robot.entity.MessageEncapsulation;
import ninja.skyrocketing.utils.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeMessage {
	/**
	 * 时间：获取当前时间
	 **/
	public static Message timeOfNow(MessageEncapsulation messageEntity) {
		return messageEntity.sendMsg(time().contentToString());
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
	public static Message kaoyanCountDown(MessageEncapsulation messageEntity) {
		LocalDate today = LocalDate.now();
		String days = TimeUtil.calculateDate(
				new int[]{
						today.getYear(),
						today.getMonthValue(),
						today.getDayOfMonth()
				}, new int[]{
						2020,
						12,
						19
				}).toString();
		return messageEntity.sendMsg("距离 2021 考研还有 " + days + " 天！");
	}
	
	/**
	 * 输出日历
	 **/
	public static Message calendar(MessageEncapsulation messageEntity) {
		int offset, count = 0, sum = 0;
		int mon[] = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		String[] msg = messageEntity.getMsg().split(" ");
		if (msg.length > 1) {
			int year = Integer.parseInt(msg[1]);
			int month = Integer.parseInt(msg[2]);
			if (TimeUtil.isLeapYear(year))
				mon[2] = 29;
			for (int i = 1; i < month; ++i)
				sum += mon[i];
			for (int k = year - 1; k >= 1900; --k) {
				sum += 365;
				if (TimeUtil.isLeapYear(k))
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
			return messageEntity.sendMsg(result.toString());
		}
		return null;
	}
}