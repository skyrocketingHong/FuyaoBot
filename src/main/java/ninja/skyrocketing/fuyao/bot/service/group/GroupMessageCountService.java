package ninja.skyrocketing.fuyao.bot.service.group;

import ninja.skyrocketing.fuyao.bot.pojo.group.GroupMessageCount;

import java.util.List;

/**
 * @author skyrocketing Hong
 * @date 2022-04-12 22:36
 */
public interface GroupMessageCountService {
	/**
	 * 将对应群的消息数量+1
	 *
	 * @param groupId 群号
	 * @return int 插入条数
	 * */
	int addMessageCountById(long groupId, long timestamp);
	
	/**
	 * 获取大于对应消息数量值的群号list
	 *
	 * @param count 指定消息数量
	 * @return List<GroupMessageCount> 群号list
	 * */
	List<Long> getGroupMessageCountListByCount(int count);
	
	/**
	 * 获取昨日大于对应消息数量值的群号list
	 *
	 * @param count 指定消息数量
	 * @return List<GroupMessageCount> 群号list
	 * */
	List<Long> getLastDayGroupMessageCountListByCount(int count);
	
	/**
	 * 获取全部GroupMessageCount
	 *
	 * @return List<GroupMessageCount> list
	 * */
	List<GroupMessageCount> getAllGroupMessageCount();
	
	/**
	 * 根据群号批量修改表
	 *
	 * @param groupMessageCountList groupMessageCountList
	 * @return int 插入数量
	 * */
	int updateGroupMessageCountById(List<GroupMessageCount> groupMessageCountList);
	
	/**
	 * 根据群号修改表
	 *
	 * @param groupMessageCount groupMessageCount
	 * @return int 插入数量
	 * */
	int updateGroupMessageCountById(GroupMessageCount groupMessageCount);
	
	/**
	 * 根据群号查找
	 *
	 * @param groupId id
	 * @return GroupMessageCount
	 * */
	GroupMessageCount getGroupMessageCountById(long groupId);
}
