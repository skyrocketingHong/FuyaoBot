package ninja.skyrocketing.fuyao.bot.mapper.group;

import ninja.skyrocketing.fuyao.bot.pojo.group.GroupTimelyMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author skyrocketing Hong
 */

@Mapper
public interface GroupTimelyMessageMapper {
    int deleteByPrimaryKey(@Param("groupId") Long groupId, @Param("userId") Long userId);

    int insert(GroupTimelyMessage record);

    int insertSelective(GroupTimelyMessage record);

    GroupTimelyMessage selectByPrimaryKey(@Param("groupId") Long groupId, @Param("userId") Long userId);

    int updateByPrimaryKeySelective(GroupTimelyMessage record);

    int updateByPrimaryKey(GroupTimelyMessage record);

    List<GroupTimelyMessage> selectAllGroupTimelyMessage();
}