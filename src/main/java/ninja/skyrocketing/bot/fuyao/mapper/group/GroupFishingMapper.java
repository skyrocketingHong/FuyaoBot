package ninja.skyrocketing.bot.fuyao.mapper.group;

import ninja.skyrocketing.bot.fuyao.pojo.group.GroupFishing;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GroupFishingMapper {
    int deleteByPrimaryKey(@Param("groupId") Long groupId, @Param("userId") Long userId);

    int insert(GroupFishing record);

    int insertSelective(GroupFishing record);

    GroupFishing selectByPrimaryKey(@Param("groupId") Long groupId, @Param("userId") Long userId);

    int updateByPrimaryKeySelective(GroupFishing record);

    int updateByPrimaryKey(GroupFishing record);
}