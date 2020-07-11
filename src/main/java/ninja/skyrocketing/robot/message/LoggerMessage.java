package ninja.skyrocketing.robot.message;

import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.util.TimeUtil;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-11 011 19:30:09
 * @Version 1.0
 */
public class LoggerMessage {
	public static void logger(GroupMessageEvent event, Message replyMessage, Long adminId) {
		MessageChainBuilder messages = new MessageChainBuilder() {{
			add("⚠ 机器人触发提醒 ⚠" + "\n" +
					"① 触发消息：" + event.getMessage().contentToString().replaceAll("\\t|\\n", "") + "\n" +
					"② 回复消息：" + replyMessage.contentToString().replaceAll("\\t|\\n", "") + "\n" +
					"③ 时间：" + TimeUtil.reformatDateTimeOfTimestamp(event.getTime()) + "\n" +
					"④ 群名：" + event.getGroup().getName() + "\n" +
					"⑤ 群号：" + event.getGroup().getId() + "\n" +
					"⑥ 群名片：" + event.getSender().getNameCard() + "\n" +
					"⑦ QQ号：" + event.getSender().getId()
			);
		}};
		event.getBot().getGroup(adminId).sendMessage(messages.asMessageChain());
	}
}
