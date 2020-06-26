package ninja.skyrocketing.robot.pojo;

import cc.moecraft.icq.event.events.message.EventGroupMessage;

public class CoolQMessage {
	String msg;
	Long groupId;
	Long userId;
	EventGroupMessage event;
	YamlFile yamlFile;
	
	public CoolQMessage(String msg, Long groupId, Long userId, EventGroupMessage event, YamlFile yamlFile) {
		this.msg = msg;
		this.groupId = groupId;
		this.userId = userId;
		this.event = event;
		this.yamlFile = yamlFile;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Long getGroupId() {
		return groupId;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public EventGroupMessage getEvent() {
		return event;
	}
	
	public YamlFile getYamlFile() {
		return yamlFile;
	}
	
	public void sendGroupMessage(String resultMessage) {
		this.getEvent().respond(resultMessage, false);
	}
	
	public void sendGroupSelfMessage() {
		this.getEvent().respond(this.getMsg(), false);
	}
	
	public void sendSpecificGroupMessage(Long groupId, String resultMessage) {
		this.getEvent().respond(resultMessage, false);
	}
	
	public String atSomeone() {
		return "[CQ:at,qq=" + this.userId + "]";
	}
}
