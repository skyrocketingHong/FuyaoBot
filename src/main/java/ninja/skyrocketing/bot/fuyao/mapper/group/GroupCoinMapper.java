package ninja.skyrocketing.bot.fuyao.mapper.group;

import ninja.skyrocketing.bot.fuyao.pojo.group.GroupCoin;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupKey;

public interface GroupCoinMapper {
    int deleteByPrimaryKey(GroupKey key);

    int insert(GroupCoin record);

    int insertSelective(GroupCoin record);

    GroupCoin selectByPrimaryKey(GroupKey key);

    int updateByPrimaryKeySelective(GroupCoin record);

    int updateByPrimaryKey(GroupCoin record);
}