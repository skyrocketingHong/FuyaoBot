package ninja.skyrocketing.fuyao.bot.mapper.group;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupRSSMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GroupRSSMessageMapper extends BaseMapper<GroupRSSMessage> {
	@Select("select * from group_rss_message")
	List<GroupRSSMessage> getAllGroupRSSMessage();
	
	@Select("select distinct rss_url from group_rss_message where enabled is true")
	List<String> getAllRSSUrl();
}
