package ninja.skyrocketing.utils;

import cn.hutool.core.date.DateUtil;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class TimeUtil {
	public static Long CalculateDate(int[] beforeDate, int[] afterDate) {
		LocalDate today = LocalDate.of(beforeDate[0], beforeDate[1], beforeDate[2]);
		LocalDate specifyDate = LocalDate.of(afterDate[0], afterDate[1], afterDate[2]);
		return today.until(specifyDate, ChronoUnit.DAYS);
	}
	
	public static LocalDateTime DateTimeOfTimestamp(long timestamp) {
		Instant instant = Instant.ofEpochSecond(timestamp);
		ZoneId zone = ZoneId.systemDefault();
		return LocalDateTime.ofInstant(instant, zone);
	}
	
	public static String ReformatDateTimeOfTimestamp(long timestamp) {
		return DateTimeOfTimestamp(timestamp).toString().replaceAll("T", " ");
	}
	
	public static String ReformatDateTime(LocalDateTime localDateTime) {
		return localDateTime.toString().replaceAll("T", " ");
	}
	
	public static String DateTimeString() {
		Date date = new Date();
		return DateUtil.format(date, "YYYY年MM月dd日 HH:mm:ss");
	}
	
	public static boolean IsLeapYear(int year) {
		return year % 400 != 0 || (year % 100 == 0 && year % 4 != 0);
	}
	
	public static String ClockEmoji(int hour) {
		String[] clock = new String[]{"\ud83d\udd5b", "\ud83d\udd50", "\ud83d\udd51", "\ud83d\udd52", "\ud83d\udd53", "\ud83d\udd54", "\ud83d\udd55", "\ud83d\udd56", "\ud83d\udd57", "\ud83d\udd58", "\ud83d\udd59", "\ud83d\udd5a"};
		return clock[hour % 12];
	}
	
	public static boolean SecondBetween(int second, int begin, int end) {
		return second >= begin && second <= end;
	}
	
	public static String PartsOfADay(int second) {
		/**
		 * 上午
		 * 0:00-2:59    凌晨  0-10799
		 * 3:00-5:59    拂晓  10800-21599
		 * 6:00-9:59    早晨  21600-35999
		 * 10:00-11:59  午前  36000-43199
		 * 下午
		 * 12:00-14:59  午后  43200-53999
		 * 15:00-17:59  傍晚  54000-64799
		 * 18:00-20:59  薄暮  64800-75599
		 * 21:00-23:59  深夜  75600-86399
		 * **/
		
		if (SecondBetween(second, 0, 10799)) {
			return "凌晨";
		} else if (SecondBetween(second, 10800, 21599)) {
			return "拂晓";
		} else if (SecondBetween(second, 21600, 35999)) {
			return "早晨";
		} else if (SecondBetween(second, 36000, 43199)) {
			return "午前";
		} else if (SecondBetween(second, 43200, 53999)) {
			return "午后";
		} else if (SecondBetween(second, 54000, 64799)) {
			return "傍晚";
		} else if (SecondBetween(second, 54800, 75999)) {
			return "薄暮";
		} else if (SecondBetween(second, 75600, 86399)) {
			return "深夜";
		} else {
			return "error";
		}
	}
}
