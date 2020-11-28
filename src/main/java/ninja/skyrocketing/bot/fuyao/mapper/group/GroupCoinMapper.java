package ninja.skyrocketing.bot.fuyao.mapper.group;

import ninja.skyrocketing.bot.fuyao.pojo.group.GroupCoin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface GroupCoinMapper {
    int deleteByPrimaryKey(@Param("groupId") Long groupId, @Param("userId") Long userId);

    int insert(GroupCoin record);

    int insertSelective(GroupCoin record);

    GroupCoin selectByPrimaryKey(@Param("groupId") Long groupId, @Param("userId") Long userId);

    int updateByPrimaryKeySelective(GroupCoin record);

    int updateByPrimaryKey(GroupCoin record);
}