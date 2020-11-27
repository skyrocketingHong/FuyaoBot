package ninja.skyrocketing.bot.fuyao.mapper.user;

import ninja.skyrocketing.bot.fuyao.pojo.user.UserExpRankName;

public interface UserExpRankNameMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(UserExpRankName record);

    int insertSelective(UserExpRankName record);

    UserExpRankName selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(UserExpRankName record);

    int updateByPrimaryKey(UserExpRankName record);
}