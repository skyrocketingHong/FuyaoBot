package ninja.skyrocketing.bot.fuyao.service.impl.bot;

import ninja.skyrocketing.bot.fuyao.mapper.bot.BotGameFishingMapper;
import ninja.skyrocketing.bot.fuyao.pojo.bot.BotGameFishing;
import ninja.skyrocketing.bot.fuyao.service.bot.BotGameFishingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-29 029 13:21:30
 * @Version 1.0
 */

@Service
public class BotGameFishingServiceImpl implements BotGameFishingService {
    @Autowired
    BotGameFishingMapper botGameFishingMapper;

    @Override
    public List<BotGameFishing> GetAllFish() {
        return botGameFishingMapper.getAllFish();
    }
}
