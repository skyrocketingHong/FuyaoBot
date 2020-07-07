package ninja.skyrocketing.robot.handler;

import java.sql.ResultSet;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-07 007 14:58:34
 * @Version 1.0
 */
public interface ResultSetHandlerInterface<T> {
	T handler(ResultSet rSet);
}
