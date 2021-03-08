package ninja.skyrocketing.bot.fuyao.listener.group;

import kotlin.coroutines.CoroutineContext;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.*;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.bot.fuyao.util.LogUtil;
import ninja.skyrocketing.bot.fuyao.sender.group.GroupMessageSender;
import ninja.skyrocketing.bot.fuyao.service.bot.BotConfigService;
import ninja.skyrocketing.bot.fuyao.util.DBUtil;
import ninja.skyrocketing.bot.fuyao.util.MessageUtil;
import ninja.skyrocketing.bot.fuyao.util.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * @Author skyrocketing Hong
 * @Date 2021-03-05 14:07:19
 */

@Component
@NoArgsConstructor
public class GroupEventListener extends SimpleListenerHost {
    private static BotConfigService botConfigService;
    @Autowired
    public GroupEventListener(BotConfigService botConfigService) {
        GroupEventListener.botConfigService = botConfigService;
    }

    //新成员主动进群
    @EventHandler
    public ListeningStatus onJoin(MemberJoinEvent.Active event) throws IOException {
        //生成消息
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("👏 欢迎第" + (event.getGroup().getMembers().size() + 1) + "名群员。" + "\n");
        messageChainBuilder.add(MessageUtil.UploadAvatarImageToGroup(event.getGroup(), event.getMember()));
        messageChainBuilder.add(MessageUtil.UserNotify(event.getMember(), true));
        messageChainBuilder.add("\n记得阅读群公告（如果有的话）哦！");
        GroupMessageSender.SendMessageByGroupId(messageChainBuilder, event.getGroup());
        return ListeningStatus.LISTENING;
    }

    //新成员被邀请进群
    @EventHandler
    public ListeningStatus onInvite(MemberJoinEvent.Invite event) throws IOException {
        //生成消息
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("👏 欢迎由 ");
        messageChainBuilder.add(MessageUtil.UserNotify(event.getInvitor(), false));
        messageChainBuilder.add(" 邀请的第 " + (event.getGroup().getMembers().size() + 1) + " 名群员：" + "\n");
        messageChainBuilder.add(MessageUtil.UploadAvatarImageToGroup(event.getGroup(), event.getMember()));
        messageChainBuilder.add(MessageUtil.UserNotify(event.getMember(), true));
        messageChainBuilder.add("\n" + "记得阅读群公告（如果有的话）哦！");
        GroupMessageSender.SendMessageByGroupId(messageChainBuilder, event.getGroup());
        return ListeningStatus.LISTENING;
    }

    //群员主动退群
    @EventHandler
    public ListeningStatus onQuit(MemberLeaveEvent.Quit event) throws IOException {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("⚠ 群员减少提醒\n群员 ");
        messageChainBuilder.add(MessageUtil.UserNotify(event.getMember(), false));
        messageChainBuilder.add(" 悄悄地溜了...\n(提醒消息将在1分钟内自动撤回)");
        //清理数据
        DBUtil.CleanDataAfterLeave(event.getGroup().getId(), event.getMember().getId());
        //撤回消息
        event.getGroup().sendMessage("").recallIn(1);
        GroupMessageSender.SendMessageByGroupId(messageChainBuilder, event.getGroup(), 60000);
        return ListeningStatus.LISTENING;
    }

    //群员被踢
    @EventHandler
    public ListeningStatus onKick(MemberLeaveEvent.Kick event) throws IOException {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("⚠ 群员减少提醒\n群员 ");
        messageChainBuilder.add(MessageUtil.UserNotify(event.getMember(), false));
        messageChainBuilder.add(" 已被 ");
        messageChainBuilder.add(MessageUtil.UserNotify(event.getOperator(), false));
        messageChainBuilder.add(" 移出群聊。\n(提醒消息将在1分钟内自动撤回)");
        //清理数据
        DBUtil.CleanDataAfterLeave(event.getGroup().getId(), event.getMember().getId());
        //撤回消息
        GroupMessageSender.SendMessageByGroupId(messageChainBuilder, event.getGroup(), 60000);
        return ListeningStatus.LISTENING;
    }

    //机器人被移出群聊
    @EventHandler
    public ListeningStatus onBotKick(BotLeaveEvent.Kick event) throws IOException {
        //清理数据
        DBUtil.CleanDataAfterLeave(event.getGroup().getId());
        //保存log
        LogUtil.GroupEventFile(event.toString(), "机器人被移出群聊");
        return ListeningStatus.LISTENING;
    }

    //机器人主动退出群聊
    public ListeningStatus onBotKick(BotLeaveEvent.Active event) throws IOException {
        //清理数据
        DBUtil.CleanDataAfterLeave(event.getGroup().getId());
        //保存log
        LogUtil.GroupEventFile(event.toString(), "机器人主动退出群聊");
        return ListeningStatus.LISTENING;
    }

    //群员荣誉修改
    @EventHandler
    public ListeningStatus onMemberHonorChange(MemberHonorChangeEvent event) throws IOException {
        String honorName = MessageUtil.GetGroupHonorTypeName(event.getHonorType());
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("恭喜 ");
        messageChainBuilder.add(MessageUtil.UserNotify(event.getUser(), true));
        messageChainBuilder.add("\n于 " + TimeUtil.NowDateTime(new Date()) + " " +
                "喜提" +  " \"" + honorName + "\" "
        );
        GroupMessageSender.SendMessageByGroupId(messageChainBuilder, event.getGroup());
        return ListeningStatus.LISTENING;
    }

    //群龙王更改
    @EventHandler
    public ListeningStatus onGroupTalkativeChange(GroupTalkativeChangeEvent event) throws IOException {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("恭喜新龙王 ");
        messageChainBuilder.add(MessageUtil.UserNotify(event.getNow(), true));
        messageChainBuilder.add("\n前任龙王为 ");
        messageChainBuilder.add(MessageUtil.UserNotify(event.getPrevious(), false));
        GroupMessageSender.SendMessageByGroupId(messageChainBuilder, event.getGroup());
        return ListeningStatus.LISTENING;
    }

    //群员头衔修改
    @EventHandler
    public ListeningStatus onMemberSpecialTitleChange(MemberSpecialTitleChangeEvent event) throws IOException {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("恭喜 ");
        messageChainBuilder.add(MessageUtil.UserNotify(event.getMember(), false));
        messageChainBuilder.add("\n于 " + TimeUtil.NowDateTime(new Date()) + " " +
                "喜提 \"" + event.getNew() + "\" 头衔\n"
        );
        messageChainBuilder.add(new At(event.getMember().getId()));
        GroupMessageSender.SendMessageByGroupId(messageChainBuilder, event.getGroup());
        return ListeningStatus.LISTENING;
    }

    //机器人被邀请加入群
    @EventHandler
    public ListeningStatus onBotInvitedJoinGroupRequestEvent(BotInvitedJoinGroupRequestEvent event) throws IOException {
        if (Objects.equals(event.getBot().getFriend(event.getInvitorId()), event.getInvitor())) {
            event.accept();
            LogUtil.GroupEventFile(event.toString(), "机器人被邀请入群 (已同意)");
        } else {
            LogUtil.GroupEventFile(event.toString(), "机器人被邀请入群 (未同意)");
        }
        return ListeningStatus.LISTENING;
    }

    //机器人成功加入了一个新群 (可能是主动加入)
    @EventHandler
    public ListeningStatus onBotJoinGroupEvent(BotJoinGroupEvent.Active event) throws IOException {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("大家好啊，我是扶摇bot\n");
        messageChainBuilder.add(botConfigService.GetConfigValueByKey("reply"));
        GroupMessageSender.SendMessageByGroupId(messageChainBuilder, event.getGroup());
        return ListeningStatus.LISTENING;
    }

    //机器人成功加入了一个新群 (被群员邀请)
    @EventHandler
    public ListeningStatus onBotJoinGroupEvent(BotJoinGroupEvent.Invite event) throws IOException {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("感谢 ");
        messageChainBuilder.add(MessageUtil.UserNotify(event.getInvitor(), true));
        messageChainBuilder.add(" 邀请\n");
        messageChainBuilder.add("大家好啊，我是扶摇bot\n");
        messageChainBuilder.add(botConfigService.GetConfigValueByKey("reply"));
        GroupMessageSender.SendMessageByGroupId(messageChainBuilder, event.getGroup());
        return ListeningStatus.LISTENING;
    }

    //机器人成功加入了一个新群 (原群主通过 https://huifu.qq.com/ 恢复原来群主身份并入群, Bot 是原群主)
    @EventHandler
    public ListeningStatus onBotJoinGroupEvent(BotJoinGroupEvent.Retrieve event) throws IOException {
        LogUtil.GroupEventFile(
                event.toString(), "机器人成功加入了一个新群 (原群主通过 https://huifu.qq.com/ 恢复原来群主身份并入群, Bot 是原群主)"
        );
        return ListeningStatus.LISTENING;
    }

    //监听机器人的群名片被修改后，改成默认名片
    @EventHandler
    public ListeningStatus onMemberCardChangeEvent(MemberCardChangeEvent event) throws IOException {
        if (event.getMember().getId() == event.getBot().getId()) {
            event.getMember().setNameCard("");
        }
        LogUtil.GroupEventFile(event.toString(), "机器人群名片被修改");
        return ListeningStatus.LISTENING;
    }

    //处理事件处理时抛出的异常
    @SneakyThrows
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        LogUtil.GroupEventFile(context + "\n" + exception, "抛出异常");
    }
}
