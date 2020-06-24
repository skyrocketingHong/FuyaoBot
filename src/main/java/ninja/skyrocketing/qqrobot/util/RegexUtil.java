package ninja.skyrocketing.qqrobot.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {
	//通过正则表达式提取信息
	public static List<String> extractMessage(String regex, String context) {
		Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(context);
		ArrayList<String> list = new ArrayList<String>();
		while (m.find()) {
			list.add(m.group());
		}
		return list;
	}
	
	public String qxInfo(String reg, List<String> list) {
		String result = "";
		Pattern clear = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
		for (String s : list) {
			Matcher m1 = clear.matcher(s);
			while (m1.find() && !result.contains(m1.group())) {
				result += m1.group() + ",";
			}
		}
		if (!result.equals("")) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}
}
