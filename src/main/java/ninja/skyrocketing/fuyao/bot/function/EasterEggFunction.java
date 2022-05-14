package ninja.skyrocketing.fuyao.bot.function;

import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.MessageReceipt;
import ninja.skyrocketing.fuyao.bot.config.GlobalVariables;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupMessageInfo;
import ninja.skyrocketing.fuyao.bot.sender.group.GroupMessageSender;
import ninja.skyrocketing.fuyao.util.MessageUtil;
import ninja.skyrocketing.fuyao.util.RandomUtil;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author skyrocketing Hong
 * @date 2021-03-08 14:07:44
 */

public class EasterEggFunction {
    /**
    * 消息复读
    * */
    public static void repeater(GroupMessageEvent event) {
        //群号
        long groupId = event.getGroup().getId();
        //消息
        String message = MessageUtil.removeSource(event.getMessage());
        //消息ID
        int messageId = MessageUtil.getMessageIDInGroup(event.getMessage());
        //GroupAndMessageId
        GroupMessageInfo groupMessageInfo = new GroupMessageInfo(event);
        //获取当前群的消息列表
        List<String> groupMessageList = GlobalVariables.getGlobalVariables().getGroupMessageMap().get(groupId);
        //如果为null，则初始化
        if (groupMessageList == null) {
            groupMessageList = new ArrayList<>(3);
        }
        //如果已经有三个了，则直接移除第一条消息
        if (groupMessageList.size() == 3) {
            groupMessageList.remove(0);
        }
        //添加当前消息
        groupMessageList.add(message);
        //放回全局Map中
        GlobalVariables.getGlobalVariables().getGroupMessageMap().put(groupId, groupMessageList);
        //判断三条消息是否相等，是否为已复读的消息
        if (groupMessageList.size() == 3
                && MessageUtil.isSame(groupMessageList.get(0), groupMessageList.get(1), groupMessageList.get(2))
        ) {
            String repeatedMessage = GlobalVariables.getGlobalVariables().getGroupRepeatedMessage().get(groupId);
            if (repeatedMessage == null || !repeatedMessage.equals(message)) {
                //发送消息
                MessageReceipt<Group> messageReceipt = GroupMessageSender.sendMessageByGroupIdWithReceipt(event.getMessage(), event.getGroup());
                //存放已复读的消息
                GlobalVariables.getGlobalVariables().getGroupRepeatedMessage().put(groupId, message);
                //存放已发送消息的回执，便于撤回
                GlobalVariables.getGlobalVariables().getGroupSentMessageReceipt().put(groupMessageInfo, messageReceipt);
                //存放触发消息、触发消息的第一个前驱和第二个前驱的ID
                GlobalVariables.getGlobalVariables().getTriggerGroupMessageInfoMap().put(groupMessageInfo, false);
                GlobalVariables.getGlobalVariables().getTriggerGroupMessageInfoMap().put(new GroupMessageInfo(groupId, messageId - 1), true);
                GlobalVariables.getGlobalVariables().getTriggerGroupMessageInfoMap().put(new GroupMessageInfo(groupId, messageId - 2), true);
            }
        }
    }

    /**
     * 人工智障回复“为什么”或“***吗”消息
     * */
    public static void stupidAiForWhy(GroupMessageEvent event) throws NoSuchAlgorithmException {
        List<String> whyMessageList = new ArrayList<>();
        whyMessageList.add("不知道，下一个");
        whyMessageList.add("你可以试试问一下神奇的魔法海螺");
        whyMessageList.add("看看有没有群友知道");
        whyMessageList.add("你可以试试百度啊");
        whyMessageList.add("你猜为什么");
        int randomNum = RandomUtil.secureRandomNum(0, whyMessageList.size());
        GroupMessageSender.sendMessageByGroupId("人工智障扶摇 bot 为您服务：\n" + whyMessageList.get(randomNum), event.getGroup().getId());
    }
}
