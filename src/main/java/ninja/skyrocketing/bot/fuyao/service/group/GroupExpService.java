package ninja.skyrocketing.bot.fuyao.service.group;


import ninja.skyrocketing.bot.fuyao.pojo.group.GroupExp;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupUser;

/**
 * @author skyrocketing Hong
 * @date 2020-11-28 14:49:40
 */
public interface GroupExpService {
    GroupExp getExpByGroupUser(GroupUser groupUser);
    int insertExp(GroupExp groupExp);
    int updateExp(GroupExp groupExp);
    int deleteExp(GroupUser groupUser);
    int deleteExpByGroupId(Long groupId);
}
