package ninja.skyrocketing.bot.fuyao.function;

import cn.hutool.core.date.DateUtil;
import lombok.NoArgsConstructor;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupCoin;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupMessage;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupUser;
import ninja.skyrocketing.bot.fuyao.service.group.GroupCoinService;
import ninja.skyrocketing.bot.fuyao.util.RandomUtil;
import ninja.skyrocketing.bot.fuyao.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author skyrocketing Hong
 * @date 2020-11-28 21:18:23
 */

@Component
@NoArgsConstructor
public class CoinFunction {
    private static GroupCoinService groupCoinService;

    @Autowired
    private CoinFunction(GroupCoinService groupCoinService) {
        CoinFunction.groupCoinService = groupCoinService;
    }

    //领金币
    public static Message getCoin(GroupMessage groupMessage) {
        //创建消息实例
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        //创建群号和QQ号的实例
        GroupUser groupUser = groupMessage.getGroupUser();
        //从数据库中获取当前群中的用户的数据
        GroupCoin groupCoin = groupCoinService.getCoinByGroupUser(groupUser);
        //获取当前时间
        Date nowDate = DateUtil.date();
        //随机生成一个金币值
        Long coin = (long) (RandomUtil.randomNum(10) + 10);
        //用户不存在时，直接插入一条新的数据
        if (groupCoin == null) {
            groupCoin = new GroupCoin(groupUser, coin);
            int status = groupCoinService.insertCoin(groupCoin);
            //数据库问题，插入失败
            if (status == 0) {
                messageChainBuilder.add("❌ 领金币失败" + "\n" + "请联系开发者查看数据库是否出现问题");
                return messageChainBuilder.asMessageChain();
            }
            //签到成功
            messageChainBuilder.add("✔ 领金币成功" + "\n" +
                    "💰 领到了 " + coin + " 金币" + "\n" +
                    "下次领取时间 " + TimeUtil.dateFormatter(new Date(nowDate.getTime() + 28800000))
            );
            return messageChainBuilder.asMessageChain();
        }
        //用户存在时，再判断是否可以签到
        else {
            //获取上次领取时间
            Date lastGetDate = groupCoin.getGetDate();
            //如果上次领取时间与当前时间间隔小于8小时，则直接返回消息
            if (nowDate.getTime() - lastGetDate.getTime() <= 28800000) {
                messageChainBuilder.add("❌ 领金币失败" + "\n" +
                        "下次领取时间 " + TimeUtil.dateFormatter(new Date(lastGetDate.getTime() + 28800000))
                );
                return messageChainBuilder.asMessageChain();
            }
            //直接领金币
            else {
                groupCoin.nextCoin(coin, nowDate);
                int status = groupCoinService.updateCoin(groupCoin);
                //数据库问题，插入失败
                if (status == 0) {
                    messageChainBuilder.add("❌ 领金币失败" + "\n" + "请联系开发者查看数据库是否出现问题");
                    return messageChainBuilder.asMessageChain();
                }
            }
        }
        //领取成功
        messageChainBuilder.add("✔ 领金币成功" + "\n" +
                "💰 领到了 " + coin + " 金币" + "\n" +
                "下次领取时间 " + TimeUtil.dateFormatter(new Date(nowDate.getTime() + 28800000))
        );
        return messageChainBuilder.asMessageChain();
    }

    //金币查询
    public static Message coinQuery(GroupMessage groupMessage) {
        //创建消息实例
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        //创建群号和QQ号的实例
        GroupUser groupUser = groupMessage.getGroupUser();
        //从数据库中获取当前群中的用户的数据
        GroupCoin groupCoin = groupCoinService.getCoinByGroupUser(groupUser);
        if (groupCoin != null) {
            long exp = groupCoin.getCoin();
            messageChainBuilder.add("💰 金币个数为 " + groupCoin.getCoin());
        } else {
            messageChainBuilder.add("❌ 当前群没有领过金币");
        }
        return messageChainBuilder.asMessageChain();
    }

    //金币转移
    public static Message coinTransform(GroupMessage groupMessage) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        long transformCoin = 0L, transformId = 0L;
        //获取当前时间
        Date nowDate = DateUtil.date();
        MessageChain messageChain = groupMessage.getGroupMessageEvent().getMessage();

        //获取转移金币的数量，接受账户和转出账户
        transformCoin = Long.parseLong(messageChain.get(1).toString().replaceAll("^(~|～)金币转移\s*|\s*", ""));
        transformId = Long.parseLong(messageChain.get(2).toString().replaceAll("\\[mirai:at:|,@.+\\]", ""));

        //转移前，转出账户数据
        GroupCoin originGroupCoin = groupCoinService.getCoinByGroupUser(groupMessage.getGroupUser());
        if (originGroupCoin == null) {
            messageChainBuilder.add("❌ 当前你在本群的账户中无金币");
            return messageChainBuilder.asMessageChain();
        }
        if (transformCoin > originGroupCoin.getCoin()) {
            messageChainBuilder.add("❌ 当前你在本群的账户中余额不足");
            return messageChainBuilder.asMessageChain();
        }

        //转移前，接受账户数据
        GroupUser transformGroupUser = new GroupUser(groupMessage.getGroupUser().getGroupId(), transformId);
        GroupCoin transformGroupCoin = groupCoinService.getCoinByGroupUser(transformGroupUser);
        //如果为空，则直接插入新的数据
        if (transformGroupCoin == null) {
            transformGroupCoin = new GroupCoin(
                    transformGroupUser,
                    transformCoin,
                    new Date(nowDate.getTime() - 28800000)
            );
            //向接受账户转移
            groupCoinService.insertCoin(transformGroupCoin);
            //转移后，原账户减少对应金币
            originGroupCoin.setCoin(originGroupCoin.getCoin() - transformCoin);
            groupCoinService.updateCoin(originGroupCoin);
            messageChainBuilder.add("✔ 转移成功，他在本群的账户中有 " + transformCoin + " 金币");
        } else {
            //如果不为空，则在原金币余额中添加
            long tmpCoin = transformGroupCoin.getCoin() + transformCoin;
            transformGroupCoin.setCoin(tmpCoin + transformCoin);
            //向接受账户转移
            groupCoinService.updateCoin(transformGroupCoin);
            //转移后，原账户减少对应金币
            originGroupCoin.setCoin(originGroupCoin.getCoin() - transformCoin);
            groupCoinService.updateCoin(originGroupCoin);
            messageChainBuilder.add("✔ 转移成功，他在本群的账户中有 " + tmpCoin + " 金币");
        }
        return messageChainBuilder.asMessageChain();
    }

    //福利金币
    public static Message bonusCoin(GroupMessage groupMessage) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        GroupCoin groupCoin = groupCoinService.getCoinByGroupUser(groupMessage.getGroupUser());
        if (groupCoin == null) {
            messageChainBuilder.add("❌ 从未领金币");
        } else {
            if (groupCoin.getCoin() >= 1000) {
                messageChainBuilder.add("❌ 你金币太多了");
            } else {
                groupCoin.setCoin(groupCoin.getCoin() + 1000);
                int status = groupCoinService.updateCoin(groupCoin);
                if (status == 0) {
                    //插入失败提示
                    messageChainBuilder.add("❌ 领取福利金币失败，请联系开发者查看数据库连接问题");
                } else {
                    messageChainBuilder.add("✔ 成功领到了 1000 金币" + "\n" +
                            "快去消费吧");
                }
            }
        }
        return messageChainBuilder.asMessageChain();
    }

    //清理金币数据
    public static int cleanCoinData(Long groupId, Long userId) {
        if (userId == 0L) {
            groupCoinService.deleteCoinByGroupId(groupId);
        }
        if (groupId == 0L) {

        }
        return groupCoinService.deleteCoin(new GroupUser(groupId, userId));
    }
}
