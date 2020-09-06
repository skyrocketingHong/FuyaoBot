package ninja.skyrocketing.robot.sender;

import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.robot.entity.BotConfig;
import ninja.skyrocketing.robot.entity.MessageEncapsulation;
import ninja.skyrocketing.robot.entity.datebase.Trigger;
import ninja.skyrocketing.utils.InvokeUtil;

public class GroupMessageSender {
	public static Message Sender(GroupMessageEvent event) throws Exception {
		MessageEncapsulation messageEncapsulation = new MessageEncapsulation(event);
		String className = matchedClass(messageEncapsulation);
		if (className != null) {
			return runByInvoke(className, messageEncapsulation);
		}
		return null;
	}
	
	/**
	 * 根据消息获取对应的实现类
	 **/
	public static String matchedClass(MessageEncapsulation messageEncapsulation) {
		String msg = messageEncapsulation.getMsg();
		if (msg.contains("[闪照]")) {
			if (messageEncapsulation.getGroupMessageEvent().getMessage().toString().matches(".*mirai:flash:.*")) {
				msg = messageEncapsulation.getGroupMessageEvent().getMessage().toString();
			} else {
				return null;
			}
		}
		if (msg.equals("[QQ红包]请使用新版手机QQ查收红包。") || msg.equals("[QQ红包]你收到一个红包，请在新版手机QQ查看。")) {
			msg = messageEncapsulation.getGroupMessageEvent().getMessage().toString();
		}
		for (Trigger trigger : BotConfig.getTriggers()) {
			if (msg.matches(trigger.getKeywordRegex())) {
				if (trigger.isEnable()) {
					return trigger.getImplementation();
				} else {
					return "FuncOffMessage.FuncOff";
				}
			}
		}
		return null;
	}
	
	/**
	 * 根据实现类字符串执行对应的代码
	 **/
	public static Message runByInvoke(String str, MessageEncapsulation messageEncapsulation) throws Exception {
		return InvokeUtil.RunByInvoke(str, messageEncapsulation);
	}
}
