package ninja.skyrocketing.fuyao.bot.service.group;

import ninja.skyrocketing.fuyao.bot.pojo.group.GroupMemberMessageCount;
import ninja.skyrocketing.fuyao.bot.pojo.user.User;

/**
 * @author skyrocketing Hong
 * @date 2022-04-15 00:55
 */
public interface GroupMemberMessageCountService {
	/**
	 * 添加数据
	 * */
	int addGroupMemberMessageCount(long groupId, long userId, long timestamp);
	
	/**
	 * 查找数据
	 * */
	GroupMemberMessageCount selectGroupMemberMessageCountByUser(User user);
	
	/**
	 * 查找数据
	 * */
	GroupMemberMessageCount selectGroupMemberMessageCountById(long groupId, long userId);
}
