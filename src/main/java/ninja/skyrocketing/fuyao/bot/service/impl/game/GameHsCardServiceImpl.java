package ninja.skyrocketing.fuyao.bot.service.impl.game;

import ninja.skyrocketing.fuyao.bot.mapper.game.GameHsCardMapper;
import ninja.skyrocketing.fuyao.bot.pojo.game.GameHsCard;
import ninja.skyrocketing.fuyao.bot.service.game.GameHsCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author skyrocketing Hong
 * @date 2021-03-06 01:07:36
 */

@Service
public class GameHsCardServiceImpl implements GameHsCardService {
    private static GameHsCardMapper gameHsCardMapper;
    @Autowired
    public GameHsCardServiceImpl(GameHsCardMapper gameHsCardMapper) {
        GameHsCardServiceImpl.gameHsCardMapper = gameHsCardMapper;
    }

    @Override
    public int insertACard(GameHsCard gameHsCard) {
        return gameHsCardMapper.insert(gameHsCard);
    }

    @Override
    public List<GameHsCard> selectBySetOrderByRandom() {
        return gameHsCardMapper.selectBySetOrderByRandom();
    }
    
    @Override
    public int deleteAllCards() {
        return gameHsCardMapper.deleteAllCards();
    }
}
