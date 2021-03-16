package ninja.skyrocketing.bot.fuyao.service.impl.group;

import ninja.skyrocketing.bot.fuyao.mapper.group.GroupExpMapper;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupExp;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupUser;
import ninja.skyrocketing.bot.fuyao.service.group.GroupExpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author skyrocketing Hong
 * @date 2020-11-28 14:50:05
 */
@Service
public class GroupExpServiceImpl implements GroupExpService {
    @Autowired
    GroupExpMapper groupExpMapper;

    @Override
    public GroupExp getExpByGroupUser(GroupUser groupUser) {
        return groupExpMapper.selectByPrimaryKey(groupUser.getGroupId(), groupUser.getUserId());
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
    public int deleteExp(GroupUser groupUser) {
        return groupExpMapper.deleteByPrimaryKey(groupUser.getGroupId(), groupUser.getUserId());
    }

    @Override
    public int deleteExpByGroupId(Long groupId) {
        return groupExpMapper.deleteByGroupId(groupId);
    }
}
