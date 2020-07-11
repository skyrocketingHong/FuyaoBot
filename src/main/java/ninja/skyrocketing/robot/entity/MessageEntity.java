package ninja.skyrocketing.robot.entity;

import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.PlainText;

public class MessageEntity {
	String msg;
	Long groupId;
	Long userId;
	GroupMessageEvent groupMessageEvent;
	FriendMessageEvent friendMessageEvent;
	YamlFileEntity yamlFileEntity;
	
	public MessageEntity(GroupMessageEvent event, YamlFileEntity yamlFileEntity) {
		this.msg = event.getMessage().toString().replaceAll("\\[mirai:.*:\\d+,\\d+\\]$?", "");
		this.groupId = event.getGroup().getId();
		this.userId = event.getSender().getId();
		this.groupMessageEvent = event;
		this.yamlFileEntity = yamlFileEntity;
	}
	
	public MessageEntity(FriendMessageEvent event, YamlFileEntity yamlFileEntity) {
		this.msg = event.getMessage().get(1).toString();
		this.userId = event.getSender().getId();
		this.friendMessageEvent = event;
		this.yamlFileEntity = yamlFileEntity;
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
	
	public YamlFileEntity getYamlFile() {
		return this.yamlFileEntity;
	}
	
	public Message atSomeone(String resultMsg) {
		Message message = new At(groupMessageEvent.getSender());
		return message.plus(resultMsg);
	}
	
	public Message sendMsg(String resultMsg) {
		return new PlainText(resultMsg);
	}
}
