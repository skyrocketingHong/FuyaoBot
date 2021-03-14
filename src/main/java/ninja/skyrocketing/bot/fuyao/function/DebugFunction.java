package ninja.skyrocketing.bot.fuyao.function;

import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupMessage;

/**
 * @author skyrocketing Hong
 * @date 2021-03-06 20:39:57
 */

public class DebugFunction {
    public static Message debug(GroupMessage groupMessage) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        //debug代码开始

        //debug代码结束
        return messageChainBuilder.asMessageChain();
    }
}
