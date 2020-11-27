package ninja.skyrocketing.bot.fuyao.mapper.user;

import ninja.skyrocketing.bot.fuyao.pojo.user.UserExp;

public interface UserExpMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(UserExp record);

    int insertSelective(UserExp record);

    UserExp selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(UserExp record);

    int updateByPrimaryKey(UserExp record);
}