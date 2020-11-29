package ninja.skyrocketing.bot.fuyao.function.fishing;

import lombok.NoArgsConstructor;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.bot.fuyao.pojo.bot.BotGameFishing;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupFishing;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupMessage;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupUser;
import ninja.skyrocketing.bot.fuyao.service.bot.BotGameFishingService;
import ninja.skyrocketing.bot.fuyao.service.group.GroupFishingService;
import ninja.skyrocketing.bot.fuyao.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-29 029 13:14:45
 * @Version 1.0
 */
@Component
@NoArgsConstructor
public class Fishing {
    private static BotGameFishingService botGameFishingService;
    private static GroupFishingService groupFishingService;
    @Autowired
    private Fishing(
            BotGameFishingService botGameFishingService,
            GroupFishingService groupFishingService
    ) {
        Fishing.botGameFishingService = botGameFishingService;
        Fishing.groupFishingService = groupFishingService;
    }

    //直接获取一条鱼
    public static BotGameFishing GetFish(GroupMessage groupMessage) {
        List<BotGameFishing> allFish = botGameFishingService.GetAllFish();
        //生成随机数0~9999，共10000个
        int randomNum = RandomUtil.SecureRandomNum(0, 9999);
        //使用迭代器，可以在循环中移除元素
        Iterator<BotGameFishing> allFishIterator = allFish.listIterator();
        while (allFishIterator.hasNext()) {
            BotGameFishing botGameFishing = allFishIterator.next();
            if (botGameFishing.getIsSpecial()) {
                //排除所有鱼中的特殊群组中的鱼
                if (!botGameFishing.getSpecialGroup().equals(groupMessage.getGroupUser().getGroupId())) {
                    allFishIterator.remove();
                    continue;
                }
            }
            //根据随机数，取第一个随机数比概率值小的鱼
            if (randomNum < botGameFishing.getFishProbability() * 100) {
                return botGameFishing;
            } else {
                return null;
            }
        }
        return null;
    }

    //返回钓到的鱼，生成对应消息
    public static Message FishAFish(GroupMessage groupMessage) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        //直接获取数据库中对应人的鱼筐
        GroupUser groupUser = groupMessage.getGroupUser();
        GroupFishing groupFishing = groupFishingService.GetGroupFishingByGroupUser(groupUser);
        //获取一条鱼
        BotGameFishing botGameFishing = GetFish(groupMessage);
        while (botGameFishing == null) {
            botGameFishing = GetFish(groupMessage);
        }
        //判断数据库中是否有这个人的鱼筐
        if (groupFishing == null) {
            //如果没有，则直接插入
            groupFishing = new GroupFishing(groupUser, botGameFishing.getFishId(), 1);
            int status = groupFishingService.InsertGroupFishing(groupFishing);
            //判断插入是否成功
            if (status == 0) {
                //插入失败提示
                messageChainBuilder.add("❌ 首次钓鱼失败，请联系开发者查看数据库连接问题");
            } else {
                //插入成功提示
                messageChainBuilder.add("✔ 首次钓鱼成功" + "\n" +
                        "🎣 你钓到了一条 \"" + botGameFishing.getFishName() + "\"\n" +
                        "🗑 鱼筐状态 1 / 5"
                );
            }
        } else {
            //如果数据库中有这个人的鱼筐
            //先获取空鱼筐的坑位
            int slotId = groupFishing.getNullSlot();
            //根据坑位id插入新的鱼
            groupFishing.setFishBySlotId(slotId, botGameFishing.getFishId());
            int status = groupFishingService.UpdateGroupFishing(groupFishing);
            if (status == 0) {
                //插入失败提示
                messageChainBuilder.add("❌ 钓鱼失败，请联系开发者查看数据库连接问题");
            } else {
                //插入成功提示
                messageChainBuilder.add("✔ 钓鱼成功" + "\n" +
                        "🎣 你钓到了一条 \"" + botGameFishing.getFishName() + "\"\n" +
                        "🗑 鱼筐状态 " + groupFishing.getSlotCount() + " / 5"
                );
            }
        }
        return messageChainBuilder.asMessageChain();
    }
}
