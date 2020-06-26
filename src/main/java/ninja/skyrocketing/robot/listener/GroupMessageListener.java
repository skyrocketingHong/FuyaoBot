package ninja.skyrocketing.robot.listener;

import cc.moecraft.icq.event.EventHandler;
import cc.moecraft.icq.event.IcqListener;
import cc.moecraft.icq.event.events.message.EventGroupMessage;
import ninja.skyrocketing.robot.pojo.CoolQMessage;
import ninja.skyrocketing.robot.pojo.YamlFile;
import ninja.skyrocketing.util.InvokeUtil;
import ninja.skyrocketing.util.MatchUtil;
import ninja.skyrocketing.util.RandomUtil;

public class GroupMessageListener extends IcqListener {
	static YamlFile yamlFile;
	
	public GroupMessageListener() {
	}
	
	public GroupMessageListener(YamlFile ymal) {
		yamlFile = ymal;
	}
	@EventHandler
	public void onGroupEvent(EventGroupMessage event) throws Exception {
		CoolQMessage coolQMessage = new CoolQMessage(event.getMessage(), event.getGroupId(), event.getSenderId(), event, yamlFile);
		int randomNum = RandomUtil.getRandomNum(100);
		boolean userInBlacklist = yamlFile.getIdList().get("user").contains(coolQMessage.getUserId());
		boolean groupInBlacklist = yamlFile.getIdList().get("group").contains(coolQMessage.getGroupId());
		
		if(!userInBlacklist){
			String className = MatchUtil.matchedClass(yamlFile, coolQMessage.getMsg(), randomNum);
			if (className != null) {
				InvokeUtil.runByInvoke(className, coolQMessage);
			}
			
			if(randomNum > 98 && !groupInBlacklist) {
				coolQMessage.sendGroupSelfMessage();
			}
		}
	}
}
