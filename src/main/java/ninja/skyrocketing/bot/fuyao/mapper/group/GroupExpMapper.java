package ninja.skyrocketing.bot.fuyao.mapper.group;

import ninja.skyrocketing.bot.fuyao.pojo.group.GroupExp;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupKey;

public interface GroupExpMapper {
    int deleteByPrimaryKey(GroupKey key);

    int insert(GroupExp record);

    int insertSelective(GroupExp record);

    GroupExp selectByPrimaryKey(GroupKey key);

    int updateByPrimaryKeySelective(GroupExp record);

    int updateByPrimaryKey(GroupExp record);
}