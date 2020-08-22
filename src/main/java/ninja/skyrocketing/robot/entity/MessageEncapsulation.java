package ninja.skyrocketing.robot.entity;

import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;

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
	
	public Message atSomeone(String resultMsg) {
		if (groupId == 1L) {
			MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
			messageChainBuilder.add("私聊使用群聊功能");
			return messageChainBuilder.asMessageChain().plus(resultMsg);
		}
		return new At(groupMessageEvent.getSender()).plus(resultMsg);
	}
	
	public Message sendMsg(String resultMsg) {
		MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
		messageChainBuilder.add(resultMsg);
		return messageChainBuilder.asMessageChain();
	}
	
	public Message notSudo() {
		return new PlainText(getUserId() + " is not in the sudoers file. This incident will be reported.");
	}
}
