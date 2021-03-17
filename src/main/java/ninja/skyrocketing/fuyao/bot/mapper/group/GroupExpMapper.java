package ninja.skyrocketing.fuyao.bot.mapper.group;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupExp;
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