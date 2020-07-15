package ninja.skyrocketing.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class TimeUtil {
	public static Long calculateDate(int[] beforeDate, int[] afterDate) {
		LocalDate today = LocalDate.of(beforeDate[0], beforeDate[1], beforeDate[2]);
		LocalDate specifyDate = LocalDate.of(afterDate[0], afterDate[1], afterDate[2]);
		return today.until(specifyDate, ChronoUnit.DAYS);
	}
	
	public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
		Instant instant = Instant.ofEpochSecond(timestamp);
		ZoneId zone = ZoneId.systemDefault();
		return LocalDateTime.ofInstant(instant, zone);
	}
	
	public static String reformatDateTimeOfTimestamp(long timestamp) {
		return getDateTimeOfTimestamp(timestamp).toString().replaceAll("T", " ");
	}
	
	public static String reformatDateTime(LocalDateTime localDateTime) {
		return localDateTime.toString().replaceAll("T", " ");
	}
	
	public static String getDateTimeString() {
		LocalDateTime dateTime = LocalDateTime.now();
		return dateTime.toString().replace("T", " ");
	}
	
	public static boolean isLeapYear(int year) {
		return year % 400 != 0 || (year % 100 == 0 && year % 4 != 0);
	}
	
	public static String getClockEmoji(int hour) {
		String[] clock = new String[]{"\ud83d\udd5b", "\ud83d\udd50", "\ud83d\udd51", "\ud83d\udd52", "\ud83d\udd53", "\ud83d\udd54", "\ud83d\udd55", "\ud83d\udd56", "\ud83d\udd57", "\ud83d\udd58", "\ud83d\udd59", "\ud83d\udd5a"};
		return clock[hour % 12];
	}
}
