package ninja.skyrocketing.fuyao.bot.function;

import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.fuyao.bot.pojo.user.UserMessage;
import ninja.skyrocketing.fuyao.util.HearthstoneUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author skyrocketing Hong
 * @date 2021-03-06 20:39:57
 */

public class DebugFunction {
    /**
     * 临时测试代码
     * */
    public static Message debug(UserMessage userMessage) {
        //debug代码开始

        //debug代码结束
        return userMessage.getMessageChainBuilderAsMessageChain();
    }

    /**
     * 炉石卡牌配置
     * */
    public static Message hearthStoneDebug(UserMessage userMessage) throws IOException {
        File file = new File("D:\\cards.collectible.json");
        int num = HearthstoneUtil.readJsonFromFile(file, "THE_BARRENS");
        userMessage.getMessageChainBuilder().add("已添加 " + num + " 张卡。");
        return userMessage.getMessageChainBuilderAsMessageChain();
    }
}
