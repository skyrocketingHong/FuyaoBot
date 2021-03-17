package ninja.skyrocketing.fuyao.bot.service.game;

import ninja.skyrocketing.fuyao.bot.pojo.game.GameHsCard;

import java.util.List;

/**
 * @author skyrocketing Hong
 * @date 2021-03-06 01:06:58
 */

public interface GameHsCardService {
    int insertACard(GameHsCard gameHsCard);

    List<GameHsCard> selectBySetOrderByRandom(String setName);
}
