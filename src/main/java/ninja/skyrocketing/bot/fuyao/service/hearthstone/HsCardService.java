package ninja.skyrocketing.bot.fuyao.service.hearthstone;

import ninja.skyrocketing.bot.fuyao.pojo.game.GameHsCard;

import java.util.List;

/**
 * @author skyrocketing Hong
 * @date 2021-03-06 01:06:58
 */

public interface HsCardService {
    int insertACard(GameHsCard gameHsCard);

    List<GameHsCard> selectBySetOrderByRandom(String setName);
}
