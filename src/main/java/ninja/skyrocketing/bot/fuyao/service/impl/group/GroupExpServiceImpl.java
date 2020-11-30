package ninja.skyrocketing.bot.fuyao.service.impl.group;

import ninja.skyrocketing.bot.fuyao.mapper.group.GroupExpMapper;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupExp;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupUser;
import ninja.skyrocketing.bot.fuyao.service.group.GroupExpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-28 14:50:05
 */
@Service
public class GroupExpServiceImpl implements GroupExpService {
    @Autowired
    GroupExpMapper groupExpMapper;

    @Override
    public GroupExp GetExpByGroupUser(GroupUser groupUser) {
        return groupExpMapper.selectByPrimaryKey(groupUser.getUserId(), groupUser.getGroupId());
    }

    @Override
    public int InsertExp(GroupExp groupExp) {
        return groupExpMapper.insertSelective(groupExp);
    }

    @Override
    public int UpdateExp(GroupExp groupExp) {
        return groupExpMapper.updateByPrimaryKeySelective(groupExp);
    }
}
