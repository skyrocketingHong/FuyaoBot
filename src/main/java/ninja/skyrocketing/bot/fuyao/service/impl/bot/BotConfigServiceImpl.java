package ninja.skyrocketing.bot.fuyao.service.impl.bot;

import ninja.skyrocketing.bot.fuyao.mapper.bot.BotConfigMapper;
import ninja.skyrocketing.bot.fuyao.pojo.bot.BotConfig;
import ninja.skyrocketing.bot.fuyao.service.bot.BotConfigService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-28 028 00:58:18
 * @Version 1.0
 */
@Service
public class BotConfigServiceImpl implements BotConfigService {
    @Resource
    BotConfigMapper botConfigMapper;

    @Override
    public BotConfig GetConfigByKey(String key) {
        return botConfigMapper.selectByPrimaryKey(key);
    }

    @Override
    public String GetConfigValueByKey(String key) {
        return botConfigMapper.selectByPrimaryKey(key).getConfigValue();
    }
}
