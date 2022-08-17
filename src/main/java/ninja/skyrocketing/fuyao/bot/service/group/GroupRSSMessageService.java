package ninja.skyrocketing.fuyao.bot.service.group;

import ninja.skyrocketing.fuyao.bot.pojo.group.GroupRSSMessage;

import java.util.List;

public interface GroupRSSMessageService {
	/**
	 * 获取所有GroupRSSMessage
	 * */
	List<GroupRSSMessage> getAllGroupRSSMessage();
	
	/**
	 * 根据ID更新单个GroupRSSMessage的推送时间和推送链接
	 * */
	int updateGroupRSSMessage(GroupRSSMessage groupRSSMessage);
	
	/**
	 * 获取全部已启用的RSS URL
	 * */
	List<String> getAllRSSUrl();
}
