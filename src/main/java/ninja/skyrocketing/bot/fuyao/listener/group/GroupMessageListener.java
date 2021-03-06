package ninja.skyrocketing.bot.fuyao.listener.group;

import cn.hutool.http.HttpUtil;
import kotlin.coroutines.CoroutineContext;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.FlashImage;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.bot.fuyao.config.MiraiBotConfig;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupRepeaterMessage;
import ninja.skyrocketing.bot.fuyao.util.LogUtil;
import ninja.skyrocketing.bot.fuyao.sender.group.GroupMessageSender;
import ninja.skyrocketing.bot.fuyao.service.bot.BotBanedGroupService;
import ninja.skyrocketing.bot.fuyao.service.bot.BotConfigService;
import ninja.skyrocketing.bot.fuyao.service.bot.BotReplyMessageService;
import ninja.skyrocketing.bot.fuyao.service.user.BotBanedUserService;
import ninja.skyrocketing.bot.fuyao.util.FileUtil;
import ninja.skyrocketing.bot.fuyao.util.MessageUtil;
import ninja.skyrocketing.bot.fuyao.util.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

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

    //监听所有群消息
    @EventHandler
    public ListeningStatus onMessage(GroupMessageEvent event) throws Exception {
        //首先判断是否为@机器人
        if (event.getMessage().toString().matches(".*\\[mirai:at:" + event.getBot().getId() + "].*") &&
                !event.getMessage().toString().matches(".*\\[mirai:quote:\\[\\d*],\\[\\d*]].*")) {
            //被@后返回帮助文案
            GroupMessageSender.SendMessageByGroupId(botConfigService.GetConfigValueByKey("reply"), event.getGroup().getId());
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
                        messageChainBuilder.add("\n");
                        messageChainBuilder.add(message);
                        GroupMessageSender.SendMessageByGroupId(messageChainBuilder, event.getGroup());
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
                GroupMessageSender.SendMessageByGroupId(messageChainBuilder, event.getGroup());
                //转存闪照
                Image flashImage = ((FlashImage) event.getMessage().get(1)).getImage();
                String imageURL = FileUtil.ImageIdToURL(flashImage);
                //文件名规则：群号-QQ号-日期（年月日时分秒微秒）
                String fileName = event.getGroup().getId() + "-" + event.getSender().getId() + "-" + TimeUtil.DateTimeFileName();
                String separator = File.separator;
                File imagePath = new File(MiraiBotConfig.jarPath +
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
                GroupMessageSender.SendMessageByGroupId(messageChainBuilder, event.getGroup());
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
                        MiraiBotConfig.GroupsRepeaterMessagesMap.get(event.getGroup().getId());
                //如果没有，就put进全局map
                if (groupRepeaterMessage == null) {
                    groupRepeaterMessage = new GroupRepeaterMessage(message, 1);
                    MiraiBotConfig.GroupsRepeaterMessagesMap.put(event.getGroup().getId(), groupRepeaterMessage);
                } else {
                    String messageInClass = groupRepeaterMessage.getMessage();
                    Integer timesInClass = groupRepeaterMessage.getTimes();
                    if (message.equals(messageInClass)) {
                        groupRepeaterMessage.setTimes(groupRepeaterMessage.getTimes() + 1);
                    } else {
                        MiraiBotConfig.GroupsRepeaterMessagesMap.remove(event.getGroup().getId());
                        return ListeningStatus.LISTENING;
                    }
                    if (timesInClass == 2) {
                        GroupMessageSender.SendMessageByGroupId(message, event.getGroup().getId());
                        MiraiBotConfig.GroupsRepeaterMessagesMap.remove(event.getGroup().getId());
                        return ListeningStatus.LISTENING;
                    }
                }
                return ListeningStatus.LISTENING;
            }
        }
    }

    //处理事件处理时抛出的异常
    @SneakyThrows
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        LogUtil.GroupEventFile(context + "\n" + exception, "抛出异常");
    }
}
