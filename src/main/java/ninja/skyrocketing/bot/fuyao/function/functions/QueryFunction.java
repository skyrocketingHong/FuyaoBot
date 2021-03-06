package ninja.skyrocketing.bot.fuyao.function.functions;

import cn.hutool.core.date.ChineseDate;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupMessage;
import ninja.skyrocketing.bot.fuyao.util.HttpUtil;
import ninja.skyrocketing.bot.fuyao.util.MessageUtil;
import ninja.skyrocketing.bot.fuyao.util.TimeUtil;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @Author skyrocketing Hong
 * @Date 2021-03-05 16:27:53
 */

public class QueryFunction {
    /**
     * 获取当前时间
     **/
    public static Message Time(GroupMessage groupMessage) {
        LocalDateTime beijingTime = LocalDateTime.now();
        LocalDateTime ptTime = LocalDateTime.now(ZoneId.of("America/Los_Angeles"));
        ChineseDate chineseDate = new ChineseDate(DateUtil.parseDate(beijingTime.toLocalDate().toString()));
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add(TimeUtil.GetClockEmoji(beijingTime.getHour()) +
                "中国标准时间 (UTC+8)" + "\n" +
                chineseDate + "\n" +
                beijingTime.format(DateTimeFormatter.ofPattern("yyyy 年 MM 月 dd 日 HH:mm:ss.SSS"))
                + "\n" +
                TimeUtil.GetClockEmoji(ptTime.getHour()) +
                "太平洋时间 (UTC-7/UTC-8)" + "\n" +
                ptTime.format (DateTimeFormatter.ofPattern ("yyyy 年 MM 月 dd 日 HH:mm:ss.SSS")));
        return messageChainBuilder.asMessageChain();
    }

    /**
     * 守望先锋街机模式查询
     **/
    public static Message GetOverwatchArcadeModes(GroupMessage groupMessage) throws IOException, ParseException {
        MessageReceipt<Contact> messageReceipt = MessageUtil.WaitingForAPI(groupMessage);
        JSONObject owModes = HttpUtil.ReadJsonFromURL("https://overwatcharcade.today/api/overwatch/today");
        SimpleDateFormat updateDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
        MessageChainBuilder messages = new MessageChainBuilder();
        messages.add("今日守望先锋街机模式列表\n更新时间：" +
                DateTime.of(updateDateTime.parse(owModes.getByPath("created_at", String.class))) + "\n");
        for (int i = 1; i < 8; i++) {
            messages.add(i + ". " + owModes.getByPath("modes.tile_" + i + ".name", String.class) + "\n");
        }
        messageReceipt.recall();
        return messages.asMessageChain();
    }
}
