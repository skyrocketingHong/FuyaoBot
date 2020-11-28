package ninja.skyrocketing.bot.fuyao.service.impl.bot;

import ninja.skyrocketing.bot.fuyao.mapper.bot.BotFunctionTriggerMapper;
import ninja.skyrocketing.bot.fuyao.pojo.bot.BotFunctionTrigger;
import ninja.skyrocketing.bot.fuyao.service.bot.BotFunctionTriggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-28 028 15:28:58
 * @Version 1.0
 */
@Service
public class BotFunctionTriggerServiceImpl implements BotFunctionTriggerService {
    @Autowired
    BotFunctionTriggerMapper botFunctionTriggerMapper;

    @Override
    public List<BotFunctionTrigger> GetAllTrigger() {
        return botFunctionTriggerMapper.selectAllFunctionTrigger();
    }
}
