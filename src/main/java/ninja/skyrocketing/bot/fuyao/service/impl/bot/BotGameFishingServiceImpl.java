package ninja.skyrocketing.bot.fuyao.service.impl.bot;

import ninja.skyrocketing.bot.fuyao.mapper.game.GameFishingMapper;
import ninja.skyrocketing.bot.fuyao.pojo.game.GameFishing;
import ninja.skyrocketing.bot.fuyao.service.bot.BotGameFishingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author skyrocketing Hong
 * @date 2020-11-29 13:21:30
 */

@Service
public class BotGameFishingServiceImpl implements BotGameFishingService {
    @Autowired
    GameFishingMapper gameFishingMapper;

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
