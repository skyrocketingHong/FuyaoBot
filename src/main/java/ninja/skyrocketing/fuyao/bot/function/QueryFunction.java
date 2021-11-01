package ninja.skyrocketing.fuyao.bot.function;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.fuyao.FuyaoBotApplication;
import ninja.skyrocketing.fuyao.bot.pojo.user.UserMessage;
import ninja.skyrocketing.fuyao.util.HttpUtil;
import ninja.skyrocketing.fuyao.util.MessageUtil;
import ninja.skyrocketing.fuyao.util.MusicSearchUtil;
import ninja.skyrocketing.fuyao.util.TimeUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author skyrocketing Hong
 * @date 2021-03-05 16:27:53
 */

public class QueryFunction {
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
            //拼接查询参数
            String param = "{\"text\" : \"" + msg + "\"}";
            //存储返回的json字符串
            String nbnhhsh = cn.hutool.http.HttpUtil.post("https://lab.magiconch.com/api/nbnhhsh/guess", param);
            //如果字符串是json数组，则返回消息
            if (JSONUtil.isJsonArray(nbnhhsh)) {
                //将字符串转换为json数组
                JSONArray nbnhhshArray = JSONUtil.parseArray(nbnhhsh);
                //获取可能的结果，去掉中括号并分割成String数组
                String[] trans = nbnhhshArray.getByPath("trans", String.class)
                        .replaceAll("\\[|]", "")
                        .split(",");
                if (trans[0].equals("null")) {
                    messageReceipt.recall();
                    userMessage.getMessageChainBuilder().add("没有查询到与 \"" + msg + "\" 相关的结果");
                    return userMessage.getMessageChainBuilderAsMessageChain();
                }
                //统计个数
                int count = trans.length;
                //拼接消息
                userMessage.getMessageChainBuilder().add("\"" + msg + "\" ");
                userMessage.getMessageChainBuilder().add("有以下 " + count + " 种结果: \n");
                for (int i = 0; i < count; ++i) {
                    userMessage.getMessageChainBuilder().add(i + 1 + ". " + trans[i] + " ");
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
}
