package ninja.skyrocketing.fuyao.bot.function;

import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.fuyao.bot.pojo.user.UserMessage;

/**
 * @author skyrocketing Hong
 * @date 2021-03-05 17:09:44
 */

public class FunctionDisabledMessage {
    /**
     * 功能被禁用提醒
     * */
    public static Message functionDisabled(UserMessage userMessage) {
        userMessage.getMessageChainBuilder().add("⚠ 维护中" + "\n" +
                "\"" + userMessage.getFunctionName() + "\" " +
                "功能已暂时关闭。");
        return userMessage.getMessageChainBuilderAsMessageChain();
    }

    /**
     * 管理员功能提醒
     * */
    public static Message adminFunction(UserMessage userMessage) {
        userMessage.getMessageChainBuilder().add("⚠ 非管理员无法使用 " +
                "\"" + userMessage.getFunctionName() + "\" " +
                "功能，请联系管理员！");
        return userMessage.getMessageChainBuilderAsMessageChain();
    }
}
