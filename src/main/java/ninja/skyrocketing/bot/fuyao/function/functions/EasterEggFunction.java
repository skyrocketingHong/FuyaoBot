package ninja.skyrocketing.bot.fuyao.function.functions;

import net.mamoe.mirai.event.events.GroupMessageEvent;
import ninja.skyrocketing.bot.fuyao.config.MiraiBotConfig;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupRepeaterMessage;
import ninja.skyrocketing.bot.fuyao.sender.group.GroupMessageSender;
import ninja.skyrocketing.bot.fuyao.util.MessageUtil;

import java.io.IOException;

/**
 * @Author skyrocketing Hong
 * @Date 2021-03-08 14:07:44
 */

public class EasterEggFunction {
    /**
    * 消息复读
    * */
    public static void Repeater(GroupMessageEvent event) throws IOException {
        //群号
        Long groupId = event.getGroup().getId();
        //保存移除了source后的消息
        String messageInGroup = MessageUtil.RemoveSource(event.getMessage());
        //已复读的消息
        String messageIsRepeated = MiraiBotConfig.GroupRepeatedMessagesMap.get(groupId);
        //查看全局map中是否有这个群
        GroupRepeaterMessage groupRepeaterMessage = MiraiBotConfig.GroupsRepeaterMessagesMap.get(groupId);
        //如果map里面没有这个群，就将群号作为key，消息和次数作为value（GroupRepeaterMessage类）放进map中，并将次数设置为1次
        if (groupRepeaterMessage == null) {
            groupRepeaterMessage = new GroupRepeaterMessage(messageInGroup, 1);
            MiraiBotConfig.GroupsRepeaterMessagesMap.put(groupId, groupRepeaterMessage);
            //当消息与GroupRepeatedMessagesMap中的不同时，就将之前已经复读的消息移除
            if (!messageInGroup.equals(messageIsRepeated)) {
                MiraiBotConfig.GroupRepeatedMessagesMap.remove(groupId);
            }
        } else {
            /*
             * 如果存在这个群，就将GroupRepeaterMessage类中的消息取出来，
             * 与函数传进来的消息作对比，如果不同，则说明这条消息与上一条消息不同，不构成复读条件
             * 将key（群号）从map中移除。
             */
            String messageInClass = groupRepeaterMessage.getMessage();
            Integer timesInClass = groupRepeaterMessage.getTimes();
            if (messageInGroup.equals(messageInClass) && !messageInGroup.equals(messageIsRepeated)) {
                //消息次数+1
                groupRepeaterMessage.setTimes(++timesInClass);
                //当消息次数为2时，则触发复读
                if (timesInClass == 2) {
                    //发送消息
                    GroupMessageSender.SendMessageByGroupId(event.getMessage(), event.getGroup());
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
}
