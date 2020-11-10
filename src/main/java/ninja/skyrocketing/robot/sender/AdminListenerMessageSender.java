package ninja.skyrocketing.robot.sender;

import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.robot.entity.BotConfig;
import org.jetbrains.annotations.NotNull;

/**
 * @Author skyrocketing Hong
 * @Date 2020-08-20 020 20:39:18
 */

public class AdminListenerMessageSender {
	public static void AdminMessageSender(MessageChainBuilder messages, Bot bot) {
		for (Long id : BotConfig.getAdminGroups()) {
//			messages.add(new At(bot.getGroup(id).getOwner()));
			bot.getGroup(id).sendMessage(messages.asMessageChain());
		}
	}
	
	public static void ErrorMessageSender(@NotNull CoroutineContext context, @NotNull Throwable exception, Bot bot) {
		MessageChainBuilder messages = new MessageChainBuilder();
		messages.add("[FATAL]" + "\n" + context.toString() + "\n" + exception.toString());
		AdminMessageSender(messages, bot);
	}
	
	public static void ErrorMessageSender(Exception e, Bot bot) {
		MessageChainBuilder messages = new MessageChainBuilder();
		messages.add("[FATAL]" + "\n" + e.getLocalizedMessage());
		AdminMessageSender(messages, bot);
	}
}
