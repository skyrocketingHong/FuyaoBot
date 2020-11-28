package ninja.skyrocketing.bot.fuyao.service.group;

import ninja.skyrocketing.bot.fuyao.pojo.group.GroupCoin;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupExp;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupUser;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-28 028 21:25:54
 * @Version 1.0
 */
public interface GroupCoinService {
    GroupCoin GetCoinByGroupUser(GroupUser groupUser);
    int InsertCoin(GroupCoin groupCoin);
    int UpdateCoin(GroupCoin groupCoin);
}
