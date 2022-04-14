package ninja.skyrocketing.fuyao.bot.service.impl.group;

import cn.hutool.core.date.DateUtil;
import ninja.skyrocketing.fuyao.bot.mapper.group.GroupMessageCountMapper;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupMessageCount;
import ninja.skyrocketing.fuyao.bot.service.group.GroupMessageCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author skyrocketing Hong
 * @date 2022-04-12 22:36
 */

@Service
public class GroupMessageCountServiceImpl implements GroupMessageCountService {
	private static GroupMessageCountMapper groupMessageCountMapper;
	@Autowired
	public GroupMessageCountServiceImpl (GroupMessageCountMapper groupMessageCountMapper) {
		GroupMessageCountServiceImpl.groupMessageCountMapper = groupMessageCountMapper;
	}
	
	/**
	 * 将对应群的消息数量+1
	 *
	 * @param groupId 群号
	 * @return int 修改/插入条数
	 */
	@Override
	public int addOneMessageCountById(long groupId) {
		int messageCount = 1;
		GroupMessageCount groupMessageCount = getGroupMessageCountById(groupId);
		if(groupMessageCount == null) {
			groupMessageCountMapper.insert(
					GroupMessageCount.builder()
							.groupId(groupId)
							.messageCount(messageCount)
							.lastUpdateTime(new Date(System.currentTimeMillis()))
							.build()
			);
			return 1;
		} else {
			//当该群的最后更新时间不是同一天时，自动将前一日的消息移至yesterday_message_count
			if (!DateUtil.isSameDay(groupMessageCount.getLastUpdateTime(), new Date())) {
				groupMessageCount.setYesterdayMessageCount(groupMessageCount.getMessageCount());
				groupMessageCount.setMessageCount(messageCount);
				groupMessageCount.setLastUpdateTime(new Date(System.currentTimeMillis()));
				return groupMessageCountMapper.updateById(groupMessageCount);
			}
			messageCount = groupMessageCount.getMessageCount() + 1;
			groupMessageCount.setMessageCount(messageCount);
			groupMessageCount.setLastUpdateTime(new Date());
			return groupMessageCountMapper.updateById(groupMessageCount);
		}
	}
	
	/**
	 * 获取大于对应消息数量值的群号list
	 *
	 * @param count 指定消息数量
	 * @return List<GroupMessageCount> 群号list
	 */
	@Override
	public List<Long> getGroupMessageCountListByCount(int count) {
		return groupMessageCountMapper.selectGroupMessageCountListByCount(count);
	}
	
	/**
	 * 获取昨日大于对应消息数量值的群号list
	 *
	 * @param count 指定消息数量
	 * @return List<GroupMessageCount> 群号list
	 */
	@Override
	public List<Long> getLastDayGroupMessageCountListByCount(int count) {
		return groupMessageCountMapper.selectYesterdayGroupMessageCountListByCount(count);
	}
	
	/**
	 * 获取全部GroupMessageCount
	 *
	 * @return List<GroupMessageCount> list
	 */
	@Override
	public List<GroupMessageCount> getAllGroupMessageCount() {
		return groupMessageCountMapper.selectAll();
	}
	
	/**
	 * 根据群号修改表
	 *
	 * @param groupMessageCountList groupMessageCountList
	 * @return Integer 插入数量
	 */
	@Override
	public int updateGroupMessageCountById(List<GroupMessageCount> groupMessageCountList) {
		int update = 0;
		for (GroupMessageCount groupMessageCount : groupMessageCountList) {
			groupMessageCount.setLastUpdateTime(new Date());
			update += groupMessageCountMapper.updateById(groupMessageCount);
		}
		return update;
	}
	
	/**
	 * 根据群号查找
	 *
	 * @param groupId id
	 * @return GroupMessageCount
	 */
	@Override
	public GroupMessageCount getGroupMessageCountById(long groupId) {
		return groupMessageCountMapper.selectById(groupId);
	}
}
