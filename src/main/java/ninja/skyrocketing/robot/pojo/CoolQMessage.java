package ninja.skyrocketing.robot.pojo;

import cc.moecraft.icq.event.events.message.EventGroupMessage;
import cc.moecraft.icq.event.events.message.EventPrivateMessage;

public class CoolQMessage {
	String msg;
	Long groupId;
	Long userId;
	EventGroupMessage eventG;
	EventPrivateMessage eventP;
	YamlFile yamlFile;
	
	public CoolQMessage(String msg, Long groupId, Long userId, EventGroupMessage event, YamlFile yamlFile) {
		this.msg = msg;
		this.groupId = groupId;
		this.userId = userId;
		this.eventG = event;
		this.yamlFile = yamlFile;
	}
	
	public CoolQMessage(String msg, Long userId, EventPrivateMessage event, YamlFile yamlFile) {
		this.msg = msg;
		this.userId = userId;
		this.eventP = event;
		this.yamlFile = yamlFile;
	}
	
	public String getMsg() {
		return this.msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Long getGroupId() {
		return this.groupId;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	
	public EventGroupMessage getEventG() {
		return this.eventG;
	}
	
	public EventPrivateMessage getEventP() {
		return this.eventP;
	}
	
	public YamlFile getYamlFile() {
		return this.yamlFile;
	}
	
	public void sendGroupMessage(String resultMessage) {
		this.getEventG().respond(resultMessage, false);
	}
	
	public String sendGroupSelfMessage() {
		return this.getMsg();
	}
	
	public void sendSpecificGroupMessage(Long groupId, String resultMessage) {
		this.getEventG().respond(resultMessage, false);
	}
	
	public String atSomeone() {
		return "[CQ:at,qq=" + this.userId + "]";
	}
}
