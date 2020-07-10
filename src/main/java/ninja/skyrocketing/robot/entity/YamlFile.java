package ninja.skyrocketing.robot.entity;

import java.util.List;
import java.util.Map;

public class YamlFile {
	private Map<String, Object> allMap;
	
	public YamlFile(Map<String, Object> allMap) {
		this.allMap = allMap;
	}

	public Map<String, Object> getAllMap() {
		return allMap;
	}

	public void delAllMap() {
		this.allMap = null;
	}

	public void setAllMap(Map<String, Object> allMap) {
		this.allMap = allMap;
	}

	public Map<String, String> getNoteAndFunc() {
		return (Map<String, String>) getAllMap().get("noteandfunc");
	}

	public Map<String, Object> getReplyMap() {
		return (Map<String, Object>) getAllMap().get("reply");
	}

	public Map<String, String> getReplyEqualsMap() {
		return (Map<String, String>) getReplyMap().get("equalsmap");
	}

	public List<String> getMatchesList() {
		return (List<String>) getReplyMap().get("matcheslist");
	}

	public Map<String, List<String>> getIdList() {
		return (Map<String, List<String>>) getAllMap().get("id");
	}
	
	public Map<String, String> getConfigList() {
		return (Map<String, String>) getAllMap().get("config");
	}
	
	public Map<Long, Integer> getSignInMap() {
		return (Map<Long, Integer>) getAllMap().get("singin");
	}
	
	public List<String> getBotList() {
		return (List<String>) getAllMap().get("bot");
	}
	
	public Long getBotQQ() {
		return Long.parseLong(getBotList().get(0));
	}
	
	public String getBotPassword() {
		return getBotList().get(1);
	}
}
