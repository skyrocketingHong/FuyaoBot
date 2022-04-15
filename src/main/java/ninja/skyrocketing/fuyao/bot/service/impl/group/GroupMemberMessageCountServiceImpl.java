package ninja.skyrocketing.fuyao.bot.service.impl.group;

import cn.hutool.core.date.DateUtil;
import ninja.skyrocketing.fuyao.bot.mapper.group.GroupMemberMessageCountMapper;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupMemberMessageCount;
import ninja.skyrocketing.fuyao.bot.pojo.user.User;
import ninja.skyrocketing.fuyao.bot.service.group.GroupMemberMessageCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author skyrocketing Hong
 * @date 2022-04-15 00:56
 */

@Service
public class GroupMemberMessageCountServiceImpl implements GroupMemberMessageCountService {
	private static GroupMemberMessageCountMapper groupMemberMessageCountMapper;
	@Autowired
	public GroupMemberMessageCountServiceImpl (GroupMemberMessageCountMapper groupMemberMessageCountMapper) {
		GroupMemberMessageCountServiceImpl.groupMemberMessageCountMapper = groupMemberMessageCountMapper;
	}
	
	/**
	 * 添加数据
	 */
	@Override
	public int addGroupMemberMessageCount(long groupId, long userId, long timestamp) {
		//查询是否有对应数据
		GroupMemberMessageCount groupMemberMessageCount = selectGroupMemberMessageCountById(groupId, userId);
		if (groupMemberMessageCount == null) {
			return groupMemberMessageCountMapper.insert(groupId, userId, new Date(timestamp), 1);
		} else {
			//如果不是同一天，则直接置为1
			if (!DateUtil.isSameDay(new Date(timestamp), groupMemberMessageCount.getLastUpdateTime())) {
				int messageCount = 1;
				return groupMemberMessageCountMapper.update(groupId, userId, new Date(timestamp), messageCount);
			}
			//默认情况，直接+1
			int messageCount = groupMemberMessageCount.getMessageCount() + 1;
			return groupMemberMessageCountMapper.update(groupId, userId, new Date(timestamp), messageCount);
		}
	}
	
	/**
	 * 根据User查找数据
	 */
	@Override
	public GroupMemberMessageCount selectGroupMemberMessageCountByUser(User user) {
		return selectGroupMemberMessageCountById(user.getGroupId(), user.getUserId());
	}
	
	/**
	 * 根据ID查找数据
	 */
	@Override
	public GroupMemberMessageCount selectGroupMemberMessageCountById(long groupId, long userId) {
		return groupMemberMessageCountMapper.selectById(groupId, userId);
	}
}
