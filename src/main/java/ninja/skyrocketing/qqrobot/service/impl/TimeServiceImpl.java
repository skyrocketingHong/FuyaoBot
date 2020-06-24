package ninja.skyrocketing.qqrobot.service.impl;

import ninja.skyrocketing.qqrobot.pojo.CoolQMessage;
import ninja.skyrocketing.qqrobot.util.TimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeServiceImpl {
	//现在时间
	public static String timeOfNow(CoolQMessage coolQMessage) {
		LocalDateTime beijingTime = LocalDateTime.now();
		LocalDateTime ptTime = LocalDateTime.now(ZoneId.of("America/Los_Angeles"));
		String[] clock = {"🕛", "🕐", "🕑", "🕒", "🕓", "🕔", "🕕", "🕖", "🕗", "🕘", "🕙", "🕚"};
		String result = "中国标准时间 (UTC+8)：\n" + clock[beijingTime.getHour() % 12] +
				beijingTime.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss")) + "\n" +
				"太平洋时间 (UTC-7/UTC-8)：\n" + clock[ptTime.getHour() % 12] +
				ptTime.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss"));
		coolQMessage.sendGroupMessage(result);
		return result;
	}
	
	//考研倒计时
	public static String kaoyanCountDown(CoolQMessage coolQMessage) {
		LocalDate today = LocalDate.now();
		String days = TimeUtil.calculateDate(new int[]{today.getYear(), today.getMonthValue(), today.getDayOfMonth()}, new int[]{2020, 12, 19}).toString();
		String result = "[CQ:at,qq=all]" + " 距离 2021 考研还有 " + days + " 天！";
		coolQMessage.sendSpecificGroupMessage((long) 857035739, result);
		return result;
	}
}
