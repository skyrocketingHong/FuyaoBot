package ninja.skyrocketing.bot.fuyao.service.group;

import ninja.skyrocketing.bot.fuyao.pojo.group.GroupCoin;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupUser;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-28 21:25:54
 */
public interface GroupCoinService {
    GroupCoin GetCoinByGroupUser(GroupUser groupUser);
    int InsertCoin(GroupCoin groupCoin);
    int UpdateCoin(GroupCoin groupCoin);
    int DeleteCoin(GroupUser groupUser);
    int DeleteCoinByGroupId(Long groupId);
}
