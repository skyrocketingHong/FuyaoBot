package ninja.skyrocketing.fuyao.bot.service.impl.group;

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
	 * @return boolean 是否插入成功
	 */
	@Override
	public int addOneMessageCountById(Long groupId) {
		int messageCount;
		GroupMessageCount groupMessageCount = groupMessageCountMapper.selectById(groupId);
		if(groupMessageCount == null) {
			groupMessageCountMapper.insert(
					GroupMessageCount.builder()
							.groupId(groupId)
							.messageCount(1)
							.lastUpdateTime(new Date(System.currentTimeMillis()))
							.build()
			);
			return 1;
		} else {
			messageCount = groupMessageCount.getMessageCount();
			++messageCount;
			return groupMessageCountMapper.updateById(
					GroupMessageCount.builder()
							.groupId(groupId)
							.messageCount(messageCount)
							.lastUpdateTime(new Date(System.currentTimeMillis()))
							.build()
			);
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
		return groupMessageCountMapper.selectLastDayGroupMessageCountListByCount(count);
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
			groupMessageCount.setLastUpdateTime(new Date(System.currentTimeMillis()));
			update += groupMessageCountMapper.updateById(groupMessageCount);
		}
		return update;
	}
}
