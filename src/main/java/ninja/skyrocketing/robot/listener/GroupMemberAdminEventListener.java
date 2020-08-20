package ninja.skyrocketing.robot.listener;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.*;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.RobotApplication;
import ninja.skyrocketing.robot.entity.BotConfig;
import ninja.skyrocketing.robot.entity.datebase.UserExpIds;
import org.jetbrains.annotations.NotNull;

import static ninja.skyrocketing.robot.sender.AdminListenerMessageSender.ErrorMessageSender;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-11 011 21:25:50
 * @Version 1.0
 */

public class GroupMemberAdminEventListener extends SimpleListenerHost {
	//群里来新人了
	@EventHandler
	public ListeningStatus onJoin(MemberJoinEvent event) {
		MessageChainBuilder messages = new MessageChainBuilder() {{
			add("👏 欢迎第" + (event.getGroup().getMembers().size() + 1) + "名群员。" + "\n");
			add(new At(event.getMember()));
			add("\n" + "记得阅读群公告（如果有的话）哦！");
		}};
		event.getGroup().sendMessage(messages.asMessageChain());
		return ListeningStatus.LISTENING;
	}
	
	//群里有人溜了
	@EventHandler
	public ListeningStatus onQuit(MemberLeaveEvent.Quit event) {
		MessageChainBuilder messages = new MessageChainBuilder() {{
			add("⚠ 群员减少提醒\n" + "群员" +
					event.getMember().getNick() + " (" +
					event.getMember().getId() + ") " +
					"悄悄地溜了...");
		}};
		try {
			UserExpIds userExpIds = new UserExpIds(event.getMember().getId(), event.getGroup().getId());
			BotConfig.userExp.deleteByUserExpIds(userExpIds);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		event.getGroup().sendMessage(messages.asMessageChain());
		return ListeningStatus.LISTENING;
	}
	
	//群里有人被踢了
	@EventHandler
	public ListeningStatus onKick(MemberLeaveEvent.Kick event) {
		MessageChainBuilder messages = new MessageChainBuilder() {{
			add("⚠ 群员减少提醒\n" + "群员" +
					event.getMember().getNick() + " (" +
					event.getMember().getId() + ") " +
					"已被" +
					event.getOperator().getNick() + " (" +
					event.getOperator().getId() + ") " +
					"移出群聊。");
		}};
		try {
			UserExpIds userExpIds = new UserExpIds(event.getMember().getId(), event.getGroup().getId());
			BotConfig.userExp.deleteByUserExpIds(userExpIds);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		event.getGroup().sendMessage(messages.asMessageChain());
		return ListeningStatus.LISTENING;
	}
	
	//群员被修改了权限
	@EventHandler
	public ListeningStatus onSetAdmin(MemberPermissionChangeEvent event) {
		MessageChainBuilder messages = new MessageChainBuilder() {{
			add("⚠ 群员权限变动\n" +
					"群员" +
					event.getMember().getNick() + " (" +
					event.getMember().getId() + ") " +
					"已被设置为" +
					event.getNew().name() +
					"。"
			);
		}};
		event.getGroup().sendMessage(messages.asMessageChain());
		return ListeningStatus.LISTENING;
	}
	
	//群员被口球
	@EventHandler
	public ListeningStatus onMute(MemberMuteEvent event) {
		MessageChainBuilder messages = new MessageChainBuilder() {{
			add("⚠ 群员被禁言提醒\n" + "群员" +
					event.getGroup().get(event.getMember().getId()).getNameCard() + " (" + event.getMember().getId() + ") " +
					"已被管理员" + event.getGroup().get(event.getOperator().getId()).getNameCard() + " (" + event.getOperator().getId() + ") " +
					"禁言，解封时间：" + DateUtil.offsetSecond(new DateTime(), event.getDurationSeconds()));
		}};
		event.getGroup().sendMessage(messages.asMessageChain());
		return ListeningStatus.LISTENING;
	}
	
	//群员被解禁
	@EventHandler
	public ListeningStatus onUnmute(MemberUnmuteEvent event) {
		MessageChainBuilder messages = new MessageChainBuilder() {{
			add("⚠ 群员被解除禁言提醒\n" + "群员" +
					event.getMember().getId() +
					"已被管理员" + event.getGroup().get(event.getOperator().getId()).getNameCard() + " (" + event.getOperator().getId() + ") " +
					"解除禁言。");
		}};
		event.getGroup().sendMessage(messages.asMessageChain());
		return ListeningStatus.LISTENING;
	}
	
	//处理事件处理时抛出的异常
	@Override
	public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
		ErrorMessageSender(context, exception, RobotApplication.bot);
	}
}
