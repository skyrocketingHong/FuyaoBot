package ninja.skyrocketing.bot.fuyao.service.impl.bot;

import ninja.skyrocketing.bot.fuyao.mapper.bot.BotBanedGroupMapper;
import ninja.skyrocketing.bot.fuyao.pojo.bot.BotBanedGroup;
import ninja.skyrocketing.bot.fuyao.service.bot.BotBanedGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-28 15:02:12
 */
@Service
public class BotBanedGroupServiceImpl implements BotBanedGroupService {
    @Autowired
    BotBanedGroupMapper botBanedGroupMapper;

    @Override
    public boolean IsBaned(Long id) {
        BotBanedGroup botBanedGroup = botBanedGroupMapper.selectByPrimaryKey(id);
        return botBanedGroup != null;
    }
}
