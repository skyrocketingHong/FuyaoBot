package ninja.skyrocketing.bot.fuyao.mapper.user;

import ninja.skyrocketing.bot.fuyao.pojo.user.UserCoin;

public interface UserCoinMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(UserCoin record);

    int insertSelective(UserCoin record);

    UserCoin selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(UserCoin record);

    int updateByPrimaryKey(UserCoin record);
}