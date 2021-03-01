package ninja.skyrocketing.bot.fuyao.listener.group;

import cn.hutool.http.HttpUtil;
import kotlin.coroutines.CoroutineContext;
import lombok.NoArgsConstructor;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.*;
import net.mamoe.mirai.message.data.*;
import ninja.skyrocketing.bot.fuyao.FuyaoBotApplication;
import ninja.skyrocketing.bot.fuyao.function.coin.Coin;
import ninja.skyrocketing.bot.fuyao.function.exp.Exp;
import ninja.skyrocketing.bot.fuyao.function.fishing.Fishing;
import ninja.skyrocketing.bot.fuyao.sender.group.GroupMessageSender;
import ninja.skyrocketing.bot.fuyao.service.bot.BotBanedGroupService;
import ninja.skyrocketing.bot.fuyao.service.bot.BotConfigService;
import ninja.skyrocketing.bot.fuyao.service.bot.BotReplyMessageService;
import ninja.skyrocketing.bot.fuyao.service.user.BotBanedUserService;
import ninja.skyrocketing.bot.fuyao.util.DBUtil;
import ninja.skyrocketing.bot.fuyao.util.FileUtil;
import ninja.skyrocketing.bot.fuyao.util.MessageUtil;
import ninja.skyrocketing.bot.fuyao.util.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Date;

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

    //监听群消息
    @EventHandler
    public ListeningStatus onMessage(GroupMessageEvent event) throws Exception {
        //首先判断是否为@机器人
        if (event.getMessage().toString().matches(".*\\[mirai:at:" + event.getBot().getId() + "].*") &&
                !event.getMessage().toString().matches(".*\\[mirai:quote:\\[\\d*],\\[\\d*]].*")) {
            //被@后返回帮助文案
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
                        event.getGroup().sendMessage(new At(event.getSender().getId()).plus("\n" + message));
                        return ListeningStatus.LISTENING;
                    }
                }
            }
            //拦截闪照消息
            else if (event.getMessage().toString().matches(".*\\[mirai:flash:\\{[0-9A-F]{8}(-[0-9A-F]{4}){3}-[0-9A-F]{12}\\}\\.jpg].*")) {
                //向群内发送闪照消息
                MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
                messageChainBuilder.add(MessageUtil.UserNotify(event.getSender(),true));
                messageChainBuilder.add("\n" + "发了一张闪照，快来康康。");
                event.getGroup().sendMessage(messageChainBuilder.asMessageChain());
                //转存闪照
                Image flashImage = ((FlashImage) event.getMessage().get(1)).getImage();
                String imageURL = FileUtil.ImageIdToURL(flashImage);
                //文件名规则：群号-QQ号-日期（年月日时分秒微秒）
                String fileName = event.getGroup().getId() + "-" + event.getSender().getId() + "-" + TimeUtil.DateTimeFileName();
                String separator = File.separator;
                File imagePath = new File(FileUtil.GetPath() +
                        separator + "cache" +
                        separator + "Flash Image" +
                        separator + fileName + ".jpg"
                );
                HttpUtil.downloadFile(imageURL, imagePath);
                //继续监听
                return ListeningStatus.LISTENING;
            }
            //拦截红包消息
            {

            }
            //非~开头的消息
            {

            }
        }
        return ListeningStatus.LISTENING;
    }

    //监听成员进群，并发送欢迎消息
    @EventHandler
    public ListeningStatus onJoin(MemberJoinEvent.Active event) throws IOException {
        //生成消息
        MessageChainBuilder messages = new MessageChainBuilder();
        messages.add("👏 欢迎第" + (event.getGroup().getMembers().size() + 1) + "名群员。" + "\n");
        messages.add(MessageUtil.UploadImageToGroup(event.getGroup(), event.getMember()));
        messages.add(MessageUtil.UserNotify(event.getMember(), true));
        messages.add("\n" + "记得阅读群公告（如果有的话）哦！");
        event.getGroup().sendMessage(messages.asMessageChain());
        return ListeningStatus.LISTENING;
    }

    @EventHandler
    public ListeningStatus onInvite(MemberJoinEvent.Invite event) throws IOException {
        //生成消息
        MessageChainBuilder messages = new MessageChainBuilder();
        messages.add("👏 欢迎由 ");
        messages.add(MessageUtil.UserNotify(event.getInvitor(), false));
        messages.add(" 邀请的第 " + (event.getGroup().getMembers().size() + 1) + " 名群员：" + "\n");
        messages.add(MessageUtil.UploadImageToGroup(event.getGroup(), event.getMember()));
        messages.add(MessageUtil.UserNotify(event.getMember(), false));
        messages.add("\n" + "记得阅读群公告（如果有的话）哦！");
        event.getGroup().sendMessage(messages.asMessageChain());
        return ListeningStatus.LISTENING;
    }

    //监听群员主动退群
    @EventHandler
    public ListeningStatus onQuit(MemberLeaveEvent.Quit event) {
        MessageChainBuilder messages = new MessageChainBuilder();
        messages.add("⚠ 群员减少提醒\n群员 ");
        messages.add(MessageUtil.UserNotify(event.getMember(), false));
        messages.add(" 悄悄地溜了...\n(提醒消息将在1分钟内自动撤回)");
        //清理数据
        DBUtil.CleanDataAfterLeave(event.getGroup().getId(), event.getMember().getId());
        //撤回消息
        event.getGroup().sendMessage(messages.asMessageChain()).recallIn(60000);
        return ListeningStatus.LISTENING;
    }

    //群成员被踢
    @EventHandler
    public ListeningStatus onKick(MemberLeaveEvent.Kick event) {
        MessageChainBuilder messages = new MessageChainBuilder();
        messages.add("⚠ 群员减少提醒\n群员 ");
        messages.add(MessageUtil.UserNotify(event.getMember(), false));
        messages.add(" 已被 ");
        messages.add(MessageUtil.UserNotify(event.getOperator(), false));
        messages.add(" 移出群聊。\n(提醒消息将在1分钟内自动撤回)");
        //清理数据
        DBUtil.CleanDataAfterLeave(event.getGroup().getId(), event.getMember().getId());
        //撤回消息
        event.getGroup().sendMessage(messages.asMessageChain()).recallIn(60000);
        return ListeningStatus.LISTENING;
    }

    //机器人被移出群聊
    @EventHandler
    public ListeningStatus onBotKick(BotLeaveEvent.Active event) {
        //清理数据
        Exp.CleanExpData(event.getGroup().getId(), 0L);
        Coin.CleanCoinData(event.getGroup().getId(), 0L);
        Fishing.CleanFishingData(event.getGroup().getId(), 0L);
        return ListeningStatus.LISTENING;
    }

    //监听群成员荣誉修改
    @EventHandler
    public ListeningStatus onMemberHonorChange(MemberHonorChangeEvent event) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add(MessageUtil.UserNotify(event.getUser(), true));
        messageChainBuilder.add("\n于 " + TimeUtil.NowDateTime(new Date()) + " " +
                "喜提" +  " \"" + event.getHonorType() + "\" "
        );
        event.getGroup().sendMessage(messageChainBuilder.asMessageChain());
        return ListeningStatus.LISTENING;
    }

    //群龙王更改
    @EventHandler
    public ListeningStatus onGroupTalkativeChange(GroupTalkativeChangeEvent event) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("恭喜新龙王 ");
        messageChainBuilder.add(MessageUtil.UserNotify(event.getNow(), true));
        messageChainBuilder.add("\n前任龙王为 ");
        messageChainBuilder.add(MessageUtil.UserNotify(event.getPrevious(), false));
        return ListeningStatus.LISTENING;
    }

    //监听群头衔修改
    @EventHandler
    public ListeningStatus onMemberSpecialTitleChange(MemberSpecialTitleChangeEvent event) {
        String honorTypeName = event.getNew();
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("恭喜 ");
        messageChainBuilder.add(MessageUtil.UserNotify(event.getMember(), false));
        messageChainBuilder.add("\n于 " + TimeUtil.NowDateTime(new Date()) + " " +
                "喜提 " + MessageUtil.GetGroupHonorTypeName(honorTypeName) + "\n"
        );
        messageChainBuilder.add(new At(event.getMember().getId()));
        event.getGroup().sendMessage(messageChainBuilder.asMessageChain());
        return ListeningStatus.LISTENING;
    }

    //处理事件处理时抛出的异常
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        System.out.println(context + " " + exception.getMessage());
    }
}
