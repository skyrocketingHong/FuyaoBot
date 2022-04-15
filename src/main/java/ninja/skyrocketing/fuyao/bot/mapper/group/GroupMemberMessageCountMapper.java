package ninja.skyrocketing.fuyao.bot.mapper.group;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupMemberMessageCount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

/**
 * @author skyrocketing Hong
 * @date 2022-04-15 00:55
 */

@Mapper
public interface GroupMemberMessageCountMapper extends BaseMapper<GroupMemberMessageCount> {
	@Insert("INSERT INTO group_member_message_count (group_id, user_id, last_update_time, message_count) VALUES (#{groupId,jdbcType=BIGINT}, #{userId,jdbcType=BIGINT}, #{lastUpdateTime,jdbcType=TIMESTAMP}, #{messageCount,jdbcType=INTEGER})")
	int insert(Long groupId, Long userId, Date lastUpdateTime, Integer messageCount);
	
	@Select("select * from group_member_message_count where group_id = #{groupId,jdbcType=BIGINT} and user_id = #{userId,jdbcType=BIGINT}")
	GroupMemberMessageCount selectById(Long groupId, Long userId);
	
	@Update("UPDATE group_member_message_count SET last_update_time = #{lastUpdateTime,jdbcType=TIMESTAMP}, message_count = #{messageCount,jdbcType=INTEGER} WHERE group_id = #{groupId,jdbcType=BIGINT} and user_id = #{userId,jdbcType=BIGINT}")
	int update(Long groupId, Long userId, Date lastUpdateTime, Integer messageCount);
}
