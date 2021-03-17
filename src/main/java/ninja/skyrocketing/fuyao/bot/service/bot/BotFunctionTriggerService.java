package ninja.skyrocketing.fuyao.bot.service.bot;

import ninja.skyrocketing.fuyao.bot.pojo.bot.BotFunctionTrigger;

import java.util.List;

/**
 * @author skyrocketing Hong
 * @date 2020-11-28 15:23:44
 */
public interface BotFunctionTriggerService {
    List<BotFunctionTrigger> getAllTrigger();
}
