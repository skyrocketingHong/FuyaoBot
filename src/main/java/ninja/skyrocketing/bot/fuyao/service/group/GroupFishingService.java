package ninja.skyrocketing.bot.fuyao.service.group;

import ninja.skyrocketing.bot.fuyao.pojo.group.GroupFishing;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupUser;

/**
 * @author skyrocketing Hong
 * @date 2020-11-29 14:50:36
 */
public interface GroupFishingService {
    GroupFishing getGroupFishingByGroupUser(GroupUser groupUser);
    int updateGroupFishing(GroupFishing groupFishing);
    int insertGroupFishing(GroupFishing groupFishing);
    int deleteFishing(GroupUser groupUser);
    int deleteFishingByGroup(Long groupId);
}
