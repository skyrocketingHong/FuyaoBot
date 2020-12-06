package ninja.skyrocketing.bot.fuyao.service.group;


import ninja.skyrocketing.bot.fuyao.pojo.group.GroupExp;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupUser;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-28 14:49:40
 */
public interface GroupExpService {
    GroupExp GetExpByGroupUser(GroupUser groupUser);
    int InsertExp(GroupExp groupExp);
    int UpdateExp(GroupExp groupExp);
    int DeleteExp(GroupUser groupUser);
    int DeleteExpByGroupId(Long groupId);
}
