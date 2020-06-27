package ninja.skyrocketing.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import ninja.skyrocketing.robot.pojo.CoolQMessage;

public class InvokeUtil {
	public InvokeUtil() {
	}
	
	public static String runByInvoke(String str, CoolQMessage coolQMessage) throws Exception {
		String[] className = str.split("\\.");
		Class<?> clz = Class.forName("ninja.skyrocketing.robot.service.impl." + className[0]);
		Method method = clz.getMethod(className[1], CoolQMessage.class);
		Constructor<?> constructor = clz.getConstructor();
		Object object = constructor.newInstance();
		return (String)method.invoke(object, coolQMessage);
	}
}