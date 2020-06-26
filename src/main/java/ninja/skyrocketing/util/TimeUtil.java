package ninja.skyrocketing.util;

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
}
