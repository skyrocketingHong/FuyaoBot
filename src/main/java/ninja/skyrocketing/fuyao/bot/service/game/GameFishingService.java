package ninja.skyrocketing.fuyao.bot.service.game;

import ninja.skyrocketing.fuyao.bot.pojo.game.GameFishing;

import java.util.List;

/**
 * @author skyrocketing Hong
 * @date 2020-11-29 13:20:23
 */
public interface GameFishingService {
    List<GameFishing> getAllFish();
    String getFishNameById(String id);
    Long getFishValueById(String id);
}
