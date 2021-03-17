package ninja.skyrocketing.fuyao.bot.service.impl.game;

import ninja.skyrocketing.fuyao.bot.mapper.game.GameFishingMapper;
import ninja.skyrocketing.fuyao.bot.pojo.game.GameFishing;
import ninja.skyrocketing.fuyao.bot.service.game.GameFishingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author skyrocketing Hong
 * @date 2020-11-29 13:21:30
 */

@Service
public class GameFishingServiceImpl implements GameFishingService {
    private static GameFishingMapper gameFishingMapper;
    @Autowired
    public GameFishingServiceImpl(GameFishingMapper gameFishingMapper) {
        GameFishingServiceImpl.gameFishingMapper = gameFishingMapper;
    }

    @Override
    public List<GameFishing> getAllFish() {
        return gameFishingMapper.getAllFish();
    }

    @Override
    public String getFishNameById(String id) {
        return gameFishingMapper.selectFishNameByPrimaryKey(id);
    }

    @Override
    public Long getFishValueById(String id) {
        return gameFishingMapper.getFishValueById(id);
    }
}
