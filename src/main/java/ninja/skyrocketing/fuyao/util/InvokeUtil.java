package ninja.skyrocketing.fuyao.util;

import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.fuyao.bot.pojo.user.UserMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author skyrocketing Hong
 * @date 2020-11-28 028 15:51:39
 */

public class InvokeUtil {
    public static Message runByInvoke(String str, UserMessage userMessage) {
        try {
            String[] className = str.split("\\.");
            Class<?> clz = Class.forName("ninja.skyrocketing.fuyao.bot." + className[0] + "." + className[1]);
            Method method = clz.getMethod(className[2], UserMessage.class);
            Constructor<?> constructor = clz.getConstructor();
            Object object = constructor.newInstance();
            return (Message) method.invoke(object, userMessage);
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(InvokeUtil.class);
            logger.error("查找运行类时错误，错误详情: " + e.getMessage());
            return null;
        }
        
    }
}
