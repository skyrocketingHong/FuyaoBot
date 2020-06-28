package ninja.skyrocketing.robot.pojo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YamlFile {
	private Map<String, Object> allMap;
	public YamlFile() {
	}
	
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
		return (Map<String, String>) allMap.get("noteandfunc");
	}
	
	public Map<String, Object> getReplyMap() {
		return (Map<String, Object>) allMap.get("reply");
	}
	
	public Map<String, String> getReplyEqualsMap() {
		return (Map<String, String>) getReplyMap().get("equalsmap");
	}

	public List<List<String>> getAllMatchList() {
		List<List<String>> lists = new ArrayList<>();
		String[] listType = {"containslist", "matcheslist", "randomlist"};
		for (String s : listType) {
			lists.add((List<String>) getReplyMap().get(s));
		}
		return lists;
	}
	
	public Map<String, List<String>> getIdList() {
		return (Map<String, List<String>>) allMap.get("id");
	}
}
