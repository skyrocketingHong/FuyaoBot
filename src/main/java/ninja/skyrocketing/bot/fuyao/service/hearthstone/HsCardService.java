package ninja.skyrocketing.bot.fuyao.service.hearthstone;

import ninja.skyrocketing.bot.fuyao.pojo.hearthstone.HsCard;

import java.util.List;

/**
 * @author skyrocketing Hong
 * @date 2021-03-06 01:06:58
 */

public interface HsCardService {
    int insertACard(HsCard hsCard);

    List<HsCard> selectBySetOrderByRandom(String setName);
}
