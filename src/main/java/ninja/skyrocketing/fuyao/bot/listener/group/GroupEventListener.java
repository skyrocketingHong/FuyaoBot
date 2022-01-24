package ninja.skyrocketing.fuyao.bot.listener.group;

import cn.hutool.core.date.DateUtil;
import kotlin.coroutines.CoroutineContext;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.*;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.fuyao.bot.config.GlobalVariables;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupMessageInfo;
import ninja.skyrocketing.fuyao.bot.sender.friend.FriendMessageSender;
import ninja.skyrocketing.fuyao.bot.sender.group.GroupMessageSender;
import ninja.skyrocketing.fuyao.bot.service.bot.BotConfigService;
import ninja.skyrocketing.fuyao.util.DBUtil;
import ninja.skyrocketing.fuyao.util.LogUtil;
import ninja.skyrocketing.fuyao.util.MessageUtil;
import ninja.skyrocketing.fuyao.util.TimeUtil;
import org.jetbrains.annotations.NotNull;
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
        if (GlobalVariables.getGlobalVariables().getTriggerGroupMessageInfoMap().containsKey(groupMessageInfo)) {
            try {
                if (Boolean.FALSE.equals(GlobalVariables.getGlobalVariables().getTriggerGroupMessageInfoMap().get(groupMessageInfo))) {
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
                LogUtil.eventLog(e.getMessage(), "撤回消息时出现错误");
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
        messageChainBuilder.add("🏃 群员退群\n群员 ");
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
        messageChainBuilder.add("✈️ 群员被移除\n群员 ");
        messageChainBuilder.add(MessageUtil.userNotify(event.getMember(), false));
        messageChainBuilder.add(" 已被 ");
        messageChainBuilder.add(MessageUtil.userNotify(event.getOperator(), false));
        messageChainBuilder.add(" 移出群聊\n(提醒消息将在1分钟内自动撤回)");
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
     * 群解散
     * */
    @EventHandler
    public ListeningStatus onBotDisband(BotLeaveEvent.Disband event) {
        //清理数据
        DBUtil.cleanDataAfterLeave(event.getGroup().getId());
        //保存log
        LogUtil.eventLog(event.toString(), "群已被解散");
        return ListeningStatus.LISTENING;
    }

    /**
     * 群龙王更改
     * */
    @EventHandler
    public ListeningStatus onGroupTalkativeChange(GroupTalkativeChangeEvent event) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("🐲 恭喜新龙王 ");
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
        messageChainBuilder.add("👏 恭喜 ");
        messageChainBuilder.add(MessageUtil.userNotify(event.getMember(), false));
        messageChainBuilder.add("\n于 " + TimeUtil.dateTimeFormatter(new Date()) + " " +
                "喜提 \"" + event.getNew() + "\" 头衔\n"
        );
        GroupMessageSender.sendMessageByGroupId(messageChainBuilder, event.getGroup());
        return ListeningStatus.LISTENING;
    }

    /**
     * 机器人被邀请加入群
     * */
    @EventHandler
    public ListeningStatus onBotInvitedJoinGroupRequestEvent(BotInvitedJoinGroupRequestEvent event) {
        //邀请人是否为机器人好友
        if (Objects.equals(event.getBot().getFriend(event.getInvitorId()), event.getInvitor())) {
            FriendMessageSender.sendMessageByFriendId("❓ 等待开发者审核入群", event.getInvitor());
            LogUtil.eventLog(event.toString(), "等待开发者审核入群");
        } else {
            event.cancel();
            FriendMessageSender.sendMessageByFriendId("❌ 邀请人非机器人好友\n机器人未同意入群", event.getInvitor());
            LogUtil.eventLog(event.toString(), "邀请人非机器人好友，机器人未同意入群");
        }
        return ListeningStatus.LISTENING;
    }

    /**
     * 机器人成功加入了一个新群 (被群员邀请)
     * */
    @EventHandler
    public ListeningStatus onBotJoinGroupEvent(BotJoinGroupEvent.Invite event) {
        //默认全体禁言或群名包含违禁词时直接退群
        if (event.getGroup().getName().matches(botConfigService.getConfigValueByKey("ban_name"))) {
            event.getGroup().quit();
            FriendMessageSender.sendMessageByFriendId("❌ 邀请群的名字中包含违禁词\n机器人未同意入群", event.getInvitor().getId());
            LogUtil.eventLog(event.toString(), "群名包含违禁词，直接退群");
        }
        //判断群禁言
        else if(event.getGroup().getSettings().isMuteAll()) {
            event.getGroup().quit();
            FriendMessageSender.sendMessageByFriendId("❌ 邀请的群为全员禁言群\n机器人未同意入群", event.getInvitor().getId());
            LogUtil.eventLog(event.toString(), "由于默认全体禁言，直接退群");
        }
        //判断群人数
        else if (event.getGroup().getMembers().size() <= 2) {
            event.cancel();
            FriendMessageSender.sendMessageByFriendId("❌ 邀请群人数过少\n机器人未同意入群", event.getInvitor().getId());
            LogUtil.eventLog(event.toString(), "邀请群人数过少，机器人未同意入群");
        } else {
            MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
            messageChainBuilder.add("👏 感谢 ");
            messageChainBuilder.add(MessageUtil.userNotify(event.getInvitor(), true));
            messageChainBuilder.add(" 邀请\n");
            GroupMessageSender.sendMessageByGroupId(messageChainBuilder, event.getGroup());
        }
        return ListeningStatus.LISTENING;
    }
    
    /**
     * 机器人成功加入了一个新群 (可能是主动加入)
     * */
    @EventHandler
    public ListeningStatus onBotJoinGroupEvent(BotJoinGroupEvent.Active event) {
        //默认全体禁言或群名包含违禁词时直接退群
        if (event.getGroup().getName().matches(botConfigService.getConfigValueByKey("ban_name"))) {
            event.getGroup().quit();
            LogUtil.eventLog(event.toString(), "群名包含违禁词，直接退群");
        }
        //判断群禁言
        else if(event.getGroup().getSettings().isMuteAll()) {
            event.getGroup().quit();
            LogUtil.eventLog(event.toString(), "由于默认全体禁言，直接退群");
        }
        //判断群人数
        else if (event.getBot().getGroup(event.getGroupId()).getMembers().size() <= 2) {
            event.cancel();
            LogUtil.eventLog(event.toString(), "邀请群人数过少，机器人未同意入群");
        } else {
            MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
            messageChainBuilder.add("👏 大家好啊，我是扶摇bot\n");
            messageChainBuilder.add(botConfigService.getConfigValueByKey("reply"));
            GroupMessageSender.sendMessageByGroupId(messageChainBuilder, event.getGroup());
        }
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
        messageChainBuilder.add("💬 群名片修改\n");
        messageChainBuilder.add("🔙 原名片: \"" + event.getOrigin() + "\"\n");
        messageChainBuilder.add("🆕 新名片: \"" + event.getNew() + "\"\n");
        messageChainBuilder.add("(提醒消息将在1分钟内自动撤回)");
        GroupMessageSender.sendMessageByGroupId(messageChainBuilder, event.getGroup(), 60000L);
        return ListeningStatus.LISTENING;
    }
    
    /**
     * 监听群名修改
     * */
    @EventHandler
    public ListeningStatus onGroupNameChangeEvent(GroupNameChangeEvent event) {
        if (event.getNew().matches(botConfigService.getConfigValueByKey("ban_name"))) {
            GroupMessageSender.sendMessageByGroupId("⚠ 群名称修改\n检测到违禁词，已自动退群", event.getGroup().getId());
            event.getGroup().quit();
        } else {
            MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
            messageChainBuilder.add("💬 群名称修改\n");
            messageChainBuilder.add("🔙 原名称: \"" + event.getOrigin() + "\"\n");
            messageChainBuilder.add("🆕 新名称: \"" + event.getNew() + "\"\n");
            messageChainBuilder.add("🔧 修改人: " + MessageUtil.userNotify(event.getOperator(), false));
            GroupMessageSender.sendMessageByGroupId(messageChainBuilder, event.getGroup());
        }
        return ListeningStatus.LISTENING;
    }
    
    /**
     * 监听群成员被禁言
     * */
    @EventHandler
    public ListeningStatus onMemberMuteEvent(MemberMuteEvent event) {
        Date date = new Date();
        int durationSeconds = event.getDurationSeconds();
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("🤐 群员被禁言\n");
        messageChainBuilder.add("🚫 被禁言人: " + MessageUtil.userNotify(event.getMember(), false) + "\n");
        messageChainBuilder.add("👮 操作人: " + MessageUtil.userNotify(event.getOperator(), false) + "\n");
        messageChainBuilder.add("⏱️ 禁言时长: " + DateUtil.secondToTime(durationSeconds) + "\n");
        messageChainBuilder.add("📅 预计解禁时间: " + TimeUtil.dateTimeFormatter(DateUtil.offsetSecond(date, event.getDurationSeconds())));
        messageChainBuilder.add("\n(提醒消息将在1分钟内自动撤回)");
        GroupMessageSender.sendMessageByGroupId(messageChainBuilder, event.getGroup(), 60000L);
        return ListeningStatus.LISTENING;
    }
    
    /**
     * 监听成员被解禁
     * */
    @EventHandler
    public ListeningStatus onMemberUnmuteEvent(MemberUnmuteEvent event) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("😬 群员被解禁\n");
        messageChainBuilder.add("✅ 被解禁人: " + MessageUtil.userNotify(event.getMember(), false) + "\n");
        messageChainBuilder.add("👮 操作人: " + MessageUtil.userNotify(event.getOperator(), false));
        messageChainBuilder.add("\n(提醒消息将在1分钟内自动撤回)");
        GroupMessageSender.sendMessageByGroupId(messageChainBuilder, event.getGroup(), 60000L);
        return ListeningStatus.LISTENING;
    }
    
    /**
     * 监听加群申请
     * */
    @EventHandler
    public ListeningStatus onMemberJoinRequestEvent(MemberJoinRequestEvent event) {
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
