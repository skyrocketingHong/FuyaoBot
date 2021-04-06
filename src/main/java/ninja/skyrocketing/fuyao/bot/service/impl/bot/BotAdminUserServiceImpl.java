package ninja.skyrocketing.fuyao.bot.service.impl.bot;

import ninja.skyrocketing.fuyao.bot.mapper.bot.BotAdminUserMapper;
import ninja.skyrocketing.fuyao.bot.pojo.bot.BotAdminUser;
import ninja.skyrocketing.fuyao.bot.service.bot.BotAdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author skyrocketing Hong
 * @Date 2021-04-06 13:14:00
 */
@Service
public class BotAdminUserServiceImpl implements BotAdminUserService {
    private static BotAdminUserMapper botAdminUserMapper;
    @Autowired
    public BotAdminUserServiceImpl(BotAdminUserMapper botAdminUserMapper) {
        BotAdminUserServiceImpl.botAdminUserMapper = botAdminUserMapper;
    }

    /**
     * 判断用户是否为管理员
     * @param id 用户QQ号
     * @return true或false
     */
    @Override
    public boolean isAdmin(Long id) {
        BotAdminUser botAdminUser = botAdminUserMapper.selectById(id);
        return botAdminUser != null;
    }
}
