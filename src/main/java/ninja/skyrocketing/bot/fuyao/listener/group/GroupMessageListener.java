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
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupRepeaterMessage;
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
            GroupMessageSender.SendMessageByGroupId(botConfigService.GetConfigValueByKey("reply_after_at"), event.getGroup().getId());
            return ListeningStatus.LISTENING;
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
                        messageChainBuilder.add("\n" + message);
                        GroupMessageSender.SendMessageByGroupId(messageChainBuilder.asMessageChain(), event.getGroup().getId());
                    }
                }
                return ListeningStatus.LISTENING;
            }
            //拦截闪照消息，使用mirai码判断
            else if (event.getMessage().toString().matches(".*\\[mirai:flash:\\{[0-9A-F]{8}(-[0-9A-F]{4}){3}-[0-9A-F]{12}\\}\\.jpg].*")) {
                //向群内发送闪照消息
                MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
                messageChainBuilder.add(MessageUtil.UserNotify(event.getSender(),true));
                messageChainBuilder.add("\n发了一张闪照，快来康康。");
                GroupMessageSender.SendMessageByGroupId(messageChainBuilder.asMessageChain(), event.getGroup().getId());
                //转存闪照
                Image flashImage = ((FlashImage) event.getMessage().get(1)).getImage();
                String imageURL = FileUtil.ImageIdToURL(flashImage);
                //文件名规则：群号-QQ号-日期（年月日时分秒微秒）
                String fileName = event.getGroup().getId() + "-" + event.getSender().getId() + "-" + TimeUtil.DateTimeFileName();
                String separator = File.separator;
                File imagePath = new File(FileUtil.GetPath() +
                        separator + "cache" +
                        separator + "Flash Image" +
                        separator + TimeUtil.DateFileName() +
                        separator + fileName + ".jpg"
                );
                HttpUtil.downloadFile(imageURL, imagePath);
                //继续监听
                return ListeningStatus.LISTENING;
            }
            //拦截红包消息
            else if (event.getMessage().contentToString().matches("\\[QQ红包].+新版手机QQ查.+")){
                MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
                messageChainBuilder.add(MessageUtil.UserNotify(event.getSender(), true));
                messageChainBuilder.add("\n发了一个红包，快来抢啊！");
                GroupMessageSender.SendMessageByGroupId(messageChainBuilder.asMessageChain(), event.getGroup().getId());
                return ListeningStatus.LISTENING;
            }
            //拦截判断复读消息
            else {
                String message = event.getMessage().contentToString();
                if (message.matches("\\[.*]")) {
                    return ListeningStatus.LISTENING;
                }
                //查看全局map中是否有这个群
                GroupRepeaterMessage groupRepeaterMessage =
                        FuyaoBotApplication.GroupsRepeaterMessagesMap.get(event.getGroup().getId());
                //如果没有，就put进全局map
                if (groupRepeaterMessage == null) {
                    groupRepeaterMessage = new GroupRepeaterMessage(message, 1);
                    FuyaoBotApplication.GroupsRepeaterMessagesMap.put(event.getGroup().getId(), groupRepeaterMessage);
                } else {
                    String messageInClass = groupRepeaterMessage.getMessage();
                    Integer timesInClass = groupRepeaterMessage.getTimes();
                    if (message.equals(messageInClass)) {
                        groupRepeaterMessage.setTimes(groupRepeaterMessage.getTimes() + 1);
                    } else {
                        FuyaoBotApplication.GroupsRepeaterMessagesMap.remove(event.getGroup().getId());
                        return ListeningStatus.LISTENING;
                    }
                    if (timesInClass == 2) {
                        GroupMessageSender.SendMessageByGroupId(message, event.getGroup().getId());
                        FuyaoBotApplication.GroupsRepeaterMessagesMap.remove(event.getGroup().getId());
                        return ListeningStatus.LISTENING;
                    }
                }
                return ListeningStatus.LISTENING;
            }
        }
    }

    //监听成员进群，并发送欢迎消息
    @EventHandler
    public ListeningStatus onJoin(MemberJoinEvent.Active event) throws IOException {
        //生成消息
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("👏 欢迎第" + (event.getGroup().getMembers().size() + 1) + "名群员。" + "\n");
        messageChainBuilder.add(MessageUtil.UploadImageToGroup(event.getGroup(), event.getMember()));
        messageChainBuilder.add(MessageUtil.UserNotify(event.getMember(), true));
        messageChainBuilder.add("\n记得阅读群公告（如果有的话）哦！");
        GroupMessageSender.SendMessageByGroupId(messageChainBuilder.asMessageChain(), event.getGroupId());
        return ListeningStatus.LISTENING;
    }

    @EventHandler
    public ListeningStatus onInvite(MemberJoinEvent.Invite event) throws IOException {
        //生成消息
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("👏 欢迎由 ");
        messageChainBuilder.add(MessageUtil.UserNotify(event.getInvitor(), false));
        messageChainBuilder.add(" 邀请的第 " + (event.getGroup().getMembers().size() + 1) + " 名群员：" + "\n");
        messageChainBuilder.add(MessageUtil.UploadImageToGroup(event.getGroup(), event.getMember()));
        messageChainBuilder.add(MessageUtil.UserNotify(event.getMember(), false));
        messageChainBuilder.add("\n" + "记得阅读群公告（如果有的话）哦！");
        GroupMessageSender.SendMessageByGroupId(messageChainBuilder.asMessageChain(), event.getGroupId());
        return ListeningStatus.LISTENING;
    }

    //监听群员主动退群
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
        GroupMessageSender.SendMessageByGroupId(messageChainBuilder.asMessageChain(), event.getGroupId(), 60000);
        return ListeningStatus.LISTENING;
    }

    //群成员被踢
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
        GroupMessageSender.SendMessageByGroupId(messageChainBuilder.asMessageChain(), event.getGroupId());
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
    public ListeningStatus onMemberHonorChange(MemberHonorChangeEvent event) throws IOException {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add(MessageUtil.UserNotify(event.getUser(), true));
        messageChainBuilder.add("\n于 " + TimeUtil.NowDateTime(new Date()) + " " +
                "喜提" +  " \"" + event.getHonorType() + "\" "
        );
        GroupMessageSender.SendMessageByGroupId(messageChainBuilder.asMessageChain(), event.getGroup().getId());
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
    public ListeningStatus onMemberSpecialTitleChange(MemberSpecialTitleChangeEvent event) throws IOException {
        String honorTypeName = event.getNew();
        String honorName = MessageUtil.GetGroupHonorTypeName(honorTypeName);
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("恭喜 ");
        messageChainBuilder.add(MessageUtil.UserNotify(event.getMember(), false));
        messageChainBuilder.add("\n于 " + TimeUtil.NowDateTime(new Date()) + " " +
                "喜提 " + honorName + "\n"
        );
        messageChainBuilder.add(new At(event.getMember().getId()));
        GroupMessageSender.SendMessageByGroupId(messageChainBuilder.asMessageChain(), event.getGroupId());
        return ListeningStatus.LISTENING;
    }

    //处理事件处理时抛出的异常
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        System.out.println(context + " " + exception.getMessage());
    }
}
