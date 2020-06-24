package ninja.skyrocketing.qqrobot.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "reply")
public class Matches {
	private List<String> maps = new ArrayList<>();
	
	public String getReply(String msg, Integer integer) {
		for (String str : maps) {
			String[] strSplit = str.split(":");
			if (strSplit[0].equals("equals") && strSplit[1].equals(msg))
				return strSplit[2];
			if (strSplit[0].equals("contains") && msg.contains(strSplit[1]))
				return strSplit[2];
			if (strSplit[0].equals("matches") && msg.matches(strSplit[1]))
				return strSplit[2];
			if (strSplit[0].equals("random") && msg.matches(strSplit[1]) && integer > 90) {
				return strSplit[2];
			}
		}
		return null;
	}
	
	public void setMaps(List<String> maps) {
		this.maps = maps;
	}
}
