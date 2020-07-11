package ninja.skyrocketing.robot.listener;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.event.events.MemberLeaveEvent;
import net.mamoe.mirai.event.events.MemberMuteEvent;
import net.mamoe.mirai.event.events.MemberUnmuteEvent;
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
	public void onLeave(MemberLeaveEvent event) {
		MessageChainBuilder messages = new MessageChainBuilder() {{
			add("群员" +
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
			add("群员" +
					event.getMember().getId() +
					"已被管理员" + event.getOperator().getId() +
					"禁言，" + DateUtil.offsetSecond(new DateTime(), event.getDurationSeconds()) + "解封。");
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
			add("群员" +
					event.getMember().getId() +
					"已被管理员" + event.getOperator().getId() +
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
}
