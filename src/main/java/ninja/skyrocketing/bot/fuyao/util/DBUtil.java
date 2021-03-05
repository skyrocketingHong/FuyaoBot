package ninja.skyrocketing.bot.fuyao.util;

import ninja.skyrocketing.bot.fuyao.function.coin.Coin;
import ninja.skyrocketing.bot.fuyao.function.exp.Exp;
import ninja.skyrocketing.bot.fuyao.function.fishing.Fishing;

/**
 * @Author skyrocketing Hong
 * @Date 2021-02-25 19:14:41
 */
public class DBUtil {
    //清理单独用户的数据
    public static void CleanDataAfterLeave(long groupId, long userId) {
        Exp.CleanExpData(groupId, userId);
        Coin.CleanCoinData(groupId, userId);
        Fishing.CleanFishingData(groupId, userId);
    }

    //清理整个群的数据
    public static void CleanDataAfterLeave(long groupId) {
        Exp.CleanExpData(groupId, 0L);
        Coin.CleanCoinData(groupId, 0L);
        Fishing.CleanFishingData(groupId, 0L);
    }
}
