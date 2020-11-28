package ninja.skyrocketing.bot.fuyao.mapper.bot;

import ninja.skyrocketing.bot.fuyao.pojo.bot.BotBanedUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BotBanedUserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(BotBanedUser record);

    int insertSelective(BotBanedUser record);

    BotBanedUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(BotBanedUser record);

    int updateByPrimaryKey(BotBanedUser record);
}