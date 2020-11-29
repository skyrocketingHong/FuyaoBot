package ninja.skyrocketing.bot.fuyao.service.group;

import ninja.skyrocketing.bot.fuyao.pojo.group.GroupFishing;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupUser;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-29 029 14:50:36
 * @Version 1.0
 */
public interface GroupFishingService {
    GroupFishing GetGroupFishingByGroupUser(GroupUser groupUser);
    int UpdateGroupFishing(GroupFishing groupFishing);
    int InsertGroupFishing(GroupFishing groupFishing);
}
