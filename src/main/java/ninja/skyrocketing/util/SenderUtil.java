package ninja.skyrocketing.robot.sender;

import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;
import ninja.skyrocketing.robot.pojo.CoolQMessage;
import ninja.skyrocketing.robot.pojo.YamlFile;
import ninja.skyrocketing.util.InvokeUtil;
import ninja.skyrocketing.util.MatchUtil;

public class EverywhereMessage {
	public static String sender(YamlFile yamlFile, CoolQMessage coolQMessage) throws Exception {
		boolean userInBlacklist = yamlFile.getIdList().get("user").contains(coolQMessage.getUserId().toString());
		if (!userInBlacklist) {
			String className = MatchUtil.matchedClass(coolQMessage);
			if (className != null) {
				return InvokeUtil.runByInvoke(className, coolQMessage);
			}
		}
		return null;
	}
	
	public static String groupSender(EventGroupMessage event, YamlFile yamlFile) throws Exception {
		CoolQMessage coolQMessage = new CoolQMessage(event.getMessage(), event.getGroupId(), event.getSenderId(), event, yamlFile);
		return sender(yamlFile, coolQMessage);
	}
	
	public static String privateSender(EventPrivateMessage event, YamlFile yamlFile) throws Exception {
		CoolQMessage coolQMessage = new CoolQMessage(event.getMessage(), event.getSenderId(), event, yamlFile);
		return sender(yamlFile, coolQMessage);
	}
}