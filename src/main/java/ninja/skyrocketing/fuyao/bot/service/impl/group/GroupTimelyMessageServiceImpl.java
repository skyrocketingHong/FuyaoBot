package ninja.skyrocketing.fuyao.bot.service.impl.group;

import ninja.skyrocketing.fuyao.bot.mapper.group.GroupTimelyMessageMapper;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupTimelyMessage;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupUser;
import ninja.skyrocketing.fuyao.bot.service.group.GroupTimelyMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author skyrocketing Hong
 * @date 2020-12-01 16:10:24
 */
@Service
public class GroupTimelyMessageServiceImpl implements GroupTimelyMessageService {
    private static GroupTimelyMessageMapper groupTimelyMessageMapper;
    @Autowired
    public GroupTimelyMessageServiceImpl(GroupTimelyMessageMapper groupTimelyMessageMapper) {
        GroupTimelyMessageServiceImpl.groupTimelyMessageMapper = groupTimelyMessageMapper;
    }

    @Override
    public List<GroupTimelyMessage> getAllTimelyMessage() {
        return groupTimelyMessageMapper.selectAllGroupTimelyMessage();
    }

    @Override
    public int deleteSentMessageByGroupUser(GroupUser groupUser) {
        return groupTimelyMessageMapper.deleteByPrimaryKey(groupUser.getGroupId(), groupUser.getUserId());
    }

    @Override
    public int deleteSentMessageById(Long groupId, Long userId) {
        return groupTimelyMessageMapper.deleteByPrimaryKey(groupId, userId);
    }
}
