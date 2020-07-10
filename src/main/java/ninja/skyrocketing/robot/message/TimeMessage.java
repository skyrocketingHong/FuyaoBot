package ninja.skyrocketing.robot.message;

import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.robot.entity.CoolQMessage;
import ninja.skyrocketing.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeMessage {
	public static Message timeOfNow(CoolQMessage coolQMessage) {
		LocalDateTime beijingTime = LocalDateTime.now();
		LocalDateTime ptTime = LocalDateTime.now(ZoneId.of("America/Los_Angeles"));
		return coolQMessage.sendMsg("中国标准时间 (UTC+8)：\n" + TimeUtil.getClockEmoji(beijingTime.getHour()) +
				beijingTime.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss")) +
				"\n太平洋时间 (UTC-7/UTC-8)：\n" + TimeUtil.getClockEmoji(ptTime.getHour()) +
				ptTime.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss")));
	}
	
	public static Message kaoyanCountDown(CoolQMessage coolQMessage) {
		LocalDate today = LocalDate.now();
		String days = TimeUtil.calculateDate(new int[]{today.getYear(), today.getMonthValue(), today.getDayOfMonth()}, new int[]{2020, 12, 19}).toString();
		return coolQMessage.sendMsg("距离 2021 考研还有 " + days + " 天！");
	}
	
	public static Message calendar(CoolQMessage coolQMessage) {
		int offset, count = 0, sum = 0;
		int mon[] = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		String[] msg = coolQMessage.getMsg().split(" ");
		if(msg.length > 1){
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
			result = new StringBuilder(msg[1] + "年" + msg[2]  + "月" + "\n" + "  日\t一\t二\t三\t四\t五\t六\n");
			for (int i = 0; i < offset % 7; ++i) {
				result.append("\t");
				count++;
			}
			for (int j = 1; j <= mon[month]; ++j) {
				String resultDay;
				if(j < 10){
					resultDay = String.format("%02d", j);
				} else {
					resultDay = String.valueOf(j);
				}
				result.append(resultDay).append("\t");
				count++;
				if(count % 7 == 0)
					result.append("\n");
			}
			return coolQMessage.sendMsg(result.toString());
		}
		return null;
	}
}