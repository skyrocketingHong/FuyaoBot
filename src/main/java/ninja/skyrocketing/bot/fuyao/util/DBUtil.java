package ninja.skyrocketing.bot.fuyao.util;

import ninja.skyrocketing.bot.fuyao.function.coin.Coin;
import ninja.skyrocketing.bot.fuyao.function.exp.Exp;
import ninja.skyrocketing.bot.fuyao.function.fishing.Fishing;

/**
 * @Author skyrocketing Hong
 * @Date 2021-02-25 19:14:41
 */
public class DBUtil {
    public static void CleanDataAfterLeave(long groupId, long userId) {
        Exp.CleanExpData(groupId, userId);
        Coin.CleanCoinData(groupId, userId);
        Fishing.CleanFishingData(groupId, userId);
    }
}
