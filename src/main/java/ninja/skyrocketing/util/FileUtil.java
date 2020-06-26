package ninja.skyrocketing.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FileUtil {
	public static String readAsString(InputStream input) throws IOException {
		int n;
		StringBuilder sb = new StringBuilder();
		while ((n = input.read()) != -1) {
			sb.append((char) n);
		}
		return new String(sb.toString().getBytes("iso8859-1"), StandardCharsets.UTF_8);
	}
}
