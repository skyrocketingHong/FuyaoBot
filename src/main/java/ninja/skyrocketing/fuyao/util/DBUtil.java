package ninja.skyrocketing.fuyao.util;

import ninja.skyrocketing.fuyao.bot.function.CoinFunction;
import ninja.skyrocketing.fuyao.bot.function.ExpFunction;
import ninja.skyrocketing.fuyao.bot.function.FishingFunction;

/**
 * @author skyrocketing Hong
 * @date 2021-02-25 19:14:41
 */
public class DBUtil {
    //清理单独用户的数据
    public static void cleanDataAfterLeave(long groupId, long userId) {
        ExpFunction.cleanExpData(groupId, userId);
        CoinFunction.cleanCoinData(groupId, userId);
        FishingFunction.cleanFishingData(groupId, userId);
    }

    //清理整个群的数据
    public static void cleanDataAfterLeave(long groupId) {
        ExpFunction.cleanExpData(groupId, 0L);
        CoinFunction.cleanCoinData(groupId, 0L);
        FishingFunction.cleanFishingData(groupId, 0L);
    }
}
