package ninja.skyrocketing.bot.fuyao.mapper.group;

import ninja.skyrocketing.bot.fuyao.pojo.group.GroupExp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GroupExpMapper {
    int deleteByPrimaryKey(@Param("userId") Long userId, @Param("groupId") Long groupId);

    int insert(GroupExp record);

    int insertSelective(GroupExp record);

    GroupExp selectByPrimaryKey(@Param("userId") Long userId, @Param("groupId") Long groupId);

    int updateByPrimaryKeySelective(GroupExp record);

    int updateByPrimaryKey(GroupExp record);
}