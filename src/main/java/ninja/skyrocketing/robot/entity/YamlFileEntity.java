package ninja.skyrocketing.robot.entity;

import java.util.List;
import java.util.Map;

public class YamlFileEntity {
	public static Map<String, Object> allMap;
	
	public YamlFileEntity(Map<String, Object> allMap) {
		YamlFileEntity.allMap = allMap;
	}
	
	public Map<String, Object> getAllMap() {
		return allMap;
	}
	
	public void setAllMap(Map<String, Object> allMap) {
		YamlFileEntity.allMap = allMap;
	}
	
	public void delAllMap() {
		allMap = null;
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
	
	public boolean isBanedUser(Long id) {
		List<String> banedIdList = getIdList().get("baneduser");
		for (int i = 0; i < banedIdList.size(); i++) {
			if (getIdList().get("baneduser").get(i).equals(id.toString())) {
				return true;
			}
		}
		return false;
	}
}
