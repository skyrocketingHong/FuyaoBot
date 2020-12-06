package ninja.skyrocketing.bot.fuyao.mapper.group;

import ninja.skyrocketing.bot.fuyao.pojo.group.GroupExp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GroupExpMapper {
    int deleteByPrimaryKey(@Param("groupId") Long groupId, @Param("userId") Long userId);

    int insert(GroupExp record);

    int insertSelective(GroupExp record);

    GroupExp selectByPrimaryKey(@Param("groupId") Long groupId, @Param("userId") Long userId);

    int updateByPrimaryKeySelective(GroupExp record);

    int updateByPrimaryKey(GroupExp record);

    int deleteByGroupId(@Param("groupId") Long groupId);
}