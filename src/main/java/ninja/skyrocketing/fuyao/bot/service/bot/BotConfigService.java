package ninja.skyrocketing.fuyao.bot.service.bot;

import ninja.skyrocketing.fuyao.bot.pojo.bot.BotConfig;

/**
 * @author skyrocketing Hong
 * @date 2020-11-28 00:29:33
 */

public interface BotConfigService {
    BotConfig getConfigByKey(String key);
    String getConfigValueByKey(String key);
}
