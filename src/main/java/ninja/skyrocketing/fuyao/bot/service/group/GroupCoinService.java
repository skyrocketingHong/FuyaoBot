package ninja.skyrocketing.fuyao.bot.service.group;

import ninja.skyrocketing.fuyao.bot.pojo.group.GroupCoin;
import ninja.skyrocketing.fuyao.bot.pojo.user.User;

/**
 * @author skyrocketing Hong
 * @date 2020-11-28 21:25:54
 */
public interface GroupCoinService {
    GroupCoin getCoinByGroupUser(User user);
    int insertCoin(GroupCoin groupCoin);
    int updateCoin(GroupCoin groupCoin);
    int deleteCoin(User user);
    int deleteCoinByGroupId(Long groupId);
}
