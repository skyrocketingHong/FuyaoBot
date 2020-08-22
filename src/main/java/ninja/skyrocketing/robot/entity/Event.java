package ninja.skyrocketing.robot.entity;

import net.mamoe.mirai.event.events.*;
import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.GroupMessageEvent;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-14 014 17:51:30
 */
public class Event {
	private GroupMessageEvent groupMessageEvent;
	private FriendMessageEvent friendMessageEvent;
	private MemberJoinEvent memberJoinEvent;
	private MemberLeaveEvent.Quit memberLeaveEvent;
	private MemberMuteEvent memberMuteEvent;
	private MemberUnmuteEvent memberUnmuteEvent;
	private BotMuteEvent botMuteEvent;
	private BotUnmuteEvent botUnmuteEvent;
	private BotLeaveEvent.Active botLeaveEvent;
	private BotJoinGroupEvent.Invite botJoinGroupEvent;
	private MessageRecallEvent.GroupRecall groupMessageRecallEvent;
	private MessageRecallEvent.FriendRecall friendMessageRecallEvent;
	
	public Event(GroupMessageEvent groupMessageEvent) {
		this.groupMessageEvent = groupMessageEvent;
	}
	
	public Event(FriendMessageEvent friendMessageEvent) {
		this.friendMessageEvent = friendMessageEvent;
	}
	
	public Event(MemberJoinEvent memberJoinEvent) {
		this.memberJoinEvent = memberJoinEvent;
	}
	
	public Event(MemberLeaveEvent.Quit memberLeaveEvent) {
		this.memberLeaveEvent = memberLeaveEvent;
	}
	
	public Event(MemberMuteEvent memberMuteEvent) {
		this.memberMuteEvent = memberMuteEvent;
	}
	
	public Event(MemberUnmuteEvent memberUnmuteEvent) {
		this.memberUnmuteEvent = memberUnmuteEvent;
	}
	
	public Event(BotMuteEvent botMuteEvent) {
		this.botMuteEvent = botMuteEvent;
	}
	
	public Event(BotUnmuteEvent botUnmuteEvent) {
		this.botUnmuteEvent = botUnmuteEvent;
	}
	
	public Event(BotLeaveEvent.Active botLeaveEvent) {
		this.botLeaveEvent = botLeaveEvent;
	}
	
	public Event(BotJoinGroupEvent.Invite botJoinGroupEvent) {
		this.botJoinGroupEvent = botJoinGroupEvent;
	}
	
	public Event(MessageRecallEvent.GroupRecall groupMessageRecallEvent) {
		this.groupMessageRecallEvent = groupMessageRecallEvent;
	}
	
	public Event(MessageRecallEvent.FriendRecall friendMessageRecallEvent) {
		this.friendMessageRecallEvent = friendMessageRecallEvent;
	}
	
	public GroupMessageEvent getGroupMessageEvent() {
		return groupMessageEvent;
	}
	
	public void setGroupMessageEvent(GroupMessageEvent groupMessageEvent) {
		this.groupMessageEvent = groupMessageEvent;
	}
	
	public FriendMessageEvent getFriendMessageEvent() {
		return friendMessageEvent;
	}
	
	public void setFriendMessageEvent(FriendMessageEvent friendMessageEvent) {
		this.friendMessageEvent = friendMessageEvent;
	}
	
	public MemberJoinEvent getMemberJoinEvent() {
		return memberJoinEvent;
	}
	
	public void setMemberJoinEvent(MemberJoinEvent memberJoinEvent) {
		this.memberJoinEvent = memberJoinEvent;
	}
	
	public MemberLeaveEvent.Quit getMemberLeaveEvent() {
		return memberLeaveEvent;
	}
	
	public void setMemberLeaveEvent(MemberLeaveEvent.Quit memberLeaveEvent) {
		this.memberLeaveEvent = memberLeaveEvent;
	}
	
	public MemberMuteEvent getMemberMuteEvent() {
		return memberMuteEvent;
	}
	
	public void setMemberMuteEvent(MemberMuteEvent memberMuteEvent) {
		this.memberMuteEvent = memberMuteEvent;
	}
	
	public MemberUnmuteEvent getMemberUnmuteEvent() {
		return memberUnmuteEvent;
	}
	
	public void setMemberUnmuteEvent(MemberUnmuteEvent memberUnmuteEvent) {
		this.memberUnmuteEvent = memberUnmuteEvent;
	}
	
	public BotMuteEvent getBotMuteEvent() {
		return botMuteEvent;
	}
	
	public void setBotMuteEvent(BotMuteEvent botMuteEvent) {
		this.botMuteEvent = botMuteEvent;
	}
	
	public BotUnmuteEvent getBotUnmuteEvent() {
		return botUnmuteEvent;
	}
	
	public void setBotUnmuteEvent(BotUnmuteEvent botUnmuteEvent) {
		this.botUnmuteEvent = botUnmuteEvent;
	}
	
	public BotLeaveEvent.Active getBotLeaveEvent() {
		return botLeaveEvent;
	}
	
	public void setBotLeaveEvent(BotLeaveEvent.Active botLeaveEvent) {
		this.botLeaveEvent = botLeaveEvent;
	}
	
	public BotJoinGroupEvent.Invite getBotJoinGroupEvent() {
		return botJoinGroupEvent;
	}
	
	public void setBotJoinGroupEvent(BotJoinGroupEvent.Invite botJoinGroupEvent) {
		this.botJoinGroupEvent = botJoinGroupEvent;
	}
	
	public MessageRecallEvent.GroupRecall getGroupMessageRecallEvent() {
		return groupMessageRecallEvent;
	}
	
	public void setGroupMessageRecallEvent(MessageRecallEvent.GroupRecall groupMessageRecallEvent) {
		this.groupMessageRecallEvent = groupMessageRecallEvent;
	}
	
	public MessageRecallEvent.FriendRecall getFriendMessageRecallEvent() {
		return friendMessageRecallEvent;
	}
	
	public void setFriendMessageRecallEvent(MessageRecallEvent.FriendRecall friendMessageRecallEvent) {
		this.friendMessageRecallEvent = friendMessageRecallEvent;
	}
}
