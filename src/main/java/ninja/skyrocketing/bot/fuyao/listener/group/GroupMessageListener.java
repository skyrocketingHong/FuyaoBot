package ninja.skyrocketing.bot.fuyao.listener.group;

import kotlin.coroutines.CoroutineContext;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;
import ninja.skyrocketing.bot.fuyao.config.MiraiBotConfig;
import ninja.skyrocketing.bot.fuyao.function.functions.EasterEggFunction;
import ninja.skyrocketing.bot.fuyao.function.functions.NotificationFunction;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupRepeaterMessage;
import ninja.skyrocketing.bot.fuyao.util.LogUtil;
import ninja.skyrocketing.bot.fuyao.sender.group.GroupMessageSender;
import ninja.skyrocketing.bot.fuyao.service.bot.BotBanedGroupService;
import ninja.skyrocketing.bot.fuyao.service.bot.BotConfigService;
import ninja.skyrocketing.bot.fuyao.service.bot.BotReplyMessageService;
import ninja.skyrocketing.bot.fuyao.service.user.BotBanedUserService;
import ninja.skyrocketing.bot.fuyao.util.MessageUtil;
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

    //监听所有群消息
    @EventHandler
    public ListeningStatus onMessage(GroupMessageEvent event) throws Exception {
        //首先判断是否为@机器人
        if (event.getMessage().toString().matches(".*\\[mirai:at:" + event.getBot().getId() + "].*") &&
                !event.getMessage().toString().matches(".*\\[mirai:quote:\\[\\d*],\\[\\d*]].*")) {
            //被@后返回帮助文案
            GroupMessageSender.SendMessageByGroupId(botConfigService.GetConfigValueByKey("reply"), event.getGroup().getId());
        } else {
            //拦截以~开头的消息
            if (event.getMessage().contentToString().matches("^[~～/].+")) {
                //判断是否为被禁用户或群
                if (!botBanedGroupService.IsBaned(event.getGroup().getId()) &&
                        !botBanedUserService.IsBaned(event.getSender().getId())) {
                    //调用消息对应的实现类，并保存返回值（对应的回复）
                    Message message = GroupMessageSender.Sender(event);
                    if (message != null) {
                        //发送消息，并在开头添加@触发人
                        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
                        messageChainBuilder.add(MessageUtil.UserNotify(event.getSender(), true));
                        messageChainBuilder.add("\n");
                        messageChainBuilder.add(message);
                        GroupMessageSender.SendMessageByGroupId(messageChainBuilder, event.getGroup());
                    }
                }
            }
            //拦截其他消息
            else {
                //拦截闪照消息，使用mirai码判断
                if (event.getMessage().toString().matches(".*\\[mirai:flash:\\{[0-9A-F]{8}(-[0-9A-F]{4}){3}-[0-9A-F]{12}\\}\\.jpg].*")) {
                    NotificationFunction.FlashImageNotification(event);
                } else {
                    //拦截红包消息
                    if (event.getMessage().contentToString().matches("\\[QQ红包].+新版手机QQ查.+")){
                        NotificationFunction.RedPackageNotification(event);
                    }
                    //拦截判断复读消息
                    else {
                        EasterEggFunction.Repeater(event);
                    }
                }
                return ListeningStatus.LISTENING;
            }
        }
        return ListeningStatus.LISTENING;
    }

    //处理事件处理时抛出的异常
    @SneakyThrows
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        LogUtil.GroupEventFile(context + "\n" + exception, "抛出异常");
    }
}
