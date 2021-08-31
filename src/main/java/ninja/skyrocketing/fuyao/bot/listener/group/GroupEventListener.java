package ninja.skyrocketing.fuyao.bot.listener.group;

import kotlin.coroutines.CoroutineContext;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.*;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.fuyao.bot.config.GlobalVariables;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupMessageInfo;
import ninja.skyrocketing.fuyao.bot.sender.group.GroupMessageSender;
import ninja.skyrocketing.fuyao.bot.service.bot.BotConfigService;
import ninja.skyrocketing.fuyao.util.DBUtil;
import ninja.skyrocketing.fuyao.util.LogUtil;
import ninja.skyrocketing.fuyao.util.MessageUtil;
import ninja.skyrocketing.fuyao.util.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * @author skyrocketing Hong
 * @date 2021-03-05 14:07:19
 */

@Component
@NoArgsConstructor
public class GroupEventListener extends SimpleListenerHost {
    private static BotConfigService botConfigService;
    @Autowired
    public GroupEventListener(BotConfigService botConfigService) {
        GroupEventListener.botConfigService = botConfigService;
    }
    
    /**
     * 当用户将触发机器人的消息撤回后，自动撤回机器人发的消息
     * */
    @EventHandler
    public ListeningStatus onGroupRecall(MessageRecallEvent.GroupRecall event) {
        GroupMessageInfo groupMessageInfo = new GroupMessageInfo(event.getGroup().getId(), event.getMessageIds()[0]);
        System.out.println(GlobalVariables.getGlobalVariables().getTriggerGroupMessageInfoMap().containsKey(groupMessageInfo));
        if (GlobalVariables.getGlobalVariables().getTriggerGroupMessageInfoMap().containsKey(groupMessageInfo)) {
            try {
                if (!GlobalVariables.getGlobalVariables().getTriggerGroupMessageInfoMap().get(groupMessageInfo)) {
                    GlobalVariables.getGlobalVariables().recallAndDeleteByGroupMessageInfo(groupMessageInfo);
                    return ListeningStatus.LISTENING;
                } else {
                    int messageId = groupMessageInfo.getMessageId();
                    for (int i = 1; i < 3; ++i) {
                        groupMessageInfo.setMessageId(messageId + i);
                        if (GlobalVariables.getGlobalVariables().getGroupSentMessageReceipt().get(groupMessageInfo) != null) {
                            GlobalVariables.getGlobalVariables().recallAndDeleteByGroupMessageInfo(groupMessageInfo);
                            return ListeningStatus.LISTENING;
                        }
                    }
                }
            } catch (Exception e) {
                Logger log =  LoggerFactory.getLogger(GroupEventListener.class);
                log.error("撤回消息时出现错误，错误详情: " + e.getMessage());
            }
        }
        return ListeningStatus.LISTENING;
    }

    /**
     * 新成员主动进群
     * */
    @EventHandler
    public ListeningStatus onJoin(MemberJoinEvent.Active event) throws IOException {
        //生成消息
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("👏 欢迎第" + (event.getGroup().getMembers().size() + 1) + "名群员。" + "\n");
        messageChainBuilder.add(MessageUtil.uploadAvatarImageToGroup(event.getGroup(), event.getMember()));
        messageChainBuilder.add(MessageUtil.userNotify(event.getMember(), true));
        messageChainBuilder.add("\n记得阅读群公告（如果有的话）哦！");
        GroupMessageSender.sendMessageByGroupId(messageChainBuilder, event.getGroup());
        return ListeningStatus.LISTENING;
    }

    /**
     * 新成员被邀请进群
     * */
    @EventHandler
    public ListeningStatus onInvite(MemberJoinEvent.Invite event) throws IOException {
        //生成消息
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("👏 欢迎由 ");
        messageChainBuilder.add(MessageUtil.userNotify(event.getInvitor(), false));
        messageChainBuilder.add(" 邀请的第 " + (event.getGroup().getMembers().size() + 1) + " 名群员：" + "\n");
        messageChainBuilder.add(MessageUtil.uploadAvatarImageToGroup(event.getGroup(), event.getMember()));
        messageChainBuilder.add(MessageUtil.userNotify(event.getMember(), true));
        messageChainBuilder.add("\n" + "记得阅读群公告（如果有的话）哦！");
        GroupMessageSender.sendMessageByGroupId(messageChainBuilder, event.getGroup());
        return ListeningStatus.LISTENING;
    }

    /**
     * 群员主动退群
     * */
    @EventHandler
    public ListeningStatus onQuit(MemberLeaveEvent.Quit event) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("⚠ 群员减少提醒\n群员 ");
        messageChainBuilder.add(MessageUtil.userNotify(event.getMember(), false));
        messageChainBuilder.add(" 悄悄地溜了...\n(提醒消息将在1分钟内自动撤回)");
        //清理数据
        DBUtil.cleanDataAfterLeave(event.getGroup().getId(), event.getMember().getId());
        //撤回消息
        GroupMessageSender.sendMessageByGroupId(messageChainBuilder, event.getGroup(), 60000L);
        return ListeningStatus.LISTENING;
    }

    /**
     * 群员被踢
     * */
    @EventHandler
    public ListeningStatus onKick(MemberLeaveEvent.Kick event) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("⚠ 群员减少提醒\n群员 ");
        messageChainBuilder.add(MessageUtil.userNotify(event.getMember(), false));
        messageChainBuilder.add(" 已被 ");
        messageChainBuilder.add(MessageUtil.userNotify(event.getOperator(), false));
        messageChainBuilder.add(" 移出群聊。\n(提醒消息将在1分钟内自动撤回)");
        //清理数据
        DBUtil.cleanDataAfterLeave(event.getGroup().getId(), event.getMember().getId());
        //撤回消息
        GroupMessageSender.sendMessageByGroupId(messageChainBuilder, event.getGroup(), 60000L);
        return ListeningStatus.LISTENING;
    }

    /**
     * 机器人被移出群聊
     * */
    @EventHandler
    public ListeningStatus onBotKick(BotLeaveEvent.Kick event) {
        //清理数据
        DBUtil.cleanDataAfterLeave(event.getGroup().getId());
        //保存log
        LogUtil.eventLog(event.toString(), "机器人被移出群聊");
        return ListeningStatus.LISTENING;
    }

    /**
     * 机器人主动退出群聊
     * */
    @EventHandler
    public ListeningStatus onBotKick(BotLeaveEvent.Active event) {
        //清理数据
        DBUtil.cleanDataAfterLeave(event.getGroup().getId());
        //保存log
        LogUtil.eventLog(event.toString(), "机器人主动退出群聊");
        return ListeningStatus.LISTENING;
    }

    /**
     * 群员荣誉修改
     */
    @EventHandler
    public ListeningStatus onMemberHonorChange(MemberHonorChangeEvent event) {
        String honorName = MessageUtil.getGroupHonorTypeName(event.getHonorType());
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("👍 恭喜 ");
        messageChainBuilder.add(MessageUtil.userNotify(event.getUser(), true));
        messageChainBuilder.add("\n于 " + TimeUtil.dateTimeFormatter(new Date()) + " " +
                "喜提" +  " \"" + honorName + "\" "
        );
        GroupMessageSender.sendMessageByGroupId(messageChainBuilder, event.getGroup());
        return ListeningStatus.LISTENING;
    }

    /**
     * 群龙王更改
     * */
    @EventHandler
    public ListeningStatus onGroupTalkativeChange(GroupTalkativeChangeEvent event) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("🐉 恭喜新龙王 ");
        messageChainBuilder.add(MessageUtil.userNotify(event.getNow(), true));
        messageChainBuilder.add("\n前任龙王为 ");
        messageChainBuilder.add(MessageUtil.userNotify(event.getPrevious(), false));
        GroupMessageSender.sendMessageByGroupId(messageChainBuilder, event.getGroup());
        return ListeningStatus.LISTENING;
    }

    /**
     * 群员头衔修改
     * */
    @EventHandler
    public ListeningStatus onMemberSpecialTitleChange(MemberSpecialTitleChangeEvent event) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("👍 恭喜 ");
        messageChainBuilder.add(MessageUtil.userNotify(event.getMember(), false));
        messageChainBuilder.add("\n于 " + TimeUtil.dateTimeFormatter(new Date()) + " " +
                "喜提 \"" + event.getNew() + "\" 头衔\n"
        );
        messageChainBuilder.add(new At(event.getMember().getId()));
        GroupMessageSender.sendMessageByGroupId(messageChainBuilder, event.getGroup());
        return ListeningStatus.LISTENING;
    }

    /**
     * 机器人被邀请加入群
     * */
    @EventHandler
    public ListeningStatus onBotInvitedJoinGroupRequestEvent(BotInvitedJoinGroupRequestEvent event) {
        if (Objects.equals(event.getBot().getFriend(event.getInvitorId()), event.getInvitor())) {
            event.accept();
            LogUtil.eventLog(event.toString(), "机器人被邀请入群 (已同意)");
        } else {
            LogUtil.eventLog(event.toString(), "机器人被邀请入群 (未同意)");
        }
        return ListeningStatus.LISTENING;
    }

    /**
     * 机器人成功加入了一个新群 (被群员邀请)
     * */
    @EventHandler
    public ListeningStatus onBotJoinGroupEvent(BotJoinGroupEvent.Invite event) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("👏 感谢 ");
        messageChainBuilder.add(MessageUtil.userNotify(event.getInvitor(), true));
        messageChainBuilder.add(" 邀请\n");
        GroupMessageSender.sendMessageByGroupId(messageChainBuilder, event.getGroup());
        return ListeningStatus.LISTENING;
    }
    
    /**
     * 机器人成功加入了一个新群 (可能是主动加入)
     * */
    @EventHandler
    public ListeningStatus onBotJoinGroupEvent(BotJoinGroupEvent.Active event) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("👏 大家好啊，我是扶摇bot\n");
        messageChainBuilder.add(botConfigService.getConfigValueByKey("reply"));
        GroupMessageSender.sendMessageByGroupId(messageChainBuilder, event.getGroup());
        return ListeningStatus.LISTENING;
    }

    /**
     * 机器人成功加入了一个新群 (原群主通过 https://huifu.qq.com/ 恢复原来群主身份并入群, Bot 是原群主)
     * */
    @EventHandler
    public ListeningStatus onBotJoinGroupEvent(BotJoinGroupEvent.Retrieve event) {
        LogUtil.eventLog(
                event.toString(), "机器人成功加入了一个新群 (原群主通过 https://huifu.qq.com/ 恢复原来群主身份并入群, Bot 是原群主)"
        );
        return ListeningStatus.LISTENING;
    }

    /**
     * 监听群名片被修改后，并将机器人的改成默认名片
     */
    @EventHandler
    public ListeningStatus onMemberCardChangeEvent(MemberCardChangeEvent event) {
        if (event.getMember().getId() == event.getBot().getId()) {
            event.getMember().setNameCard(event.getBot().getNick());
            LogUtil.eventLog(event.toString(), "机器人群名片被修改");
            return ListeningStatus.LISTENING;
        }
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("💬 群名片修改提醒\n");
        messageChainBuilder.add(MessageUtil.userNotify(event.getUser(), true));
        messageChainBuilder.add("\n的群名片从 \"" + event.getOrigin() + "\" 修改为 \"" + event.getNew() + "\"");
        messageChainBuilder.add("\n(提醒消息将在1分钟内自动撤回)");
        GroupMessageSender.sendMessageByGroupId(messageChainBuilder, event.getGroup(), 60000L);
        return ListeningStatus.LISTENING;
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
