package ninja.skyrocketing.robot.entity;

import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.PlainText;

public class CoolQMessage {
	String msg;
	Long groupId;
	Long userId;
	GroupMessageEvent groupMessageEvent;
	FriendMessageEvent friendMessageEvent;
	YamlFile yamlFile;
	
	public CoolQMessage(GroupMessageEvent event, YamlFile yamlFile) {
		this.msg = event.getMessage().toString().replaceAll("\\[mirai:.*:\\d+,\\d+\\]$?","");
		this.groupId = event.getGroup().getId();
		this.userId = event.getSender().getId();
		this.groupMessageEvent = event;
		this.yamlFile = yamlFile;
	}
	
	public CoolQMessage(FriendMessageEvent event, YamlFile yamlFile) {
		this.msg = event.getMessage().get(1).toString();
		this.userId = event.getSender().getId();
		this.friendMessageEvent = event;
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
	
	public GroupMessageEvent getGroupMessageEvent() {
		return this.groupMessageEvent;
	}
	
	public FriendMessageEvent getFriendMessageEvent() {
		return this.friendMessageEvent;
	}
	
	public YamlFile getYamlFile() {
		return this.yamlFile;
	}
	
	public Message atSomeone(String resultMsg) {
		Message message = new At(groupMessageEvent.getSender());
		return message.plus(resultMsg);
	}
	
	public Message sendMsg(String resultMsg) {
		return new PlainText(resultMsg);
	}
}
