package ninja.skyrocketing.fuyao.bot.function;

import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupMessage;

/**
 * @author skyrocketing Hong
 * @date 2021-03-05 17:09:44
 */

public class FunctionDisabledMessage {
    /**
     * 功能被禁用提醒
     * */
    public static Message functionDisabled(GroupMessage groupMessage) {
        groupMessage.getMessageChainBuilder().add("⚠ 维护中" + "\n" +
                "\"" + groupMessage.getFunctionName() + "\" " +
                "功能已暂时关闭。");
        return groupMessage.getMessageChainBuilderAsMessageChain();
    }

    /**
     * 管理员功能提醒
     * */
    public static Message adminFunction(GroupMessage groupMessage) {
        groupMessage.getMessageChainBuilder().add("⚠ 非管理员无法使用 " +
                "\"" + groupMessage.getFunctionName() + "\" " +
                "功能，请联系管理员！");
        return groupMessage.getMessageChainBuilderAsMessageChain();
    }
}
