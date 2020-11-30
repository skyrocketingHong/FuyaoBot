package ninja.skyrocketing.bot.fuyao.service.bot;

import ninja.skyrocketing.bot.fuyao.pojo.bot.BotConfig;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-28 00:29:33
 */
public interface BotConfigService {
    BotConfig GetConfigByKey(String key);
    String GetConfigValueByKey(String key);
}
