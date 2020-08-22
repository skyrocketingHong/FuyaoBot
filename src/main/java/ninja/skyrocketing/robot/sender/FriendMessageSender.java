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
 * @Version 1.0
 */
public class FriendMessageSender {
	public static Message Sender(FriendMessageEvent event) throws Exception {
		MessageEncapsulation messageEntity = new MessageEncapsulation(event);
		String className = matchedClass(messageEntity);
		if (className != null) {
			return runByInvoke(className, messageEntity);
		}
		return null;
	}
	
	/**
	 * 根据消息获取对应的实现类
	 **/
	public static String matchedClass(MessageEncapsulation messageEntity) {
		String msg = messageEntity.getMsg();
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
	public static Message runByInvoke(String str, MessageEncapsulation messageEntity) throws Exception {
		return InvokeUtil.runByInvoke(str, messageEntity);
	}
}
