package ninja.skyrocketing.bot.fuyao.service.group;

import ninja.skyrocketing.bot.fuyao.pojo.group.GroupCoin;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupUser;

/**
 * @author skyrocketing Hong
 * @date 2020-11-28 21:25:54
 */
public interface GroupCoinService {
    GroupCoin getCoinByGroupUser(GroupUser groupUser);
    int insertCoin(GroupCoin groupCoin);
    int updateCoin(GroupCoin groupCoin);
    int deleteCoin(GroupUser groupUser);
    int deleteCoinByGroupId(Long groupId);
}
