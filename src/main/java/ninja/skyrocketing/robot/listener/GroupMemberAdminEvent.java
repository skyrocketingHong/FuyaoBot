package ninja.skyrocketing.robot.listener;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.*;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.robot.entity.BotConfig;
import ninja.skyrocketing.robot.messages.LogMessage;
import org.jetbrains.annotations.NotNull;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-11 011 21:25:50
 * @Version 1.0
 */

public class GroupMemberAdminEvent extends SimpleListenerHost {
	@EventHandler
	public void onJoin(MemberJoinEvent event) {
		MessageChainBuilder messages = new MessageChainBuilder() {{
			add("欢迎 ");
			add(new At(event.getMember()));
			add("\n" + "你是第" + (event.getGroup().getMembers().size() + 1) + "名群员。\n");
			add("记得阅读群公告（如果有的话）哦！");
		}};
		event.getGroup().sendMessage(messages.asMessageChain());
	}
	
	@EventHandler
	public void onLeave(MemberLeaveEvent.Quit event) {
		MessageChainBuilder messages = new MessageChainBuilder() {{
			add("⚠ 群员减少提醒\n" + "群员" +
					event.getMember().getId() +
					"已退出群聊。");
		}};
		event.getGroup().sendMessage(messages.asMessageChain());
	}
	
	@EventHandler
	public void onMute(MemberMuteEvent event) {
		MessageChainBuilder messages = new MessageChainBuilder() {{
			add("⚠ 群员被禁言提醒\n" + "群员" +
					event.getGroup().get(event.getMember().getId()).getNameCard() + " (" + event.getMember().getId() + ") " +
					"已被管理员" + event.getGroup().get(event.getOperator().getId()).getNameCard() + " (" + event.getOperator().getId() + ") " +
					"禁言，解封时间：" + DateUtil.offsetSecond(new DateTime(), event.getDurationSeconds()));
		}};
		event.getGroup().sendMessage(messages.asMessageChain());
	}
	
	@EventHandler
	public void onUnmute(MemberUnmuteEvent event) {
		MessageChainBuilder messages = new MessageChainBuilder() {{
			add("⚠ 群员被解除禁言提醒\n" + "群员" +
					event.getMember().getId() +
					"已被管理员" + event.getGroup().get(event.getOperator().getId()).getNameCard() + " (" + event.getOperator().getId() + ") " +
					"解除禁言。");
		}};
		event.getGroup().sendMessage(messages.asMessageChain());
	}
	
	@EventHandler
	public void onBotMute(BotMuteEvent event) {
		MessageChainBuilder messages = LogMessage.logMessage("FATAL");
		messages.add("机器人被禁言" + "\n" +
				"1. 解封时间：" + DateUtil.offsetSecond(new DateTime(), event.getDurationSeconds()) + "\n" +
				"2. 群名：" + event.getGroup().getName() + "\n" +
				"3. 群号：" + event.getGroup().getId() + "\n" +
				"4. 操作人：" + event.getOperator().getId() + " " + event.getOperator().getNameCard());
		for (Long id : BotConfig.getAdminGroups()) {
			event.getBot().getGroup(id).sendMessage(messages.asMessageChain());
		}
	}
	
	@EventHandler
	public void onBotUnmute(BotUnmuteEvent event) {
		MessageChainBuilder messages = LogMessage.logMessage("FATAL");
		messages.add("机器人被解除禁言" + "\n" +
				"1. 群名：" + event.getGroup().getName() + "\n" +
				"2. 群号：" + event.getGroup().getId() + "\n" +
				"3. 操作人：" + event.getOperator().getId() + " " + event.getOperator().getNameCard());
		for (Long id : BotConfig.getAdminGroups()) {
			event.getBot().getGroup(id).sendMessage(messages.asMessageChain());
		}
	}
	
	@EventHandler
	public void onBotKick(BotLeaveEvent.Active event) {
		MessageChainBuilder messages = LogMessage.logMessage("FATAL");
		messages.add("机器人被移除群聊" + "\n" +
				"1. 群名：" + event.getGroup().getName() + "\n" +
				"2. 群号：" + event.getGroup().getId() + "\n"
		);
		for (Long id : BotConfig.getAdminGroups()) {
			event.getBot().getGroup(id).sendMessage(messages.asMessageChain());
		}
	}
	
	@EventHandler
	public void onBotJoin(BotJoinGroupEvent.Invite event) {
		MessageChainBuilder messages = LogMessage.logMessage("FATAL");
		messages.add("机器人加入群聊" + "\n" +
				"1. 群名：" + event.getGroup().getName() + "\n" +
				"2. 群号：" + event.getGroup().getId() + "\n" +
				"3. 邀请人：" + event.getInvitor().getNameCard() + " " + event.getInvitor().getId());
		for (Long id : BotConfig.getAdminGroups()) {
			event.getBot().getGroup(id).sendMessage(messages.asMessageChain());
		}
	}

//	@EventHandler
//	public void onBotRecalled(MessageRecallEvent.GroupRecall event) {
//		MessageChainBuilder messages = new MessageChainBuilder() {{
//			add("👀群员撤回消息提醒\n" + event.getGroup().get(event.getOperator().getId()).getNameCard() + " (" + event.getOperator().getId() + ") " +
//					"撤回了" +
//					event.getGroup().get(event.getAuthorId()).getNameCard() + " (" + event.getAuthorId() + ") " +
//					"在 " +
//					TimeUtil.reformatDateTimeOfTimestamp(event.getMessageTime()) +
//					" 发的一条消息。");
//		}};
//		event.getGroup().sendMessage(messages.asMessageChain());
//	}
	
	@Override
	public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
		// 处理事件处理时抛出的异常
		System.out.println(context + " " + exception);
	}
}
