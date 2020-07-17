package ninja.skyrocketing.robot.messages;

import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.PlainText;
import ninja.skyrocketing.robot.entity.MessageEncapsulation;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-17 017 09:26:18
 * @Version 1.0
 */
public class FuncOffMessage {
	public static Message FuncOff(MessageEncapsulation messageEncapsulation) {
		return new PlainText("功能维护中\n" + messageEncapsulation.getMsg() + "功能已暂时关闭。");
	}
}
