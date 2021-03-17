package ninja.skyrocketing.fuyao.bot.service.impl.user;

import ninja.skyrocketing.fuyao.bot.mapper.bot.BotBanedUserMapper;
import ninja.skyrocketing.fuyao.bot.pojo.bot.BotBanedUser;
import ninja.skyrocketing.fuyao.bot.service.user.BotBanedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author skyrocketing Hong
 * @date 2020-11-28 15:08:20
 */
@Service
public class BotBanedUserServiceImpl implements BotBanedUserService {
    private static BotBanedUserMapper botBanedUserMapper;
    @Autowired
    public BotBanedUserServiceImpl(BotBanedUserMapper botBanedUserMapper) {
        BotBanedUserServiceImpl.botBanedUserMapper = botBanedUserMapper;
    }

    @Override
    public boolean isBaned(Long id) {
        BotBanedUser botBanedUser = botBanedUserMapper.selectById(id);
        return botBanedUser != null;
    }
}
