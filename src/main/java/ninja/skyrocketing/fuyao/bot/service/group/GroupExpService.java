package ninja.skyrocketing.fuyao.bot.service.group;


import ninja.skyrocketing.fuyao.bot.pojo.group.GroupExp;
import ninja.skyrocketing.fuyao.bot.pojo.user.User;

/**
 * @author skyrocketing Hong
 * @date 2020-11-28 14:49:40
 */
public interface GroupExpService {
    GroupExp getExpByGroupUser(User user);
    int insertExp(GroupExp groupExp);
    int updateExp(GroupExp groupExp);
    int deleteExp(User user);
    int deleteExpByGroupId(Long groupId);
}
