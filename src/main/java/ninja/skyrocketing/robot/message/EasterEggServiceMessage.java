package ninja.skyrocketing.robot.message;

import net.mamoe.mirai.message.data.FlashImage;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.SingleMessage;
import ninja.skyrocketing.robot.entity.CoolQMessage;

import java.net.MalformedURLException;

public class EasterEggServiceMessage {
	public static Message kkjj(CoolQMessage coolQMessage) {
		return coolQMessage.atSomeone(" kkjj");
	}
	
	public static Message firePhoto(CoolQMessage coolQMessage) throws MalformedURLException {
		MessageChain messageChain = coolQMessage.getGroupMessageEvent().getMessage();
		long admin = Long.parseLong(coolQMessage.getYamlFile().getIdList().get("admin").get(0));
		SingleMessage message = messageChain.get(1);
		String content = message.contentToString();
		if (content.contains("[闪照]")) {
			FlashImage flashImage = (FlashImage) message;
			coolQMessage.getGroupMessageEvent().getBot().getFriend(admin).sendMessage("来自" + coolQMessage.getGroupMessageEvent().getGroup().getId() + " (" + coolQMessage.getGroupMessageEvent().getGroup().getName() + ")由" + coolQMessage.getGroupMessageEvent().getSender().getId() + "发的闪照。");
			coolQMessage.getGroupMessageEvent().getBot().getFriend(admin).sendMessage(flashImage);
		}
		return coolQMessage.atSomeone("发了一张闪照。");
	}
}