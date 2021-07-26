package ninja.skyrocketing.fuyao.bot.listener.group;

import kotlin.coroutines.CoroutineContext;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.fuyao.bot.config.GlobalVariables;
import ninja.skyrocketing.fuyao.bot.config.MiraiBotConfig;
import ninja.skyrocketing.fuyao.bot.function.EasterEggFunction;
import ninja.skyrocketing.fuyao.bot.function.NotificationFunction;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupMessageInfo;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupUser;
import ninja.skyrocketing.fuyao.bot.sender.group.GroupMessageSender;
import ninja.skyrocketing.fuyao.bot.service.bot.BotBanedGroupService;
import ninja.skyrocketing.fuyao.bot.service.bot.BotConfigService;
import ninja.skyrocketing.fuyao.bot.service.bot.BotReplyMessageService;
import ninja.skyrocketing.fuyao.bot.service.user.BotBanedUserService;
import ninja.skyrocketing.fuyao.util.LogUtil;
import ninja.skyrocketing.fuyao.util.MessageUtil;
import ninja.skyrocketing.fuyao.util.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author skyrocketing Hong
 * @date 2020-11-28 14:55:32
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

    /**
     监听所有群消息
    */
    @EventHandler
    public ListeningStatus onMessage(GroupMessageEvent event) throws Exception {
        //判断是否为黑名单用户或群
        if (botBanedGroupService.isBaned(event.getGroup().getId()) &&
                botBanedUserService.isBaned(event.getSender().getId())) {
            return ListeningStatus.LISTENING;
        }
        GroupUser groupUser = GroupUser.builder().groupId(event.getGroup().getId()).userId(event.getSender().getId()).build();
        Long timestamp = TimeUtil.getTimestamp();
        //当触发用户在防止滥用的Map中时，不发送消息
        if (MiraiBotConfig.GroupUserTriggerDelay.containsKey(groupUser)) {
            //如果该用户已被提醒过，则不执行任何操作
            if (MiraiBotConfig.GroupUserTriggerDelayNotified.contains(groupUser)) {
                return ListeningStatus.LISTENING;
            }
            //获取上一次使用的时间戳
            Long triggerTimeStamp = MiraiBotConfig.GroupUserTriggerDelay.get(groupUser);
            //计算冷却时间
            long coolDownTime = (timestamp - triggerTimeStamp) % 10;
            //生成回复消息
            MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
            messageChainBuilder.add(MessageUtil.userNotify(event.getSender(), true));
            messageChainBuilder.add("\n你的冷却时间尚未结束，请等待 " + coolDownTime + "s 后再操作");
            messageChainBuilder.add("\n(提醒消息将在冷却时间结束后撤回)");
            //发送消息，并在冷却时间内撤回
            MiraiBotConfig.GroupUserTriggerDelayNotified.add(groupUser);
            GroupMessageSender.sendMessageByGroupId(messageChainBuilder, event.getGroup(), coolDownTime * 1000);
            return ListeningStatus.LISTENING;
        }
        //获取消息
        Message messageInGroup = event.getMessage();
        String messageInGroupToString = messageInGroup.toString();
        String messageInGroupContentToString = messageInGroup.contentToString();
        //判断是否为@机器人
        if (messageInGroupToString.matches(".*\\[mirai:at:" + event.getBot().getId() + "].*") &&
                !messageInGroupToString.matches(".*\\[mirai:quote:\\[\\d*],\\[\\d*]].*")) {
            //将触发用户添加进全局map中
            MiraiBotConfig.GroupUserTriggerDelay.put(groupUser, timestamp);
            //被@后返回帮助文案
            MessageReceipt<Group> receipt = GroupMessageSender.sendMessageByGroupIdWithReceipt(botConfigService.getConfigValueByKey("reply"), event.getGroup());
            GroupMessageInfo groupMessageInfo = new GroupMessageInfo(event);
            //存放触发消息
            GlobalVariables.getGlobalVariables().getTriggerGroupMessageInfoMap().put(groupMessageInfo, false);
            //存放出发后的回复的回执
            GlobalVariables.getGlobalVariables().getGroupSentMessageReceipt().put(groupMessageInfo, receipt);
            return ListeningStatus.LISTENING;
        }
        //不是@机器人就继续处理消息
        //拦截以触发指令开头的消息
        else if (messageInGroupContentToString.matches("^[~～/].+")) {
            //调用消息对应的实现类，并保存返回值（对应的回复）
            Message message = GroupMessageSender.sender(event);
            if (message != null) {
                //构造并发送消息，在开头添加@触发人
                MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
                messageChainBuilder.add(MessageUtil.userNotify(event.getSender(), true));
                messageChainBuilder.add("\n");
                messageChainBuilder.add(message);
                MiraiBotConfig.GroupUserTriggerDelay.put(groupUser, timestamp);
                MessageReceipt<Group> receipt = GroupMessageSender.sendMessageByGroupIdWithReceipt(messageChainBuilder, event.getGroup());
                GroupMessageInfo groupMessageInfo = new GroupMessageInfo(event);
                //存放触发消息
                GlobalVariables.getGlobalVariables().getTriggerGroupMessageInfoMap().put(groupMessageInfo, false);
                //存放出发后的回复的回执
                GlobalVariables.getGlobalVariables().getGroupSentMessageReceipt().put(groupMessageInfo, receipt);
            }
            return ListeningStatus.LISTENING;
        }
        //拦截闪照消息，使用mirai码判断
        else if (messageInGroupToString.matches(".*\\[mirai:flash:\\{[0-9A-F]{8}(-[0-9A-F]{4}){3}-[0-9A-F]{12}}\\.jpg].*")) {
            //闪照消息通知
            NotificationFunction.flashImageNotification(event);
            return ListeningStatus.LISTENING;
        }
        //拦截红包消息
        else if (messageInGroupContentToString.matches("\\[QQ红包].+新版手机QQ查.+")) {
            //红包消息通知
            NotificationFunction.redPackageNotification(event);
            return ListeningStatus.LISTENING;
        }
        //拦截视频消息
        else if (messageInGroupContentToString.matches("[视频]你的QQ暂不支持查看视频短片，请升级到最新版本后查看。")) {
            return ListeningStatus.LISTENING;
        }
//        //拦截“为什么”或“***吗”消息
//        else if (messageInGroupContentToString.matches(".*为什么.*|.*吗$")) {
//            EasterEggFunction.stupidAiForWhy(event);
//            return ListeningStatus.LISTENING;
//        }
        //拦截其它可能触发机器人的消息
        //消息复读
        else {
            EasterEggFunction.repeater(event);
            return ListeningStatus.LISTENING;
        }
    }

    /**
     * 处理事件处理时抛出的异常
     * */
    @SneakyThrows
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        LogUtil.eventLog(context + "\n" + exception, "抛出异常");
    }
}
