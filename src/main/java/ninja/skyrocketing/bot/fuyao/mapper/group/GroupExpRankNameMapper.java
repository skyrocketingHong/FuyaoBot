package ninja.skyrocketing.bot.fuyao.mapper.group;

import ninja.skyrocketing.bot.fuyao.pojo.group.GroupExpRankName;

public interface GroupExpRankNameMapper {
    int deleteByPrimaryKey(Long groupId);

    int insert(GroupExpRankName record);

    int insertSelective(GroupExpRankName record);

    GroupExpRankName selectByPrimaryKey(Long groupId);

    int updateByPrimaryKeySelective(GroupExpRankName record);

    int updateByPrimaryKey(GroupExpRankName record);
}