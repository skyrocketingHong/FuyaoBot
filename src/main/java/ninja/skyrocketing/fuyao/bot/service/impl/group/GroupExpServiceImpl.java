package ninja.skyrocketing.fuyao.bot.service.impl.group;

import ninja.skyrocketing.fuyao.bot.mapper.group.GroupExpMapper;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupExp;
import ninja.skyrocketing.fuyao.bot.pojo.user.User;
import ninja.skyrocketing.fuyao.bot.service.group.GroupExpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author skyrocketing Hong
 * @date 2020-11-28 14:50:05
 */
@Service
public class GroupExpServiceImpl implements GroupExpService {
    private static GroupExpMapper groupExpMapper;
    @Autowired
    public GroupExpServiceImpl(GroupExpMapper groupExpMapper) {
        GroupExpServiceImpl.groupExpMapper = groupExpMapper;
    }

    @Override
    public GroupExp getExpByGroupUser(User user) {
        return groupExpMapper.selectByPrimaryKey(user.getGroupId(), user.getUserId());
    }

    @Override
    public int insertExp(GroupExp groupExp) {
        return groupExpMapper.insertSelective(groupExp);
    }

    @Override
    public int updateExp(GroupExp groupExp) {
        return groupExpMapper.updateByPrimaryKeySelective(groupExp);
    }

    @Override
    public int deleteExp(User user) {
        return groupExpMapper.deleteByPrimaryKey(user.getGroupId(), user.getUserId());
    }

    @Override
    public int deleteExpByGroupId(Long groupId) {
        return groupExpMapper.deleteByGroupId(groupId);
    }
}
