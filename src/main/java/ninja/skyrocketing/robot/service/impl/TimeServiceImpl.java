package ninja.skyrocketing.robot.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import ninja.skyrocketing.robot.pojo.CoolQMessage;
import ninja.skyrocketing.util.TimeUtil;

public class TimeServiceImpl {
	public static String timeOfNow(CoolQMessage coolQMessage) {
		LocalDateTime beijingTime = LocalDateTime.now();
		LocalDateTime ptTime = LocalDateTime.now(ZoneId.of("America/Los_Angeles"));
		String[] clock = new String[]{"\ud83d\udd5b", "\ud83d\udd50", "\ud83d\udd51", "\ud83d\udd52", "\ud83d\udd53", "\ud83d\udd54", "\ud83d\udd55", "\ud83d\udd56", "\ud83d\udd57", "\ud83d\udd58", "\ud83d\udd59", "\ud83d\udd5a"};
		return "中国标准时间 (UTC+8)：\n" + clock[beijingTime.getHour() % 12] + beijingTime.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss")) + "\n太平洋时间 (UTC-7/UTC-8)：\n" + clock[ptTime.getHour() % 12] + ptTime.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss"));
	}
	
	public static String kaoyanCountDown(CoolQMessage coolQMessage) {
		LocalDate today = LocalDate.now();
		String days = TimeUtil.calculateDate(new int[]{today.getYear(), today.getMonthValue(), today.getDayOfMonth()}, new int[]{2020, 12, 19}).toString();
		return "[CQ:at,qq=all] 距离 2021 考研还有 " + days + " 天！";
	}
}