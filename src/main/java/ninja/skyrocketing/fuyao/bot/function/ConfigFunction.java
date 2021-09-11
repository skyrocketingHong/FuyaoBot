package ninja.skyrocketing.fuyao.bot.function;

import lombok.NoArgsConstructor;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.fuyao.bot.pojo.user.UserMessage;
import ninja.skyrocketing.fuyao.bot.sender.friend.FriendMessageSender;
import ninja.skyrocketing.fuyao.bot.service.bot.BotConfigService;
import ninja.skyrocketing.fuyao.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skyrocketing Hong
 * @date 2021-08-15 11:58
 */

@Component
@NoArgsConstructor
public class ConfigFunction {
	private static BotConfigService botConfigService;
	@Autowired
	public ConfigFunction(BotConfigService botConfigService) {
		ConfigFunction.botConfigService = botConfigService;
	}
	/**
	 * 机器人群名片判断，防止恶意修改
	 * */
	public static void botNameCardCheck(Group group) {
		NormalMember botInGroup = group.getBotAsMember();
		//群名片
		String botNameCard = botInGroup.getNameCard();
		//bot的群名片不为空时直接设为空
		if (!botNameCard.equals("")) {
			botInGroup.setNameCard("");
		}
	}
	
	/**
	 * 反馈消息
	 * */
	public static Message feedbackMessage(UserMessage userMessage) {
		//生成发给管理员的消息
		userMessage.getMessageChainBuilder().add("🧑‍💻 收到新反馈\n");
		if (userMessage.isFriendMessage()) {
			FriendMessageEvent friendMessageEvent = userMessage.getFriendMessageEvent();
			userMessage.getMessageChainBuilder().add("发送者: " + MessageUtil.getFriendInfo(friendMessageEvent) + "\n");
			userMessage.getMessageChainBuilder().add("反馈原文:\n");
			userMessage.getMessageChainBuilder().add(friendMessageEvent.getMessage());
		} else {
			GroupMessageEvent groupMessageEvent = userMessage.getGroupMessageEvent();
			userMessage.getMessageChainBuilder().add("发送者: " + MessageUtil.getMemberInfo(groupMessageEvent.getSender()) + "\n");
			userMessage.getMessageChainBuilder().add("发送群: " + MessageUtil.getGroupInfo(groupMessageEvent) + "\n");
			userMessage.getMessageChainBuilder().add("反馈原文:\n");
			userMessage.getMessageChainBuilder().add(groupMessageEvent.getMessage());
		}
		FriendMessageSender.sendMessageByFriendId(userMessage.getMessageChainBuilder(), Long.valueOf(botConfigService.getConfigValueByKey("admin_user")));
		//清除之前的消息
		userMessage.getMessageChainBuilder().clear();
		//发送给反馈者的消息
		userMessage.getMessageChainBuilder().add("🧑‍💻 你的反馈已收到并发送给了扶摇 bot 的开发者，开发者将在后期的开发过程中考虑此反馈哦");
		return userMessage.getMessageChainBuilderAsMessageChain();
	}
}
