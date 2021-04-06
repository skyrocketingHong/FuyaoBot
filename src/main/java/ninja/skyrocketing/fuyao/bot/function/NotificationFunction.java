package ninja.skyrocketing.fuyao.bot.function;

import cn.hutool.http.HttpUtil;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.FlashImage;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.fuyao.bot.config.MiraiBotConfig;
import ninja.skyrocketing.fuyao.bot.sender.group.GroupMessageSender;
import ninja.skyrocketing.fuyao.util.FileUtil;
import ninja.skyrocketing.fuyao.util.MessageUtil;
import ninja.skyrocketing.fuyao.util.TimeUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author skyrocketing Hong
 * @date 2021-03-08 14:02:10
 */

public class NotificationFunction {
    /**
    * 闪照提醒消息
    * */
    public static void flashImageNotification(GroupMessageEvent event) throws IOException {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        //向群内发送闪照消息
        messageChainBuilder.add(MessageUtil.userNotify(event.getSender(),true));
        messageChainBuilder.add("\n发了一张闪照，快来康康。");
        GroupMessageSender.sendMessageByGroupId(messageChainBuilder, event.getGroup());
    }

    /**
    * 红包提醒消息
    * */
    public static void redPackageNotification(GroupMessageEvent event) throws IOException {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add(MessageUtil.userNotify(event.getSender(), true));
        messageChainBuilder.add("\n发了一个红包，快来抢啊！");
        GroupMessageSender.sendMessageByGroupId(messageChainBuilder, event.getGroup());
    }
}
