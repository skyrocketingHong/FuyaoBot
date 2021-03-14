package ninja.skyrocketing.bot.fuyao.service.impl.bot;

import ninja.skyrocketing.bot.fuyao.mapper.bot.BotGameFishingMapper;
import ninja.skyrocketing.bot.fuyao.pojo.bot.BotGameFishing;
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
    BotGameFishingMapper botGameFishingMapper;

    @Override
    public List<BotGameFishing> getAllFish() {
        return botGameFishingMapper.getAllFish();
    }

    @Override
    public String getFishNameById(String id) {
        return botGameFishingMapper.selectFishNameByPrimaryKey(id);
    }

    @Override
    public Long getFishValueById(String id) {
        return botGameFishingMapper.getFishValueById(id);
    }
}
