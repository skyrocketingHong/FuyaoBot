package ninja.skyrocketing.bot.fuyao.service.impl.group;

import ninja.skyrocketing.bot.fuyao.mapper.group.GroupFishingMapper;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupFishing;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupUser;
import ninja.skyrocketing.bot.fuyao.service.group.GroupFishingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author skyrocketing Hong
 * @date 2020-11-29 14:53:45
 */
@Service
public class GroupFishingServiceImpl implements GroupFishingService {
    @Autowired
    GroupFishingMapper groupFishingMapper;

    @Override
    public GroupFishing getGroupFishingByGroupUser(GroupUser groupUser) {
        return groupFishingMapper.selectByPrimaryKey(groupUser.getGroupId(), groupUser.getUserId());
    }

    @Override
    public int updateGroupFishing(GroupFishing groupFishing) {
        return groupFishingMapper.updateByPrimaryKey(groupFishing);
    }

    @Override
    public int insertGroupFishing(GroupFishing groupFishing) {
        return groupFishingMapper.insertSelective(groupFishing);
    }

    @Override
    public int deleteFishing(GroupUser groupUser) {
        return groupFishingMapper.deleteByPrimaryKey(groupUser.getGroupId(), groupUser.getUserId());
    }

    @Override
    public int deleteFishingByGroup(Long groupId) {
        return groupFishingMapper.deleteByGroupId(groupId);
    }
}
