package ninja.skyrocketing.fuyao.bot.function;

import cn.hutool.http.HttpUtil;
import lombok.NoArgsConstructor;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.fuyao.bot.config.MiraiBotConfig;
import ninja.skyrocketing.fuyao.bot.pojo.game.GameHsCard;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupMessage;
import ninja.skyrocketing.fuyao.bot.service.game.GameHsCardService;
import ninja.skyrocketing.fuyao.util.FileUtil;
import ninja.skyrocketing.fuyao.util.MessageUtil;
import ninja.skyrocketing.fuyao.util.RandomUtil;
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
    private static GameHsCardService gameHsCardService;
    @Autowired
    public SmallGamesFunction(GameHsCardService gameHsCardService) {
        SmallGamesFunction.gameHsCardService = gameHsCardService;
    }

    /**
     * 投骰子
     */
    public static Message rollADice(GroupMessage groupMessage) {
        int randomNum = RandomUtil.randomNum(100);
        String[] dice = new String[]{"⚀", "⚁", "⚂", "⚃", "⚄", "⚅"};
        groupMessage.getMessageChainBuilder().add(dice[randomNum % 6] + " 点数为 " + (randomNum % 6 + 1));
        return groupMessage.getMessageChainBuilderAsMessageChain();
    }

    /**
     * 石头剪刀布
     */
    public static Message rockPaperScissors(GroupMessage groupMessage) {
        int randomNum = RandomUtil.randomNum(100);
        String[] rockPaperScissorsIcon = new String[]{"✊", "✌", "✋"};
        String[] rockPaperScissorsText = new String[]{" 石头 ", " 剪刀 ", " 布 "};
        groupMessage.getMessageChainBuilder().add(rockPaperScissorsIcon[randomNum % 3] + " 手势为 " + rockPaperScissorsText[randomNum % 3]);
        return groupMessage.getMessageChainBuilderAsMessageChain();
    }

    /**
    * 炉石开包
    * */
    public static Message hearthStone(GroupMessage groupMessage) throws IOException {
        MessageReceipt<Group> messageReceipt = MessageUtil.waitingMessage(groupMessage, "正在开包...");
        //从数据库中随机取出5张卡，放在List中
        List<GameHsCard> gameHsCardList = gameHsCardService.selectBySetOrderByRandom();
        //卡的图片的List，为图片拼接准备
        List<File> cardImageFileList = new ArrayList<>();
        //生成的新图片的文件名，将卡的id直接拼在一起
        StringBuilder jointCardFileName = new StringBuilder();
        groupMessage.getMessageChainBuilder().add("🎁 贫瘠之地的锤炼\n");
        //遍历5张卡
        for (GameHsCard gameHsCard : gameHsCardList) {
            jointCardFileName.append(gameHsCard.getId());
            //卡的图片的缓存位置
            File cardImageFile = new File(MiraiBotConfig.HS_CACHE_PATH + FileUtil.separator + gameHsCard.getId() + ".png");
            //如果图片不存在时就下载
            if (!cardImageFile.exists()) {
                cardImageFile = HttpUtil.downloadFileFromUrl(gameHsCard.getImgurl(), cardImageFile);
            }
            cardImageFileList.add(cardImageFile);
            //生成消息
            groupMessage.getMessageChainBuilder().add(gameHsCard.getRarity() + " " + gameHsCard.getName() + "\n");
        }
        //拼接后的图片的保存位置
        File jointCardFile = new File(MiraiBotConfig.HS_CACHE_PATH + FileUtil.separator + jointCardFileName + ".png");
        //图片不存在时就拼接
        if (!jointCardFile.exists()) {
            FileUtil.jointPic(cardImageFileList, jointCardFile);
        }
        groupMessage.getMessageChainBuilder().add(MessageUtil.uploadImageToGroup(groupMessage.getGroupMessageEvent().getGroup(), jointCardFile));
        messageReceipt.recall();
        return groupMessage.getMessageChainBuilderAsMessageChain();
    }
}
