package ninja.skyrocketing.bot.fuyao.service.bot;

import ninja.skyrocketing.bot.fuyao.pojo.bot.BotFunctionTrigger;

import java.util.List;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-28 028 15:23:44
 * @Version 1.0
 */
public interface BotFunctionTriggerService {
    List<BotFunctionTrigger> GetAllTrigger();
}
