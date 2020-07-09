package ninja.skyrocketing.util;

import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.robot.entity.CoolQMessage;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class InvokeUtil {
	public static Message runByInvoke(String str, CoolQMessage coolQMessage) throws Exception {
		String[] className = str.split("\\.");
		Class<?> clz = Class.forName("ninja.skyrocketing.robot.message." + className[0]);
		Method method = clz.getMethod(className[1], CoolQMessage.class);
		Constructor<?> constructor = clz.getConstructor();
		Object object = constructor.newInstance();
		return (Message) method.invoke(object, coolQMessage);
	}
}