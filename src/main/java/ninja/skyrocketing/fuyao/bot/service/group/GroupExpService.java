package ninja.skyrocketing.fuyao.bot.service.group;


import ninja.skyrocketing.fuyao.bot.pojo.group.GroupExp;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupUser;

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
