package ninja.skyrocketing.util;

import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.robot.entity.MessageEntity;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class InvokeUtil {
	public static Message runByInvoke(String str, MessageEntity messageEntity) throws Exception {
		String[] className = str.split("\\.");
		Class<?> clz = Class.forName("ninja.skyrocketing.robot.message." + className[0]);
		Method method = clz.getMethod(className[1], MessageEntity.class);
		Constructor<?> constructor = clz.getConstructor();
		Object object = constructor.newInstance();
		return (Message) method.invoke(object, messageEntity);
	}
}