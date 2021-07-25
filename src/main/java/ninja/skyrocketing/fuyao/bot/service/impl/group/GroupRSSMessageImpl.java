package ninja.skyrocketing.fuyao.bot.service.impl.group;

import ninja.skyrocketing.fuyao.bot.mapper.group.GroupRSSMessageMapper;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupRSSMessage;
import ninja.skyrocketing.fuyao.bot.service.group.GroupRSSMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupRSSMessageImpl implements GroupRSSMessageService {
	private static GroupRSSMessageMapper groupRSSMessageMapper;
	@Autowired
	public GroupRSSMessageImpl(GroupRSSMessageMapper groupRSSMessageMapper) {
		GroupRSSMessageImpl.groupRSSMessageMapper = groupRSSMessageMapper;
	}
	/**
	 * 获取所有GroupRSSMessage
	 * */
	@Override
	public List<GroupRSSMessage> getAllGroupRSSMessage() {
		return groupRSSMessageMapper.getAllGroupRSSMessage();
	}
	
	/**
	 * 根据ID更新单个GroupRSSMessage的推送时间和推送链接
	 * @param groupRSSMessage
	 */
	@Override
	public int updateGroupRSSMessage(GroupRSSMessage groupRSSMessage) {
		return groupRSSMessageMapper.updateById(groupRSSMessage);
	}
	
	/**
	 * 获取全部RSS URL
	 */
	@Override
	public List<String> getAllRSSUrl() {
		return groupRSSMessageMapper.getAllRSSUrl();
	}
}
