package ninja.skyrocketing.fuyao.bot.function;

import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupMessage;

/**
 * @author skyrocketing Hong
 * @date 2021-03-05 17:09:44
 */

public class FunctionDisabledMessage {
    public static Message functionDisabled(GroupMessage groupMessage) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("⚠ 维护中" + "\n" +
                "\"" + groupMessage.getMessage() + "\" " +
                "功能已暂时关闭。");
        return messageChainBuilder.asMessageChain();
    }
}
