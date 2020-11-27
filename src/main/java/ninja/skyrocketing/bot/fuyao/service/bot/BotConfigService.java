package ninja.skyrocketing.bot.fuyao.service.bot;

import ninja.skyrocketing.bot.fuyao.pojo.bot.BotConfig;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-28 028 00:29:33
 * @Version 1.0
 */
public interface BotConfigService {
    BotConfig GetConfigByKey(String key);
}
