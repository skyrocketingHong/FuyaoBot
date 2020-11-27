package ninja.skyrocketing.bot.fuyao.mapper.bot;

import ninja.skyrocketing.bot.fuyao.pojo.bot.BotBanedUser;

public interface BotBanedUserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(BotBanedUser record);

    int insertSelective(BotBanedUser record);

    BotBanedUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(BotBanedUser record);

    int updateByPrimaryKey(BotBanedUser record);
}