package ninja.skyrocketing.fuyao.bot.service.group;

import ninja.skyrocketing.fuyao.bot.pojo.group.GroupFishing;
import ninja.skyrocketing.fuyao.bot.pojo.user.User;

/**
 * @author skyrocketing Hong
 * @date 2020-11-29 14:50:36
 */
public interface GroupFishingService {
    GroupFishing getGroupFishingByGroupUser(User user);
    int updateGroupFishing(GroupFishing groupFishing);
    int insertGroupFishing(GroupFishing groupFishing);
    int deleteFishing(User user);
    int deleteFishingByGroup(Long groupId);
}
