package ninja.skyrocketing.fuyao.bot.function;

import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupMessage;
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
    public static Message debug(GroupMessage groupMessage) {
        //debug代码开始

        //debug代码结束
        return groupMessage.getMessageChainBuilderAsMessageChain();
    }

    /**
     * 炉石卡牌配置
     * */
    public static Message hearthStoneDebug(GroupMessage groupMessage) throws IOException {
        File file = new File("D:\\cards.collectible.json");
        int num = HearthstoneUtil.readJsonFromFile(file, "THE_BARRENS");
        groupMessage.getMessageChainBuilder().add("已添加 " + num + " 张卡。");
        return groupMessage.getMessageChainBuilderAsMessageChain();
    }
}
