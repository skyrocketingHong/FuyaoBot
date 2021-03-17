package ninja.skyrocketing.fuyao.bot.service.impl.bot;

import ninja.skyrocketing.fuyao.bot.mapper.bot.BotFunctionTriggerMapper;
import ninja.skyrocketing.fuyao.bot.pojo.bot.BotFunctionTrigger;
import ninja.skyrocketing.fuyao.bot.service.bot.BotFunctionTriggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author skyrocketing Hong
 * @date 2020-11-28 15:28:58
 */
@Service
public class BotFunctionTriggerServiceImpl implements BotFunctionTriggerService {
    private static BotFunctionTriggerMapper botFunctionTriggerMapper;
    @Autowired
    public BotFunctionTriggerServiceImpl(BotFunctionTriggerMapper botFunctionTriggerMapper) {
        BotFunctionTriggerServiceImpl.botFunctionTriggerMapper = botFunctionTriggerMapper;
    }

    @Override
    public List<BotFunctionTrigger> getAllTrigger() {
        return botFunctionTriggerMapper.selectAllFunctionTrigger();
    }
}
