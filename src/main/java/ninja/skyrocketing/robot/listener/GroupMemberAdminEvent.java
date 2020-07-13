package ninja.skyrocketing.robot.listener;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.*;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.robot.entity.YamlFileEntity;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-11 011 21:25:50
 * @Version 1.0
 */
public class GroupMemberAdminEvent extends SimpleListenerHost {
	static YamlFileEntity yamlFileEntity;
	
	public GroupMemberAdminEvent(YamlFileEntity file) {
		yamlFileEntity = file;
	}
	
	@EventHandler
	public void onJoin(MemberJoinEvent event) {
		MessageChainBuilder messages = new MessageChainBuilder() {{
			add("欢迎 ");
			add(new At(event.getMember()));
			add("\n" + "你是第" + (event.getGroup().getMembers().size() + 1) + "名群员。\n");
			add("记得阅读群公告（如果有的话）哦！");
		}};
		event.getGroup().sendMessage(messages.asMessageChain());
		long admin = Long.parseLong(yamlFileEntity.getIdList().get("admingroup").get(0));
		event.getBot().getGroup(admin).sendMessage("⚠ 机器人群管事件提醒 ⚠" + "\n" +
				"① 操作：" + "进群" + "\n" +
				"② 回复消息：" + messages.asMessageChain().contentToString().replaceAll("\\t|\\n", "") + "\n" +
				"③ 群名：" + event.getGroup().getName() + "\n" +
				"④ 群号：" + event.getGroup().getId() + "\n" +
				"⑤ 群名片：" + event.getMember().getNameCard() + "\n" +
				"⑥ QQ号：" + event.getMember().getId());
	}
	
	@EventHandler
	public void onLeave(MemberLeaveEvent.Quit event) {
		MessageChainBuilder messages = new MessageChainBuilder() {{
			add("👀群员减少提醒\n" + "群员" +
					event.getMember().getId() +
					"已退出群聊。");
		}};
		event.getGroup().sendMessage(messages.asMessageChain());
		long admin = Long.parseLong(yamlFileEntity.getIdList().get("admingroup").get(0));
		event.getBot().getGroup(admin).sendMessage("⚠ 机器人群管事件提醒 ⚠" + "\n" +
				"① 操作：" + "退群" + "\n" +
				"② 回复消息：" + messages.asMessageChain().contentToString().replaceAll("\\t|\\n", "") + "\n" +
				"③ 群名：" + event.getGroup().getName() + "\n" +
				"④ 群号：" + event.getGroup().getId() + "\n" +
				"⑤ QQ号：" + event.getMember().getId()
		);
	}
	
	@EventHandler
	public void onMute(MemberMuteEvent event) {
		MessageChainBuilder messages = new MessageChainBuilder() {{
			add("👀群员被禁言提醒\n" + "群员" +
					event.getGroup().get(event.getMember().getId()).getNameCard() + " (" + event.getMember().getId() + ") " +
					"已被管理员" + event.getGroup().get(event.getOperator().getId()).getNameCard() + " (" + event.getOperator().getId() + ") " +
					"禁言，解封时间：" + DateUtil.offsetSecond(new DateTime(), event.getDurationSeconds()));
		}};
		event.getGroup().sendMessage(messages.asMessageChain());
		long admin = Long.parseLong(yamlFileEntity.getIdList().get("admingroup").get(0));
		event.getBot().getGroup(admin).sendMessage("⚠ 机器人群管事件提醒 ⚠" + "\n" +
				"① 操作：" + "禁言" + "\n" +
				"② 回复消息：" + messages.asMessageChain().contentToString().replaceAll("\\t|\\n", "") + "\n" +
				"③ 解封时间：" + DateUtil.offsetSecond(new DateTime(), event.getDurationSeconds()) + "\n" +
				"④ 群名：" + event.getGroup().getName() + "\n" +
				"⑤ 群号：" + event.getGroup().getId() + "\n" +
				"⑥ 操作人：" + event.getOperator().getId() + "\n" +
				"⑦ 被封群员QQ号：" + event.getMember().getId()
		);
	}
	
	@EventHandler
	public void onUnmute(MemberUnmuteEvent event) {
		MessageChainBuilder messages = new MessageChainBuilder() {{
			add("👀群员被解除禁言提醒\n" + "群员" +
					event.getMember().getId() +
					"已被管理员" + event.getGroup().get(event.getOperator().getId()).getNameCard() + " (" + event.getOperator().getId() + ") " +
					"解除禁言。");
		}};
		event.getGroup().sendMessage(messages.asMessageChain());
		long admin = Long.parseLong(yamlFileEntity.getIdList().get("admingroup").get(0));
		event.getBot().getGroup(admin).sendMessage("⚠ 机器人群管事件提醒 ⚠" + "\n" +
				"① 操作：" + "解除禁言" + "\n" +
				"② 回复消息：" + messages.asMessageChain().contentToString().replaceAll("\\t|\\n", "") + "\n" +
				"④ 群名：" + event.getGroup().getName() + "\n" +
				"⑤ 群号：" + event.getGroup().getId() + "\n" +
				"⑥ 操作人：" + event.getOperator().getId() + "\n" +
				"⑦ 被解封群员QQ号：" + event.getMember().getId()
		);
	}
	
	@EventHandler
	public void onBotMute(BotMuteEvent event) {
		long admin = Long.parseLong(yamlFileEntity.getIdList().get("admingroup").get(0));
		event.getBot().getGroup(admin).sendMessage("⚠ 机器人被禁言提醒 ⚠" + "\n" +
				"① 操作：" + "机器人被禁言" + "\n" +
				"② 解封时间：" + DateUtil.offsetSecond(new DateTime(), event.getDurationSeconds()) + "\n" +
				"③ 群名：" + event.getGroup().getName() + "\n" +
				"④ 群号：" + event.getGroup().getId() + "\n" +
				"⑤ 操作人：" + event.getOperator().getId() + " " + event.getOperator().getNameCard()
		);
	}
	
	@EventHandler
	public void onBotUnmute(BotUnmuteEvent event) {
		long admin = Long.parseLong(yamlFileEntity.getIdList().get("admingroup").get(0));
		event.getBot().getGroup(admin).sendMessage("⚠ 机器人被解除禁言提醒 ⚠" + "\n" +
				"① 操作：" + "机器人被解除禁言" + "\n" +
				"② 群名：" + event.getGroup().getName() + "\n" +
				"③ 群号：" + event.getGroup().getId() + "\n" +
				"④ 操作人：" + event.getOperator().getId() + " " + event.getOperator().getNameCard()
		);
	}
	
	@EventHandler
	public void onBotKick(BotLeaveEvent.Active event) {
		long admin = Long.parseLong(yamlFileEntity.getIdList().get("admingroup").get(0));
		event.getBot().getGroup(admin).sendMessage("⚠ 机器人被移出群聊提醒 ⚠" + "\n" +
				"① 操作：" + "机器人被移除群聊" + "\n" +
				"② 群名：" + event.getGroup().getName() + "\n" +
				"③ 群号：" + event.getGroup().getId() + "\n"
		);
	}
	
	@EventHandler
	public void onBotJoin(BotJoinGroupEvent.Invite event) {
		long admin = Long.parseLong(yamlFileEntity.getIdList().get("admingroup").get(0));
		event.getBot().getGroup(admin).sendMessage("⚠ 机器人加入群聊提醒 ⚠" + "\n" +
				"① 操作：" + "机器人加入群聊" + "\n" +
				"② 群名：" + event.getGroup().getName() + "\n" +
				"③ 群号：" + event.getGroup().getId() + "\n" +
				"④ 邀请人：" + event.getInvitor().getNameCard() + " " + event.getInvitor().getId()
		);
	}
	
	@EventHandler
	public void onBotRecalled(MessageRecallEvent.GroupRecall event) {
//		MessageChainBuilder messages = new MessageChainBuilder() {{
//			add("👀群员撤回消息提醒\n" + event.getGroup().get(event.getOperator().getId()).getNameCard() + " (" + event.getOperator().getId() + ") " +
//					"撤回了" +
//					event.getGroup().get(event.getAuthorId()).getNameCard() + " (" + event.getAuthorId() + ") " +
//					"在 " +
//					TimeUtil.reformatDateTimeOfTimestamp(event.getMessageTime()) +
//					" 发的一条消息。");
//		}};
//		event.getGroup().sendMessage(messages.asMessageChain());
		if (event.getAuthorId() == yamlFileEntity.getBotQQ()) {
			long admin = Long.parseLong(yamlFileEntity.getIdList().get("admingroup").get(0));
			event.getBot().getGroup(admin).sendMessage("⚠ 机器人撤回消息提醒 ⚠" + "\n" +
							"① 操作：" + "机器人消息被撤回" + "\n" +
//					"② 回复消息：" + messages.asMessageChain().contentToString().replaceAll("\\t|\\n", "") + "\n" +
							"② 群名：" + event.getGroup().getName() + "\n" +
							"③ 群号：" + event.getGroup().getId() + "\n" +
							"④ 操作人：" + event.getOperator().getId() + " " + event.getOperator().getNameCard()
			);
		}
	}
}
