package ninja.skyrocketing.robot.entity;

import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChain;

public class MessageEncapsulation {
	String msg;
	Long groupId;
	Long userId;
	GroupMessageEvent groupMessageEvent;
	FriendMessageEvent friendMessageEvent;
	
	public MessageEncapsulation() {
	
	}
	
	public MessageEncapsulation(GroupMessageEvent event) {
		this.msg = event.getMessage().contentToString();
		this.groupId = event.getGroup().getId();
		this.userId = event.getSender().getId();
		this.groupMessageEvent = event;
	}
	
	public MessageEncapsulation(FriendMessageEvent event) {
		this.msg = event.getMessage().contentToString();
		this.groupId = 1L;
		this.userId = event.getSender().getId();
		this.friendMessageEvent = event;
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
	
	public Contact getContact() {
		return getGroupId() == 1L ? getFriend() : getGroup();
	}
	
	public MessageChain getGroupMessageChain() {
		return this.getGroupMessageEvent().getMessage();
	}
	
	public Group getGroup() {
		return this.groupMessageEvent.getGroup();
	}
	
	public Friend getFriend() {
		return this.friendMessageEvent.getFriend();
	}
}
