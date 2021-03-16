package ninja.skyrocketing.bot.fuyao.function;

import cn.hutool.http.HttpUtil;
import lombok.NoArgsConstructor;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import ninja.skyrocketing.bot.fuyao.config.MiraiBotConfig;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupMessage;
import ninja.skyrocketing.bot.fuyao.pojo.game.GameHsCard;
import ninja.skyrocketing.bot.fuyao.service.hearthstone.HsCardService;
import ninja.skyrocketing.bot.fuyao.util.FileUtil;
import ninja.skyrocketing.bot.fuyao.util.MessageUtil;
import ninja.skyrocketing.bot.fuyao.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author skyrocketing Hong
 * @date 2021-03-05 16:48:04
 */

@Component
@NoArgsConstructor
public class SmallGamesFunction {
    private static HsCardService hsCardService;
    @Autowired
    public SmallGamesFunction(HsCardService hsCardService) {
        SmallGamesFunction.hsCardService = hsCardService;
    }

    /**
     * 投骰子
     */
    public static Message rollADice(GroupMessage groupMessage) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        int randomNum = RandomUtil.randomNum(100);
        String[] dice = new String[]{"⚀", "⚁", "⚂", "⚃", "⚄", "⚅"};
        messageChainBuilder.add(dice[randomNum % 6] + " 点数为 " + (randomNum % 6 + 1));
        return messageChainBuilder.asMessageChain();
    }

    /**
     * 石头剪刀布
     */
    public static Message rockPaperScissors(GroupMessage groupMessage) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        int randomNum = RandomUtil.randomNum(100);
        String[] rockPaperScissorsIcon = new String[]{"✊", "✌", "✋"};
        String[] rockPaperScissorsText = new String[]{" 石头 ", " 剪刀 ", " 布 "};
        messageChainBuilder.add(rockPaperScissorsIcon[randomNum % 3] + " 手势为 " + rockPaperScissorsText[randomNum % 3]);
        return messageChainBuilder.asMessageChain();
    }

    /**
    * 炉石开包
    * */
    public static Message hearthStone(GroupMessage groupMessage) throws IOException {
        MessageReceipt<Contact> messageReceipt = MessageUtil.waitingMessage(groupMessage, "正在开包...");
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        //从数据库中随机取出5张卡，放在List中
        List<GameHsCard> gameHsCardList = hsCardService.selectBySetOrderByRandom("DARKMOON_FAIRE");
        //卡的图片的List，为图片拼接准备
        List<File> cardImageFileList = new ArrayList<>();
        //生成的新图片的文件名，将卡的id直接拼在一起
        StringBuilder jointCardFileName = new StringBuilder();
        messageChainBuilder.add("疯狂的暗月马戏团\n");
        //遍历5张卡
        for (GameHsCard gameHsCard : gameHsCardList) {
            jointCardFileName.append(gameHsCard.getId());
            //卡的图片的缓存位置
            File cardImageFile = new File(MiraiBotConfig.hsCachePath + FileUtil.separator + gameHsCard.getId() + ".png");
            //如果图片不存在时就下载
            if (!cardImageFile.exists()) {
                cardImageFile = HttpUtil.downloadFileFromUrl(gameHsCard.getImgurl(), cardImageFile);
            }
            cardImageFileList.add(cardImageFile);
            //生成消息
            messageChainBuilder.add(gameHsCard.getRarity() + " " + gameHsCard.getName() + "\n");
        }
        //拼接后的图片的保存位置
        File jointCardFile = new File(MiraiBotConfig.hsCachePath + FileUtil.separator + jointCardFileName + ".png");
        //图片不存在时就拼接
        if (!jointCardFile.exists()) {
            FileUtil.jointPic(cardImageFileList, jointCardFile);
        }
        messageChainBuilder.add(MessageUtil.uploadImageToGroup(groupMessage.getGroupMessageEvent().getGroup(), jointCardFile));
        messageReceipt.recall();
        return messageChainBuilder.asMessageChain();
    }
}
