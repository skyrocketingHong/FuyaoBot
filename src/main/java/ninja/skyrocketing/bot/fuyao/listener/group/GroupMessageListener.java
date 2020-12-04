package ninja.skyrocketing.bot.fuyao.listener.group;

import kotlin.coroutines.CoroutineContext;
import lombok.NoArgsConstructor;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.bot.fuyao.sender.group.GroupMessageSender;
import ninja.skyrocketing.bot.fuyao.service.bot.BotBanedGroupService;
import ninja.skyrocketing.bot.fuyao.service.bot.BotConfigService;
import ninja.skyrocketing.bot.fuyao.service.bot.BotReplyMessageService;
import ninja.skyrocketing.bot.fuyao.service.user.BotBanedUserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-28 14:55:32
 */

@Component
@NoArgsConstructor
public class GroupMessageListener extends SimpleListenerHost {
    private static BotBanedGroupService botBanedGroupService;
    private static BotBanedUserService botBanedUserService;
    private static BotConfigService botConfigService;
    private static BotReplyMessageService botReplyMessageService;
    @Autowired
    public GroupMessageListener(
            BotBanedGroupService botBanedGroupService,
            BotBanedUserService botBanedUserService,
            BotConfigService botConfigService,
            BotReplyMessageService botReplyMessageService
    ) {
        GroupMessageListener.botBanedGroupService = botBanedGroupService;
        GroupMessageListener.botBanedUserService = botBanedUserService;
        GroupMessageListener.botConfigService = botConfigService;
        GroupMessageListener.botReplyMessageService = botReplyMessageService;
    }

    @EventHandler
    public ListeningStatus onMessage(GroupMessageEvent event) throws Exception {
        //首先判断是否为@机器人
        if (event.getMessage().toString().matches(".*\\[mirai:at:" + event.getBot().getId() + ",.*\\].*") &&
                !event.getMessage().toString().matches(".*\\[mirai:quote:\\d*,\\d*\\].*")) {
            event.getGroup().sendMessage(botConfigService.GetConfigValueByKey("reply_after_at"));
        } else {
            if (event.getMessage().contentToString().matches("^(~|～).+")) {
                //判断是否为被禁用户或群
                if (botBanedGroupService.IsBaned(event.getGroup().getId()) ||
                        botBanedUserService.IsBaned(event.getSender().getId())) {
                    return ListeningStatus.LISTENING;
                } else {
                    //调用消息对应的实现类，并保存返回值（对应的回复）
                    Message message = GroupMessageSender.Sender(event);
                    if (message != null) {
                        //发送消息，并在开头添加@触发人
                        event.getGroup().sendMessage(new At(event.getSender()).plus("\n" + message));
                        return ListeningStatus.LISTENING;
                    }
                }
            }
            //非~开头的消息
            else {
//                if (FuyaoBotApplication.botReplyMessageList == null) {
//                    FuyaoBotApplication.botReplyMessageList = botReplyMessageService.GetAllReplyMessage();
//                }
            }
        }
        return ListeningStatus.LISTENING;
    }

    //处理事件处理时抛出的异常
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        System.out.println(context + " " + exception.getMessage());
    }
}
