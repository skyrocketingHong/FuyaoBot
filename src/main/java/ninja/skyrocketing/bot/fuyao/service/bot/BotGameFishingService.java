package ninja.skyrocketing.bot.fuyao.service.bot;

import ninja.skyrocketing.bot.fuyao.pojo.bot.BotGameFishing;

import java.util.List;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-29 13:20:23
 */
public interface BotGameFishingService {
    List<BotGameFishing> GetAllFish();
    String GetFishNameById(String id);
    Long GetFishValueById(String id);
}
