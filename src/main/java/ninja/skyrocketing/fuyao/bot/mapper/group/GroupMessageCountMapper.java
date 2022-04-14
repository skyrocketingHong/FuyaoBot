package ninja.skyrocketing.fuyao.bot.mapper.group;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupMessageCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author skyrocketing Hong
 * @date 2022-04-12 22:42
 */

@Mapper
public interface GroupMessageCountMapper extends BaseMapper<GroupMessageCount> {
	@Select("select group_id from group_message_count where message_count >= #{messageCount,jdbcType=INTEGER}")
	List<Long> selectGroupMessageCountListByCount(int messageCount);
	
	@Select("select * from group_message_count")
	List<GroupMessageCount> selectAll();
	
	@Select("select group_id from group_message_count where yesterday_message_count >= #{messageCount,jdbcType=INTEGER}")
	List<Long> selectYesterdayGroupMessageCountListByCount(int count);
}
