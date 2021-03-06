package ninja.skyrocketing.bot.fuyao.service.hearthstone;

import ninja.skyrocketing.bot.fuyao.pojo.hearthstone.HsCard;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author skyrocketing Hong
 * @Date 2021-03-06 01:06:58
 */

public interface HsCardService {
    int InsertACard(HsCard hsCard);

    List<HsCard> SelectBySetOrderByRandom(String setName);
}
