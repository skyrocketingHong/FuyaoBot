package ninja.skyrocketing.bot.fuyao.service.impl.hearthstone;

import ninja.skyrocketing.bot.fuyao.mapper.game.GameHsCardMapper;
import ninja.skyrocketing.bot.fuyao.pojo.game.GameHsCard;
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
    GameHsCardMapper gameHsCardMapper;

    @Override
    public int insertACard(GameHsCard gameHsCard) {
        return gameHsCardMapper.insert(gameHsCard);
    }

    @Override
    public List<GameHsCard> selectBySetOrderByRandom(String setName) {
        return gameHsCardMapper.selectBySetOrderByRandom(setName);
    }
}
