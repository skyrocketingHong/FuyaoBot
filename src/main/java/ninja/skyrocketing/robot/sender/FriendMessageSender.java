package ninja.skyrocketing.robot.sender;

import net.mamoe.mirai.message.FriendMessageEvent;
import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.robot.entity.BotConfig;
import ninja.skyrocketing.robot.entity.MessageEncapsulation;
import ninja.skyrocketing.robot.entity.datebase.Trigger;
import ninja.skyrocketing.utils.InvokeUtil;

/**
 * @Author skyrocketing Hong
 * @Date 2020-08-22 022 11:16:04
 */
public class FriendMessageSender {
	public static Message Sender(FriendMessageEvent event) throws Exception {
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
