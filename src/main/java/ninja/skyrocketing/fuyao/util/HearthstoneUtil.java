package ninja.skyrocketing.fuyao.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import ninja.skyrocketing.fuyao.bot.pojo.game.GameHsCard;
import ninja.skyrocketing.fuyao.bot.service.game.GameHsCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * @author skyrocketing Hong
 * @date 2021-03-05 22:41:18
 */

@Component
public class HearthstoneUtil {
    private static GameHsCardService gameHsCardService;
    @Autowired
    public HearthstoneUtil(GameHsCardService gameHsCardService) {
        HearthstoneUtil.gameHsCardService = gameHsCardService;
    }

    /**
    * 将json文件中的数据插入数据库中
    * @param file json文件路径
    * */
    public static int readJsonFromFile(File file, String needSet) {
        JSONArray jsonArray = JSONUtil.parseArray(FileUtil.readString(file, StandardCharsets.UTF_8));
        int cardNum = 0;
        for (int i = 0; i < jsonArray.size(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Integer id = Integer.parseInt(jsonObject.get("dbfId", String.class));
            String set = jsonObject.get("set", String.class);
            if (!set.equals(needSet)) {
                continue;
            }
            String name = jsonObject.get("name", String.class);
            String rarity = jsonObject.get("rarity", String.class);
            String cardId = jsonObject.get("id", String.class);
            String imgUrl = "https://art.hearthstonejson.com/v1/render/latest/zhCN/256x/" + cardId + ".png";
            gameHsCardService.insertACard(new GameHsCard(id, set, name, rarity, imgUrl));
            ++cardNum;
        }
        return cardNum;
    }
}
