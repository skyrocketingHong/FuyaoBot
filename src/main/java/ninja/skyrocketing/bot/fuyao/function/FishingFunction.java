package ninja.skyrocketing.bot.fuyao.function;

import lombok.NoArgsConstructor;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.bot.fuyao.pojo.game.GameFishing;
import ninja.skyrocketing.bot.fuyao.pojo.group.*;
import ninja.skyrocketing.bot.fuyao.service.bot.BotGameFishingService;
import ninja.skyrocketing.bot.fuyao.service.group.GroupCoinService;
import ninja.skyrocketing.bot.fuyao.service.group.GroupExpService;
import ninja.skyrocketing.bot.fuyao.service.group.GroupFishingService;
import ninja.skyrocketing.bot.fuyao.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author skyrocketing Hong
 * @date 2020-11-29 13:14:45
 */

@Component
@NoArgsConstructor
public class FishingFunction {
    private static BotGameFishingService botGameFishingService;
    private static GroupFishingService groupFishingService;
    private static GroupCoinService groupCoinService;
    private static GroupExpService groupExpService;
    @Autowired
    private FishingFunction(
            BotGameFishingService botGameFishingService,
            GroupFishingService groupFishingService,
            GroupCoinService groupCoinService,
            GroupExpService groupExpService
    ) {
        FishingFunction.botGameFishingService = botGameFishingService;
        FishingFunction.groupFishingService = groupFishingService;
        FishingFunction.groupCoinService = groupCoinService;
        FishingFunction.groupExpService = groupExpService;
    }

    //根据经验值和金币值钓鱼
    public static Message fishByExpAndCoin(GroupMessage groupMessage) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        GroupCoin groupCoin = groupCoinService.getCoinByGroupUser(groupMessage.getGroupUser());
        GroupExp groupExp = groupExpService.getExpByGroupUser(groupMessage.getGroupUser());
        if (groupCoin == null && groupExp == null) {
            messageChainBuilder.add("❌ 从未签到和领金币");
        } else {
            if (groupCoin == null) {
                messageChainBuilder.add("❌ 从未领金币");
                return messageChainBuilder.asMessageChain();
            } else if (groupExp == null) {
                messageChainBuilder.add("❌ 从未签到");
                return messageChainBuilder.asMessageChain();
            } else {
                if (groupCoin.getCoin() < 50) {
                    if (groupCoin.getCoin() < 10) {
                        messageChainBuilder.add("❌ 金币小于 10，不够一次钓鱼" + "\n" + "请先领金币");
                        return messageChainBuilder.asMessageChain();
                    }
                    messageChainBuilder.add("❌ 金币小于 50" + "\n" + "请先领金币");
                    return messageChainBuilder.asMessageChain();
                } else if (groupExp.getExp() < 15) {
                    messageChainBuilder.add("❌ 经验值小于 15" + "\n" + "请先签到");
                    return messageChainBuilder.asMessageChain();
                }
            }
            return fishAFish(groupMessage, groupCoin);
        }
        return messageChainBuilder.asMessageChain();
    }

    //直接获取一条鱼
    public static GameFishing getFish(GroupMessage groupMessage) {
        List<GameFishing> allFish = botGameFishingService.getAllFish();
        //生成随机数0~9999，共10000个
        int randomNum = RandomUtil.secureRandomNum(0, 9999);
        for (GameFishing gameFishing : allFish) {
            if (gameFishing.getIsSpecial()) {
                //排除所有鱼中的特殊群组中的鱼
                if (!gameFishing.getSpecialGroup().equals(groupMessage.getGroupUser().getGroupId())) {
                    continue;
                }
            }
            //根据随机数，取第一个随机数比概率值小的鱼
            if (10000 - randomNum < gameFishing.getFishProbability() * 100) {
                return gameFishing;
            }
        }
        return null;
    }

    //返回钓到的鱼，生成对应消息
    public static Message fishAFish(GroupMessage groupMessage, GroupCoin groupCoin) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        //直接获取数据库中对应人的鱼筐
        GroupUser groupUser = groupMessage.getGroupUser();
        GroupFishing groupFishing = groupFishingService.getGroupFishingByGroupUser(groupUser);
        //获取一条鱼
        GameFishing gameFishing = getFish(groupMessage);
        //如果为空，则直接返回没钓到
        if (gameFishing == null) {
            //扣除金币
            groupCoin.minusCoin(10L);
            int status = groupCoinService.updateCoin(groupCoin);
            if (status == 0) {
                //插入失败提示
                messageChainBuilder.add("❌ 扣除金币失败，请联系开发者查看数据库连接问题");
            } else {
                //拼接回复消息
                messageChainBuilder.add("🤔 你啥都没钓到 扣除 10 金币");
            }
            return messageChainBuilder.asMessageChain();
        }
        //判断数据库中是否有这个人的鱼筐
        if (groupFishing == null) {
            //如果没有，则直接插入
            groupFishing = new GroupFishing(groupUser, gameFishing.getFishId(), 1);
            int status = groupFishingService.insertGroupFishing(groupFishing);
            //判断插入是否成功
            if (status == 0) {
                //插入失败提示
                messageChainBuilder.add("❌ 首次钓鱼失败，请联系开发者查看数据库连接问题");
            } else {
                //扣除金币
                groupCoin.minusCoin(10L);
                int statusCost = groupCoinService.updateCoin(groupCoin);
                if (statusCost == 0) {
                    //插入失败提示
                    messageChainBuilder.add("❌ 扣除金币，请联系开发者查看数据库连接问题");
                } else {
                    //插入成功提示
                    messageChainBuilder.add("✔ 首次钓鱼成功 扣除 10 金币" + "\n" +
                            "🎣 你钓到了一条 \"" + gameFishing.getFishName() + "\"\n" +
                            "🗑 鱼筐状态 1 / 5"
                    );
                }
            }
        } else {
            //如果数据库中有这个人的鱼筐
            //先获取空鱼筐的坑位
            int slotId = groupFishing.getNullSlot();
            //根据坑位id插入新的鱼
            groupFishing.setFishBySlotId(slotId, gameFishing.getFishId());
            int status = groupFishingService.updateGroupFishing(groupFishing);
            if (status == 0) {
                //插入失败提示
                messageChainBuilder.add("❌ 钓鱼失败，请联系开发者查看数据库连接问题");
            } else {
                //扣除金币
                groupCoin.minusCoin(10L);
                int statusCost = groupCoinService.updateCoin(groupCoin);
                if (statusCost == 0) {
                    //插入失败提示
                    messageChainBuilder.add("❌ 扣除金币，请联系开发者查看数据库连接问题");
                } else {
                    //插入成功提示
                    messageChainBuilder.add("✔ 钓鱼成功 扣除 10 金币" + "\n" +
                            "🎣 你钓到了一条 \"" + gameFishing.getFishName() + "\"\n" +
                            "🗑 鱼筐状态 " + groupFishing.getSlotCount() + " / 5"
                    );
                }
            }
        }
        return messageChainBuilder.asMessageChain();
    }

    //群内鱼种查询
    public static Message fishTypeQuery(GroupMessage groupMessage) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        MessageChainBuilder normalFish = new MessageChainBuilder();
        MessageChainBuilder specialFish = new MessageChainBuilder();
        //消息模板
        normalFish.add("\uD83D\uDC1F 普通鱼种: " + "\n");
        specialFish.add("\uD83D\uDC20 限定鱼种: " + "\n");
        //群内是否有特殊鱼种类，默认为true，如果有的话就改为false
        boolean noSpecialFish = true;
        //获取所有鱼
        List<GameFishing> gameFishingList = botGameFishingService.getAllFish();
        //迭代所有鱼种类
        for (GameFishing gameFishing : gameFishingList) {
            //是否是特殊种类
            if (gameFishing.getIsSpecial()) {
                //是否是对应群
                if (gameFishing.getSpecialGroup().equals(groupMessage.getGroupUser().getGroupId())) {
                    specialFish.add(gameFishing.getFishName() + "\n" +
                            "价值 " + gameFishing.getFishValue() +
                            " 概率 " + gameFishing.getFishProbability() + "\n"
                    );
                    noSpecialFish = false;
                }
            } else {
                normalFish.add(gameFishing.getFishName() + "\n" +
                        "价值 " + gameFishing.getFishValue() +
                        " 概率 " + gameFishing.getFishProbability() + "\n"
                );
            }
        }
        if (noSpecialFish) {
            specialFish.add("无" + "\n" +"可通过 \"bot add fish 鱼名 鱼价值金币 概率\" 添加");
        }
        messageChainBuilder.add(normalFish.asMessageChain());
        messageChainBuilder.add(specialFish.asMessageChain());
        return messageChainBuilder.asMessageChain();
    }

    //鱼筐查询
    public static Message fishTubQuery(GroupMessage groupMessage) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        GroupFishing groupFishing = groupFishingService.getGroupFishingByGroupUser(groupMessage.getGroupUser());
        messageChainBuilder.add("🗑️ 鱼筐状态" + "\n");
        //判断鱼筐是否为空
        if (groupFishing == null || groupFishing.getSlotCount() == 0) {
            //为空则直接返回
            messageChainBuilder.add("你的鱼筐空空如也，快去钓鱼试试吧");
        } else {
            int count = groupFishing.getSlotCount();
            //不为空则返回对应的鱼
            for (int i = 0; i < count && count <= 5; ++i) {
                String tmpFishId = groupFishing.getFishBySlot(i + 1);
                if (tmpFishId == null) {
                    ++count;
                } else {
                    messageChainBuilder.add(
                            "Slot " + (i + 1) + " " + botGameFishingService.getFishNameById(tmpFishId) + "\n"
                    );
                }
            }
        }
        return messageChainBuilder.asMessageChain();
    }

    //卖鱼
    public static Message sellFish(GroupMessage groupMessage) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        //鱼筐坑位编号
        int slotId;
        //使用try-catch，避免后面为非数字
        try {
            slotId = Integer.parseInt(groupMessage.getMessage().replaceAll("卖鱼", ""));
        } catch (NumberFormatException numberFormatException) {
            messageChainBuilder.add("❌ 语法错误" + "\n" + "非数字");
            return messageChainBuilder.asMessageChain();
        }
        //判断slot是否在范围内
        if (slotId >= 1 && slotId <= 5) {
            GroupUser groupUser = groupMessage.getGroupUser();
            //获取当前坑位的鱼的信息
            String fishId = groupFishingService.getGroupFishingByGroupUser(groupUser).getFishBySlot(slotId);
            //如果为null，则返回无鱼
            if (fishId == null) {
                messageChainBuilder.add("❌ 当前位置里面没有鱼");
            } else {
                //获取当前要卖掉的鱼的价值
                Long fishValue = botGameFishingService.getFishValueById(fishId) / 2;
                //获取当前用户的金币数据
                GroupCoin groupCoin = groupCoinService.getCoinByGroupUser(groupUser);
                //判断金币是否为空
                if (groupCoin == null) {
                    messageChainBuilder.add("❌ 从未领金币");
                } else {
                    //获取当前用户的钓鱼数据
                    GroupFishing groupFishing = groupFishingService.getGroupFishingByGroupUser(groupUser);
                    //判断钓鱼数据是否为空
                    if (groupFishing == null) {
                        messageChainBuilder.add("❌ 从未钓鱼");
                    } else {
                        //金币数据加上卖掉的鱼的价值
                        groupCoin.addCoin(fishValue);
                        //将当前位置的鱼置空
                        groupFishing.setNullBySlotId(slotId);
                        //更新数据库数据
                        int status1 = groupCoinService.updateCoin(groupCoin);
                        int status2 = groupFishingService.updateGroupFishing(groupFishing);
                        //判断是否插入成功
                        if (status1 == 0 && status2 == 0) {
                            messageChainBuilder.add("❌ 卖鱼失败，请联系开发者查看数据库连接问题");
                        } else {
                            messageChainBuilder.add("✔ 卖鱼成功" + "\n" +
                                    "💴 你卖掉了一条 \"" + botGameFishingService.getFishNameById(fishId) + "\"\n" +
                                    "💰 获得 " + fishValue + " 金币，当前余额为 " + groupCoin.getCoin() + " 金币"
                            );
                        }
                    }
                }
                return messageChainBuilder.asMessageChain();
            }
        } else {
            messageChainBuilder.add("❌ 语法错误" + "\n" + "数字超出鱼筐大小");
            return messageChainBuilder.asMessageChain();
        }
        return messageChainBuilder.asMessageChain();
    }

    //清理钓鱼数据
    public static int cleanFishingData(Long groupId, Long userId) {
        if (userId == 0L) {
            return groupFishingService.deleteFishingByGroup(groupId);
        }
        if (groupId == 0L) {

        }
        return groupFishingService.deleteFishing(new GroupUser(groupId, userId));
    }
}
