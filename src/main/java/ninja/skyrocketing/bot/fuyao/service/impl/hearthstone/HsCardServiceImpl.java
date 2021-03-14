package ninja.skyrocketing.bot.fuyao.service.impl.hearthstone;

import ninja.skyrocketing.bot.fuyao.mapper.hearthstone.HsCardMapper;
import ninja.skyrocketing.bot.fuyao.pojo.hearthstone.HsCard;
import ninja.skyrocketing.bot.fuyao.service.hearthstone.HsCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author skyrocketing Hong
 * @date 2021-03-06 01:07:36
 */

@Service
public class HsCardServiceImpl implements HsCardService {
    @Autowired
    HsCardMapper hsCardMapper;

    @Override
    public int insertACard(HsCard hsCard) {
        return hsCardMapper.insert(hsCard);
    }

    @Override
    public List<HsCard> selectBySetOrderByRandom(String setName) {
        return hsCardMapper.selectBySetOrderByRandom(setName);
    }
}
