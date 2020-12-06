package ninja.skyrocketing.bot.fuyao.service.group;

import ninja.skyrocketing.bot.fuyao.pojo.group.GroupFishing;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupUser;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-29 14:50:36
 */
public interface GroupFishingService {
    GroupFishing GetGroupFishingByGroupUser(GroupUser groupUser);
    int UpdateGroupFishing(GroupFishing groupFishing);
    int InsertGroupFishing(GroupFishing groupFishing);
    int DeleteFishing(GroupUser groupUser);
    int DeleteFishingByGroup(Long groupId);
}
