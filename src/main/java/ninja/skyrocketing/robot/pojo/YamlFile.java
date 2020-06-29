package ninja.skyrocketing.robot.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YamlFile {
	private Map<String, Object> allMap;
	private List<List<String>> allMatchLists = new ArrayList<>();

	public YamlFile() {
	}

	public YamlFile(Map<String, Object> allMap) {
		this.allMap = allMap;
		String[] listType = {"containslist", "matcheslist", "randomlist"};
		for (String s : listType) {
			this.allMatchLists.add((List<String>) getReplyMap().get(s));
		}
	}

	public Map<String, Object> getAllMap() {
		return allMap;
	}

	public void delAllMap() {
		this.allMap = null;
		this.allMatchLists = null;
	}

	public void setAllMap(Map<String, Object> allMap) {
		this.allMap = allMap;
		String[] listType = {"containslist", "matcheslist", "randomlist"};
		for (String s : listType) {
			this.allMatchLists.add((List<String>) getReplyMap().get(s));
		}
	}

	public Map<String, String> getNoteAndFunc() {
		return (Map<String, String>) allMap.get("noteandfunc");
	}

	public Map<String, Object> getReplyMap() {
		return (Map<String, Object>) allMap.get("reply");
	}

	public Map<String, String> getReplyEqualsMap() {
		return (Map<String, String>) getReplyMap().get("equalsmap");
	}

	public List<List<String>> getAllMatchList() {
		return allMatchLists;
	}

	public Map<String, List<String>> getIdList() {
		return (Map<String, List<String>>) allMap.get("id");
	}
	
	public Map<String, String> getConfigList() {
		return (Map<String, String>) allMap.get("config");
	}
}
