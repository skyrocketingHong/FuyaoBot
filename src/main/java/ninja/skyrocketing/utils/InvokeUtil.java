package ninja.skyrocketing.utils;

import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.robot.entity.MessageEncapsulation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @Author skyrocketing Hong
 * @Date 2020-08-22 022 11:24:55
 */
public class InvokeUtil {
	public static Message runByInvoke(String str, MessageEncapsulation messageEncapsulation) throws Exception {
		String[] className = str.split("\\.");
		Class<?> clz = Class.forName("ninja.skyrocketing.robot.messages." + className[0]);
		Method method = clz.getMethod(className[1], MessageEncapsulation.class);
		Constructor<?> constructor = clz.getConstructor();
		Object object = constructor.newInstance();
		return (Message) method.invoke(object, messageEncapsulation);
	}
}
