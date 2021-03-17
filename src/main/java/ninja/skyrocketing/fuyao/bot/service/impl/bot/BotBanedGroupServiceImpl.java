package ninja.skyrocketing.fuyao.bot.service.impl.bot;

import ninja.skyrocketing.fuyao.bot.mapper.bot.BotBanedGroupMapper;
import ninja.skyrocketing.fuyao.bot.pojo.bot.BotBanedGroup;
import ninja.skyrocketing.fuyao.bot.service.bot.BotBanedGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author skyrocketing Hong
 * @date 2020-11-28 15:02:12
 */
@Service
public class BotBanedGroupServiceImpl implements BotBanedGroupService {
    private static BotBanedGroupMapper botBanedGroupMapper;
    @Autowired
    public BotBanedGroupServiceImpl(BotBanedGroupMapper botBanedGroupMapper) {
        BotBanedGroupServiceImpl.botBanedGroupMapper = botBanedGroupMapper;
    }

    @Override
    public boolean isBaned(Long id) {
        BotBanedGroup botBanedGroup = botBanedGroupMapper.selectById(id);
        return botBanedGroup != null;
    }
}
