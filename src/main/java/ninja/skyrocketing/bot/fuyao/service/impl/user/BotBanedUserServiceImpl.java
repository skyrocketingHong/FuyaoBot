package ninja.skyrocketing.bot.fuyao.service.impl.user;

import ninja.skyrocketing.bot.fuyao.mapper.bot.BotBanedUserMapper;
import ninja.skyrocketing.bot.fuyao.pojo.bot.BotBanedUser;
import ninja.skyrocketing.bot.fuyao.service.user.BotBanedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-28 15:08:20
 */
@Service
public class BotBanedUserServiceImpl implements BotBanedUserService {
    @Autowired
    BotBanedUserMapper botBanedUserMapper;

    @Override
    public boolean IsBaned(Long id) {
        BotBanedUser botBanedUser = botBanedUserMapper.selectByPrimaryKey(id);
        return botBanedUser != null;
    }
}
