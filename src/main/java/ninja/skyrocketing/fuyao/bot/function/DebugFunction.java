package ninja.skyrocketing.fuyao.bot.function;

import cn.hutool.http.HttpUtil;
import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.fuyao.bot.config.GlobalVariables;
import ninja.skyrocketing.fuyao.bot.pojo.user.UserMessage;
import ninja.skyrocketing.fuyao.util.FileUtil;
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
    public static Message hearthStoneDebug(UserMessage userMessage) {
        String needSet = userMessage.getMessage().replaceAll("hs[\\s|-]?debug\\s", "");
        String[] needSets = needSet.split(" ");
        File jsonFile = new File(GlobalVariables.getGlobalVariables().getHearthstoneFilePath() + File.separator + "json" + File.separator + "cards.collectible.json");
        HttpUtil.downloadFile("https://api.hearthstonejson.com/v1/latest/zhCN/cards.collectible.json", jsonFile);
        int num = HearthstoneUtil.readJsonFromFile(jsonFile, needSets[0], needSets[1]);
        userMessage.getMessageChainBuilder().add("已添加 \"" + needSets[1] + "\" 中的 " + num + " 张卡。");
        return userMessage.getMessageChainBuilderAsMessageChain();
    }
}
