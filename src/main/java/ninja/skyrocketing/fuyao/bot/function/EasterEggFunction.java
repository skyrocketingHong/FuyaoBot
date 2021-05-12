package ninja.skyrocketing.fuyao.bot.function;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import ninja.skyrocketing.fuyao.bot.config.MiraiBotConfig;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupRepeaterMessage;
import ninja.skyrocketing.fuyao.bot.sender.group.GroupMessageSender;
import ninja.skyrocketing.fuyao.util.MessageUtil;
import ninja.skyrocketing.fuyao.util.RandomUtil;

import java.io.IOException;
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
    public static void repeater(GroupMessageEvent event) throws IOException {
        //群号
        Long groupId = event.getGroup().getId();
        //保存移除了source后的消息
        String messageInGroup = MessageUtil.removeSource(event.getMessage());
        //已复读的消息
        String messageIsRepeated = MiraiBotConfig.GroupRepeatedMessagesMap.get(groupId);
        //查看全局map中是否有这个群
        GroupRepeaterMessage groupRepeaterMessage = MiraiBotConfig.GroupsRepeaterMessagesMap.get(groupId);
        //如果map里面没有这个群，就将群号作为key，消息和次数（GroupRepeaterMessage类）作为value放进map中，并将次数设置为1次
        if (groupRepeaterMessage == null) {
            groupRepeaterMessage = new GroupRepeaterMessage(messageInGroup, 1);
            MiraiBotConfig.GroupsRepeaterMessagesMap.put(groupId, groupRepeaterMessage);
            //当消息与GroupRepeatedMessagesMap中的不同时，就将之前已经复读的消息移除
            if (!messageInGroup.equals(messageIsRepeated)) {
                MiraiBotConfig.GroupRepeatedMessagesMap.remove(groupId);
            }
        } else {
            /*
              如果存在这个群，就将GroupRepeaterMessage类中的消息取出来，
              与函数传进来的消息作对比，如果不同，则说明这条消息与上一条消息不同，不构成复读条件
              将key（群号）从map中移除。
              */
            String messageInClass = groupRepeaterMessage.getMessage();
            Integer timesInClass = groupRepeaterMessage.getTimes();
            if (messageInGroup.equals(messageInClass) && !messageInGroup.equals(messageIsRepeated)) {
                //消息次数+1
                groupRepeaterMessage.setTimes(++timesInClass);
                //当消息次数为2时，则触发复读
                if (timesInClass == 2) {
                    //发送消息
                    GroupMessageSender.sendMessageByGroupId(event.getMessage(), event.getGroup());
                    //从可复读消息map中移除已复读的消息
                    MiraiBotConfig.GroupsRepeaterMessagesMap.remove(groupId);
                    //在已复读的群中加入这条消息，避免重复复读
                    MiraiBotConfig.GroupRepeatedMessagesMap.put(groupId, messageInGroup);
                }
            } else {
                //不相同时，直接移出map
                MiraiBotConfig.GroupsRepeaterMessagesMap.remove(groupId);
            }
        }
    }

    /**
     * 人工智障回复“为什么”或“***吗”消息
     * */
    public static void stupidAiForWhy(GroupMessageEvent event) throws IOException {
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
