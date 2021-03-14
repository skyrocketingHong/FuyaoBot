package ninja.skyrocketing.bot.fuyao.service.impl.group;

import ninja.skyrocketing.bot.fuyao.mapper.group.GroupTimelyMessageMapper;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupTimelyMessage;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupUser;
import ninja.skyrocketing.bot.fuyao.service.group.GroupTimelyMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author skyrocketing Hong
 * @date 2020-12-01 16:10:24
 */
@Service
public class GroupTimelyMessageServiceImpl implements GroupTimelyMessageService {
    @Autowired
    GroupTimelyMessageMapper groupTimelyMessageMapper;

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
