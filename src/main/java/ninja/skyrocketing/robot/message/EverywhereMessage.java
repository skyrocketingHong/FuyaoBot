package ninja.skyrocketing.robot.message;

import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import ninja.skyrocketing.robot.pojo.CoolQMessage;
import ninja.skyrocketing.robot.pojo.YamlFile;
import ninja.skyrocketing.util.InvokeUtil;
import ninja.skyrocketing.util.MatchUtil;
import ninja.skyrocketing.util.RandomUtil;

public class EverywhereMessage {
	public static String Sender(EventGroupMessage event, YamlFile yamlFile) throws Exception {
		CoolQMessage coolQMessage = new CoolQMessage(event.getMessage(), event.getGroupId(), event.getSenderId(), event, yamlFile);
		boolean userInBlacklist = yamlFile.getIdList().get("user").contains(coolQMessage.getUserId().toString());
		boolean groupInBlacklist = yamlFile.getIdList().get("group").contains(coolQMessage.getGroupId().toString());
		int randomNum = RandomUtil.getRandomNum(100);
		
		if (!userInBlacklist) {
			String className = MatchUtil.matchedClass(yamlFile, coolQMessage.getMsg());
			if (className != null) {
				return InvokeUtil.runByInvoke(className, coolQMessage);
			}
			
			if (randomNum > 98 && !groupInBlacklist) {
				return coolQMessage.sendGroupSelfMessage();
			}
		}
		return null;
	}
	
	public static String Sender(EventPrivateMessage event, YamlFile yamlFile) throws Exception {
		CoolQMessage coolQMessage = new CoolQMessage(event.getMessage(), event.getSenderId(), event, yamlFile);
		boolean userInBlacklist = (yamlFile.getIdList().get("user")).contains(coolQMessage.getUserId().toString());
		if (!userInBlacklist) {
			String className = MatchUtil.matchedClass(yamlFile, coolQMessage.getMsg());
			if (className != null) {
				return InvokeUtil.runByInvoke(className, coolQMessage);
			}
		}
		return null;
	}
}