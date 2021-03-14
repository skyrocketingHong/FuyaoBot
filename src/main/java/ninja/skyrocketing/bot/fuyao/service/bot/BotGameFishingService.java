package ninja.skyrocketing.bot.fuyao.service.bot;

import ninja.skyrocketing.bot.fuyao.pojo.bot.BotGameFishing;

import java.util.List;

/**
 * @author skyrocketing Hong
 * @date 2020-11-29 13:20:23
 */
public interface BotGameFishingService {
    List<BotGameFishing> getAllFish();
    String getFishNameById(String id);
    Long getFishValueById(String id);
}
