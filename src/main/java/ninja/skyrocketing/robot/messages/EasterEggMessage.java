package ninja.skyrocketing.robot.messages;

import net.mamoe.mirai.message.data.FlashImage;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.robot.entity.BotConfig;
import ninja.skyrocketing.robot.entity.MessageEncapsulation;
import ninja.skyrocketing.utils.TimeUtil;

public class EasterEggMessage {
	public static Message kkjj(MessageEncapsulation messageEntity) {
		return kk(messageEntity);
	}
	
	public static Message kk(MessageEncapsulation messageEntity) {
		return messageEntity.sendMsg("kk？gkd发！");
	}
	
	public static Message firePhoto(MessageEncapsulation messageEntity) {
		//将闪照转为图片类型
		Image flashImage = ((FlashImage) messageEntity.getGroupMessageEvent().getMessage().get(1)).getImage();
		//生成消息
		MessageChainBuilder messages = new MessageChainBuilder() {{
			add("⚠ 机器人闪照提醒 ⚠" + "\n" +
					"① 时间：" + TimeUtil.reformatDateTimeOfTimestamp(messageEntity.getGroupMessageEvent().getTime()) + "\n" +
					"② 群名：" + messageEntity.getGroupMessageEvent().getGroup().getName() + "\n" +
					"③ 群号：" + messageEntity.getGroupMessageEvent().getGroup().getId() + "\n" +
					"④ 群名片：" + messageEntity.getGroupMessageEvent().getSender().getNameCard() + "\n" +
					"⑤ QQ号：" + messageEntity.getGroupMessageEvent().getSender().getId() + "\n" +
					"⑥ 链接：" + messageEntity.getGroupMessageEvent().getBot().queryImageUrl(flashImage) + "\n" +
					"⑦ 图片："
			);
			add(flashImage);
		}};
		//向群内转发闪照图片
		for (Long id : BotConfig.getFlashImageGroups()) {
			messageEntity.getGroupMessageEvent().getBot().getGroup(id).sendMessage(messages.asMessageChain());
		}
		//生成群提醒
		return messageEntity.atSomeone("发了一张闪照，gkd来康康。");
	}
	
	public static Message RedEnvelope(MessageEncapsulation messageEntity) {
		return messageEntity.atSomeone("发了一个红包，gkd来白嫖。");
	}
}