package ninja.skyrocketing.bot.fuyao.function.functions;

import cn.hutool.http.HttpUtil;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.FlashImage;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.bot.fuyao.config.MiraiBotConfig;
import ninja.skyrocketing.bot.fuyao.sender.group.GroupMessageSender;
import ninja.skyrocketing.bot.fuyao.util.FileUtil;
import ninja.skyrocketing.bot.fuyao.util.MessageUtil;
import ninja.skyrocketing.bot.fuyao.util.TimeUtil;

import java.io.File;
import java.io.IOException;

/**
 * @Author skyrocketing Hong
 * @Date 2021-03-08 14:02:10
 */

public class NotificationFunction {
    /**
    * 闪照提醒消息
    * */
    public static void FlashImageNotification(GroupMessageEvent event) throws IOException {
        //向群内发送闪照消息
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add(MessageUtil.UserNotify(event.getSender(),true));
        messageChainBuilder.add("\n发了一张闪照，快来康康。");
        GroupMessageSender.SendMessageByGroupId(messageChainBuilder, event.getGroup());
    }

    /**
    * 红包提醒消息
    * */
    public static void RedPackageNotification(GroupMessageEvent event) throws IOException {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add(MessageUtil.UserNotify(event.getSender(), true));
        messageChainBuilder.add("\n发了一个红包，快来抢啊！");
        GroupMessageSender.SendMessageByGroupId(messageChainBuilder, event.getGroup());
    }
}
