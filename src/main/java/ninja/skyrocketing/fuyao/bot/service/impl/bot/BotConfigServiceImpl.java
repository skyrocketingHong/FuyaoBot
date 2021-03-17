package ninja.skyrocketing.fuyao.bot.service.impl.bot;

import ninja.skyrocketing.fuyao.bot.mapper.bot.BotConfigMapper;
import ninja.skyrocketing.fuyao.bot.pojo.bot.BotConfig;
import ninja.skyrocketing.fuyao.bot.service.bot.BotConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author skyrocketing Hong
 * @date 2020-11-28 00:58:18
 */

@Service
public class BotConfigServiceImpl implements BotConfigService {
    private static BotConfigMapper botConfigMapper;
    @Autowired
    public BotConfigServiceImpl(BotConfigMapper botConfigMapper) {
        BotConfigServiceImpl.botConfigMapper = botConfigMapper;
    }

    @Override
    public BotConfig getConfigByKey(String key) {
        return botConfigMapper.selectById(key);
    }

    @Override
    public String getConfigValueByKey(String key) {
        return botConfigMapper.selectById(key).getConfigValue();
    }
}
