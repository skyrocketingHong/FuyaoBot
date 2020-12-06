package ninja.skyrocketing.bot.fuyao.util;

import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.SingleMessage;

import java.util.ArrayList;

/**
 * @Author skyrocketing Hong
 * @Date 2020-12-06 19:09:48
 */
public class MessageUtil {
    //当群名片为空时返回昵称
    public static String NameOfMember(Member member) {
        return member.getNameCard().isEmpty() ? member.getNick() : member.getNameCard();
    }

    //根据@获取QQ号
    public static ArrayList<Long> GetQQNumberFromAt(MessageChain messageChain) {
        ArrayList<Long> qqIdList = new ArrayList<>();
        for (SingleMessage singleMessage : messageChain) {
            if (singleMessage.toString().contains("mirai:at:")) {
                qqIdList.add(Long.parseLong(singleMessage.toString().split(",")[0].split(":")[2]));
            }
        }
        return qqIdList;
    }
}
