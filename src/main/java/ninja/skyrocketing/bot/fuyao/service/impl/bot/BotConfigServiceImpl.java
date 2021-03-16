package ninja.skyrocketing.bot.fuyao.service.impl.bot;

import ninja.skyrocketing.bot.fuyao.mapper.bot.BotConfigMapper;
import ninja.skyrocketing.bot.fuyao.pojo.bot.BotConfig;
import ninja.skyrocketing.bot.fuyao.service.bot.BotConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author skyrocketing Hong
 * @date 2020-11-28 00:58:18
 */

@Service
public class BotConfigServiceImpl implements BotConfigService {
    @Resource
    BotConfigMapper botConfigMapper;

    @Override
    public BotConfig getConfigByKey(String key) {
        return botConfigMapper.selectById(key);
    }

    @Override
    public String getConfigValueByKey(String key) {
        return botConfigMapper.selectById(key).getConfigValue();
    }
}
