package ninja.skyrocketing.fuyao.bot.service.impl.group;

import ninja.skyrocketing.fuyao.bot.mapper.group.GroupFishingMapper;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupFishing;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupUser;
import ninja.skyrocketing.fuyao.bot.service.group.GroupFishingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author skyrocketing Hong
 * @date 2020-11-29 14:53:45
 */
@Service
public class GroupFishingServiceImpl implements GroupFishingService {
    private static GroupFishingMapper groupFishingMapper;
    @Autowired
    public GroupFishingServiceImpl(GroupFishingMapper groupFishingMapper) {
        GroupFishingServiceImpl.groupFishingMapper = groupFishingMapper;
    }

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
