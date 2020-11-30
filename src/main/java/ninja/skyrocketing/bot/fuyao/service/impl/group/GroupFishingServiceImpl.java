package ninja.skyrocketing.bot.fuyao.service.impl.group;

import ninja.skyrocketing.bot.fuyao.mapper.group.GroupFishingMapper;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupFishing;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupUser;
import ninja.skyrocketing.bot.fuyao.service.group.GroupFishingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-29 14:53:45
 */
@Service
public class GroupFishingServiceImpl implements GroupFishingService {
    @Autowired
    GroupFishingMapper groupFishingMapper;

    @Override
    public GroupFishing GetGroupFishingByGroupUser(GroupUser groupUser) {
        return groupFishingMapper.selectByPrimaryKey(groupUser.getGroupId(), groupUser.getUserId());
    }

    @Override
    public int UpdateGroupFishing(GroupFishing groupFishing) {
        return groupFishingMapper.updateByPrimaryKeySelective(groupFishing);
    }

    @Override
    public int InsertGroupFishing(GroupFishing groupFishing) {
        return groupFishingMapper.insertSelective(groupFishing);
    }
}
