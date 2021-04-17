package ninja.skyrocketing.fuyao.bot.mapper.group;

import ninja.skyrocketing.fuyao.bot.pojo.group.GroupFishing;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author skyrocketing Hong
 */

@Mapper
public interface GroupFishingMapper {
    int deleteByPrimaryKey(@Param("groupId") Long groupId, @Param("userId") Long userId);

    int insert(GroupFishing record);

    int insertSelective(GroupFishing record);

    GroupFishing selectByPrimaryKey(@Param("groupId") Long groupId, @Param("userId") Long userId);

    int updateByPrimaryKeySelective(GroupFishing record);

    int updateByPrimaryKey(GroupFishing record);

    int deleteByGroupId(@Param("groupId") Long groupId);
}