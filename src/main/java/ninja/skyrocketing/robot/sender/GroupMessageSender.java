package ninja.skyrocketing.robot.sender;

import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.robot.entity.BotConfig;
import ninja.skyrocketing.robot.entity.MessageEncapsulation;
import ninja.skyrocketing.robot.entity.datebase.Trigger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class GroupMessageSender {
	public static Message Sender(GroupMessageEvent event) throws Exception {
		MessageEncapsulation messageEntity = new MessageEncapsulation(event);
		String className = matchedClass(messageEntity);
		if (className != null) {
			return runByInvoke(className, messageEntity);
		}
		return null;
	}
	
	public static Message Sender(Message message) {
		return message;
	}
	
	/**
	 * 根据消息获取对应的实现类
	 **/
	public static String matchedClass(MessageEncapsulation messageEntity) {
		String msg = messageEntity.getMsg();
		for (Trigger trigger : BotConfig.getTriggers()) {
			if (trigger.isEnable()) {
				if (msg.matches(trigger.getKeyword())) {
					return trigger.getImplementation();
				}
			}
		}
		return null;
	}
	
	/**
	 * 根据实现类字符串执行对应的代码
	 **/
	public static Message runByInvoke(String str, MessageEncapsulation messageEntity) throws Exception {
		String[] className = str.split("\\.");
		Class<?> clz = Class.forName("ninja.skyrocketing.robot.messages." + className[0]);
		Method method = clz.getMethod(className[1], MessageEncapsulation.class);
		Constructor<?> constructor = clz.getConstructor();
		Object object = constructor.newInstance();
		return (Message) method.invoke(object, messageEntity);
	}
}
