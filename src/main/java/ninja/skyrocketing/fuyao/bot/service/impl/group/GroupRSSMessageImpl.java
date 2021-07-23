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
	
	@Override
	public List<GroupRSSMessage> getAllGroupRSSMessage() {
		return groupRSSMessageMapper.getAllGroupRSSMessage();
	}
}
