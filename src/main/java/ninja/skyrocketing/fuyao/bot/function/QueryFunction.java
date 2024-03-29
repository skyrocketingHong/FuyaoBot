package ninja.skyrocketing.fuyao.bot.function;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import lombok.NoArgsConstructor;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.fuyao.FuyaoBotApplication;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupMemberMessageCount;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupMessageCount;
import ninja.skyrocketing.fuyao.bot.pojo.user.UserMessage;
import ninja.skyrocketing.fuyao.bot.service.group.GroupMemberMessageCountService;
import ninja.skyrocketing.fuyao.bot.service.group.GroupMessageCountService;
import ninja.skyrocketing.fuyao.util.HttpUtil;
import ninja.skyrocketing.fuyao.util.MessageUtil;
import ninja.skyrocketing.fuyao.util.MusicSearchUtil;
import ninja.skyrocketing.fuyao.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static ninja.skyrocketing.fuyao.util.QueryUtil.nbnhhshQuery;

/**
 * @author skyrocketing Hong
 * @date 2021-03-05 16:27:53
 */

@Component
@NoArgsConstructor
public class QueryFunction {
    private static GroupMessageCountService groupMessageCountService;
    private static GroupMemberMessageCountService groupMemberMessageCountService;
    @Autowired
    private QueryFunction(
            GroupMessageCountService groupMessageCountService,
            GroupMemberMessageCountService groupMemberMessageCountService
    ) {
        QueryFunction.groupMessageCountService = groupMessageCountService;
        QueryFunction.groupMemberMessageCountService = groupMemberMessageCountService;
    }
    
    /**
     * 获取当前时间
     **/
    public static Message time(UserMessage userMessage) {
        LocalDateTime beijingTime = LocalDateTime.now();
        LocalDateTime ptTime = LocalDateTime.now(ZoneId.of("America/Los_Angeles"));
        ChineseDate chineseDate = new ChineseDate(DateUtil.parseDate(beijingTime.toLocalDate().toString()));
        userMessage.getMessageChainBuilder().add(TimeUtil.getClockEmoji(beijingTime.getHour()) +
                "中国标准时间 (UTC+8)" + "\n" +
                chineseDate + "\n" +
                beijingTime.format(DateTimeFormatter.ofPattern("yyyy 年 MM 月 dd 日 HH:mm:ss.SSS"))
                + "\n" +
                TimeUtil.getClockEmoji(ptTime.getHour()) +
                "太平洋时间 (UTC-7/UTC-8)" + "\n" +
                ptTime.format (DateTimeFormatter.ofPattern ("yyyy 年 MM 月 dd 日 HH:mm:ss.SSS")));
        return userMessage.getMessageChainBuilderAsMessageChain();
    }

    /**
     * 守望先锋街机模式查询
     **/
    public static Message getOverwatchArcadeModes(UserMessage userMessage) throws IOException, ParseException {
        MessageReceipt messageReceipt = MessageUtil.waitingMessage(userMessage, "正在等待 API 返回数据");
        JSONObject owModes = HttpUtil.readJsonFromURL("https://overwatcharcade.today/api/v1/overwatch/today");
        SimpleDateFormat updateDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        userMessage.getMessageChainBuilder().add("🎮 今日守望先锋街机模式列表\n更新时间: " +
                DateTime.of(updateDateTime.parse(owModes.getByPath("data.createdAt", String.class))) + "\n");
        JSONArray modesArray = owModes.getJSONObject("data").getJSONArray("modes");
        for (int i = 1; i < modesArray.size(); i++) {
            userMessage.getMessageChainBuilder().add(MessageUtil.getEmojiNumber(i) + " ");
            userMessage.getMessageChainBuilder().add("名称: " + modesArray.getJSONObject(i - 1).getByPath("name",String.class) + "\n");
            userMessage.getMessageChainBuilder().add("　   玩家人数: " + modesArray.getJSONObject(i - 1).getByPath("players",String.class) + "\n");
        }
        messageReceipt.recall();
        return userMessage.getMessageChainBuilder().asMessageChain();
    }

    /**
    * nbnhhsh 能不能好好说话
    * */
    public static Message nbnhhsh(UserMessage userMessage) {
        //从消息中分离出需要查询的字符串
        String msg = userMessage.getMessage().replaceFirst("^wtf\\s+", "");
        //判断msg是否合法
        if (msg.matches("[a-z]+")) {
            //发送等待提醒消息
            MessageReceipt<?> messageReceipt = MessageUtil.waitingMessage(userMessage, "正在等待 API 返回数据");
            //获取查询结果
            List<String> queryList = nbnhhshQuery(msg);
            //如果不为空，则返回消息
            if (queryList != null) {
                if (queryList.isEmpty()) {
                    messageReceipt.recall();
                    userMessage.getMessageChainBuilder().add("没有查询到与 \"" + msg + "\" 相关的结果");
                    return userMessage.getMessageChainBuilderAsMessageChain();
                }
                //统计个数
                int count = queryList.size();
                //拼接消息
                userMessage.getMessageChainBuilder().add("\"" + msg + "\" ");
                userMessage.getMessageChainBuilder().add("有以下 " + count + " 种结果: \n");
                for (int i = 0; i < count; ++i) {
                    userMessage.getMessageChainBuilder().add(i + 1 + ". " + queryList.get(i) + " ");
                }
            } else {
                userMessage.getMessageChainBuilder().add("❌ 查询失败，请稍后重试...");
                return userMessage.getMessageChainBuilderAsMessageChain();
            }
            messageReceipt.recall();
        } else {
            userMessage.getMessageChainBuilder().add("❌ \"" + msg + "\" 为非字母缩写");
        }
        return userMessage.getMessageChainBuilderAsMessageChain();
    }

    /**
    * 点歌
    * */
    public static Message music(UserMessage userMessage) throws IOException {
        String str = userMessage.getMessage().replaceFirst("^music\\s+", "");
        if (str.matches("^set qq$|^set 163$")) {

        }
        return MusicSearchUtil.musicQuery(str, false);
    }
    
    /**
     * 群内设置查询
     * */
    public static Message groupSettings(UserMessage userMessage) {
        userMessage.getMessageChainBuilder().add("⚙ 本群设置\n");
        userMessage.getMessageChainBuilder().add("自动加群审批: " + MessageUtil.getBooleanEmoji(userMessage.getGroupMessageEvent().getGroup().getSettings().isAutoApproveEnabled()) + "\n");
        userMessage.getMessageChainBuilder().add("允许群员邀请好友入群: " + MessageUtil.getBooleanEmoji(userMessage.getGroupMessageEvent().getGroup().getSettings().isAllowMemberInvite()) + "\n");
        userMessage.getMessageChainBuilder().add("匿名聊天: " + MessageUtil.getBooleanEmoji(userMessage.getGroupMessageEvent().getGroup().getSettings().isAnonymousChatEnabled()) + "\n");
        userMessage.getMessageChainBuilder().add("全体禁言: " + MessageUtil.getBooleanEmoji(userMessage.getGroupMessageEvent().getGroup().getSettings().isMuteAll()));
        return userMessage.getMessageChainBuilderAsMessageChain();
    }
    
    /**
     * bot 状态查询
     * */
    public static Message botStatus(UserMessage userMessage) {
        userMessage.getMessageChainBuilder().add("📊 扶摇 bot 的状态\n");
        userMessage.getMessageChainBuilder().add("截至 " + TimeUtil.nowDateTime() + "\n");
        userMessage.getMessageChainBuilder().add("已加入群聊 " + MessageUtil.getEmojiNumber(FuyaoBotApplication.bot.getGroups().size()) + " 个\n");
        userMessage.getMessageChainBuilder().add("已添加好友 " + MessageUtil.getEmojiNumber(FuyaoBotApplication.bot.getFriends().size()) + " 人\n");
        userMessage.getMessageChainBuilder().add("已被 " + MessageUtil.getEmojiNumber(FuyaoBotApplication.bot.getStrangers().size()) + " 人添加为单向好友");
        return userMessage.getMessageChainBuilderAsMessageChain();
    }
    
    /**
     * 群内消息数量查询
     * */
    public static Message messageCount(UserMessage userMessage) {
        GroupMessageCount groupMessageCount = groupMessageCountService.getGroupMessageCountById(userMessage.getUser().getGroupId());
        userMessage.getMessageChainBuilder().add(TimeUtil.zeroDateTime(new Date()) + " ");
        userMessage.getMessageChainBuilder().add("至 " + TimeUtil.getClockEmoji(groupMessageCount.getLastUpdateTime().getHours()) +  TimeUtil.timeFormatter(groupMessageCount.getLastUpdateTime()) + "\n");
        userMessage.getMessageChainBuilder().add("👥 本群总发送消息数量约为 " + MessageUtil.getEmojiNumber(groupMessageCount.getMessageCount() + 1) + " 条" + "\n");
        return userMessage.getMessageChainBuilderAsMessageChain();
    }
    
    /**
     * 个人信息查询
     * */
    public static Message memberStatus(UserMessage userMessage) {
        String messageWithoutTrigger = userMessage.getMessage().replaceFirst("member[\\s|-]?status\\s*@?", "");
        messageWithoutTrigger = messageWithoutTrigger.replaceAll("\\s", "");
        long queryId = 0;
        if (messageWithoutTrigger.length() > 0) {
            queryId = Long.parseLong(messageWithoutTrigger);
        }
        NormalMember normalMember;
        userMessage.getMessageChainBuilder().add("👤 群员信息查询" + "\n");
        //查询其他群员信息
        if (queryId != 0) {
            normalMember = userMessage.getGroupMessageEvent().getGroup().get(queryId);
            //判断是否为有效群员qq号
            if (normalMember == null) {
                userMessage.getMessageChainBuilder().add("⚠️ 群内无此群员");
                return userMessage.getMessageChainBuilderAsMessageChain();
            }
            GroupMemberMessageCount groupMemberMessageCount = groupMemberMessageCountService.selectGroupMemberMessageCountById(userMessage.getUser().getGroupId(), queryId);
            int messageCount = 0;
            //判断群员是否发送过消息或查询的结果是否为前一日
            if (groupMemberMessageCount != null && DateUtil.isSameDay(new Date(), groupMemberMessageCount.getLastUpdateTime())) {
                messageCount = groupMemberMessageCount.getMessageCount();
            }
            userMessage.getMessageChainBuilder().add("🪪 群名片 / 昵称: " + MessageUtil.userNotify(userMessage.getGroupMessageEvent().getGroup().get(queryId), false) + "\n");
            userMessage.getMessageChainBuilder().add("🦜 最后发言时间: " + TimeUtil.dateTimeFormatter(new Date(normalMember.getLastSpeakTimestamp() * 1000L)) + "\n");
            userMessage.getMessageChainBuilder().add("🗣️ 今日已发送消息数量约为 " + MessageUtil.getEmojiNumber(messageCount) + " 条" + "\n");
            userMessage.getMessageChainBuilder().add(
                    "🈲 是否禁言: " + (normalMember.isMuted()
                            ? "是\n" + "🉑 解禁时间: " + TimeUtil.dateTimeFormatter(new Date(System.currentTimeMillis() + normalMember.getMuteTimeRemaining() * 1000L))
                            : "否")
                            + "\n"
            );
        }
        //查询发送人的信息
        else {
            normalMember = userMessage.getGroupMessageEvent().getGroup().get(userMessage.getUser().getUserId());
            GroupMemberMessageCount groupMemberMessageCount = groupMemberMessageCountService.selectGroupMemberMessageCountByUser(userMessage.getUser());
            userMessage.getMessageChainBuilder().add("🗣️ 今日已发送消息数量约为 " + MessageUtil.getEmojiNumber(groupMemberMessageCount.getMessageCount()) + " 条" + "\n");
        }
        userMessage.getMessageChainBuilder().add("➕ 入群时间: " + TimeUtil.dateTimeFormatter(new Date(normalMember.getJoinTimestamp() * 1000L)) + "\n");
        userMessage.getMessageChainBuilder().add("👑 群头衔: " + ("".equals(normalMember.getSpecialTitle()) ? "无" : normalMember.getSpecialTitle()) + "\n");
        return userMessage.getMessageChainBuilderAsMessageChain();
    }
}
