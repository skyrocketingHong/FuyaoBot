package ninja.skyrocketing.fuyao.bot.function;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupMessage;
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
    public static Message time(GroupMessage groupMessage) {
        LocalDateTime beijingTime = LocalDateTime.now();
        LocalDateTime ptTime = LocalDateTime.now(ZoneId.of("America/Los_Angeles"));
        ChineseDate chineseDate = new ChineseDate(DateUtil.parseDate(beijingTime.toLocalDate().toString()));
        groupMessage.getMessageChainBuilder().add(TimeUtil.getClockEmoji(beijingTime.getHour()) +
                "中国标准时间 (UTC+8)" + "\n" +
                chineseDate + "\n" +
                beijingTime.format(DateTimeFormatter.ofPattern("yyyy 年 MM 月 dd 日 HH:mm:ss.SSS"))
                + "\n" +
                TimeUtil.getClockEmoji(ptTime.getHour()) +
                "太平洋时间 (UTC-7/UTC-8)" + "\n" +
                ptTime.format (DateTimeFormatter.ofPattern ("yyyy 年 MM 月 dd 日 HH:mm:ss.SSS")));
        return groupMessage.getMessageChainBuilderAsMessageChain();
    }

    /**
     * 守望先锋街机模式查询
     **/
    public static Message getOverwatchArcadeModes(GroupMessage groupMessage) throws IOException, ParseException {
        MessageReceipt<Contact> messageReceipt = MessageUtil.waitingMessage(groupMessage, "正在等待 API 返回数据");
        JSONObject owModes = HttpUtil.readJsonFromURL("https://overwatcharcade.today/api/overwatch/today");
        SimpleDateFormat updateDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
        groupMessage.getMessageChainBuilder().add("今日守望先锋街机模式列表\n更新时间：" +
                DateTime.of(updateDateTime.parse(owModes.getByPath("created_at", String.class))) + "\n");
        for (int i = 1; i < 8; i++) {
            groupMessage.getMessageChainBuilder().add(i + ". " + owModes.getByPath("modes.tile_" + i + ".name", String.class) + "\n");
        }
        messageReceipt.recall();
        return groupMessage.getMessageChainBuilder().asMessageChain();
    }

    /**
    * nbnhhsh 能不能好好说话
    * */
    public static Message nbnhhsh(GroupMessage groupMessage) {
        MessageReceipt<Contact> messageReceipt = MessageUtil.waitingMessage(groupMessage, "正在等待 API 返回数据");
        //从消息中分离出需要查询的字符串
        String msg = groupMessage.getMessage().replaceFirst("^wtf\\s+", "");
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
            if (trans[0] == "null") {
                groupMessage.getMessageChainBuilder().add("没有查询到与 " + "\"" + msg + "\" " + " 相关的结果");
                return groupMessage.getMessageChainBuilderAsMessageChain();
            }
            //统计个数
            int count = trans.length;
            //拼接消息
            groupMessage.getMessageChainBuilder().add("\"" + msg + "\" ");
            groupMessage.getMessageChainBuilder().add("有以下 " + count + " 种可能: \n");
            for (int i = 0; i < count; ++i) {
                groupMessage.getMessageChainBuilder().add(i + 1 + ". " + trans[i] + " ");
            }
        } else {
            groupMessage.getMessageChainBuilder().add("查询失败，请稍后重试...");
            return groupMessage.getMessageChainBuilderAsMessageChain();
        }
        messageReceipt.recall();
        return groupMessage.getMessageChainBuilderAsMessageChain();
    }

    /**
    * 点歌
    * */
    public static Message music(GroupMessage groupMessage) throws IOException {
        String str = groupMessage.getMessage().replaceFirst("^music\\s+", "");
        if (str.matches("^set qq$|^set 163$")) {

        }
        return MusicSearchUtil.musicQuery(str, false);
    }
}
