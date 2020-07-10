package ninja.skyrocketing.robot.message;

import net.mamoe.mirai.message.data.FlashImage;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.SingleMessage;
import ninja.skyrocketing.robot.entity.CoolQMessage;
import ninja.skyrocketing.util.TimeUtil;

import java.net.MalformedURLException;

public class EasterEggMessage {
	public static Message kkjj(CoolQMessage coolQMessage) {
		return coolQMessage.atSomeone(" kkjj");
	}
	
	public static Message firePhoto(CoolQMessage coolQMessage) throws MalformedURLException {
		Long admin = Long.parseLong(coolQMessage.getYamlFile().getIdList().get("admingroup").get(0));
		SingleMessage message = coolQMessage.getGroupMessageEvent().getMessage().get(1);
		String content = message.contentToString();
		if (content.equals("[闪照]")) {
			Image flashImage = ((FlashImage) message).getImage();
			coolQMessage.getGroupMessageEvent().getBot().getGroup(admin).sendMessage(
					"收到一张闪照\n" +
							"时间：" + TimeUtil.getDateTimeOfTimestamp(coolQMessage.getGroupMessageEvent().getTime()) + "\n" +
							"群名：" + coolQMessage.getGroupMessageEvent().getGroup().getName() + "\n" +
							"群号：" + coolQMessage.getGroupMessageEvent().getGroup().getId() + "\n" +
							"群名片：" + coolQMessage.getGroupMessageEvent().getSender().getNameCard() + "\n" +
							"QQ号：" + coolQMessage.getGroupMessageEvent().getSender().getId() + "\n" +
							"链接：" + coolQMessage.getGroupMessageEvent().getBot().queryImageUrl(flashImage) + "\n" +
							"图片如下："
			);
			coolQMessage.getGroupMessageEvent().getBot().getGroup(admin).sendMessage(flashImage);
		}
		return coolQMessage.atSomeone("发了一张闪照。");
	}
}