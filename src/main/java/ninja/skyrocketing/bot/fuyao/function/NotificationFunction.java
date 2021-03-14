package ninja.skyrocketing.bot.fuyao.function;

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
 * @author skyrocketing Hong
 * @date 2021-03-08 14:02:10
 */

public class NotificationFunction {
    /**
    * 闪照提醒消息
    * */
    public static void flashImageNotification(GroupMessageEvent event) throws IOException {
        //向群内发送闪照消息
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add(MessageUtil.userNotify(event.getSender(),true));
        messageChainBuilder.add("\n发了一张闪照，快来康康。");
        GroupMessageSender.sendMessageByGroupId(messageChainBuilder, event.getGroup());
        //转存闪照
        Image flashImage = ((FlashImage) event.getMessage().get(1)).getImage();
        String imageURL = FileUtil.imageIdToURL(flashImage);
        //文件名规则：群号-QQ号-日期（年月日时分秒微秒）
        String fileName = event.getGroup().getId() + "-" + event.getSender().getId() + "-" + TimeUtil.dateTimeFileName();
        String separator = File.separator;
        File imagePath = new File(MiraiBotConfig.jarPath +
                separator + "cache" +
                separator + "Flash Image" +
                separator + TimeUtil.dateFileName() +
                separator + fileName + ".jpg"
        );
        HttpUtil.downloadFile(imageURL, imagePath);
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
