package ninja.skyrocketing.bot.fuyao.util;

import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.utils.ExternalResource;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupMessage;

import java.io.File;
import java.io.IOException;
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

    //获取群成员荣誉信息名称
    public static String GetGroupHonorTypeName(String type) {
        return switch (type) {
            case "TALKATIVE" -> " \"龙王\" ";
            case "PERFORMER" -> " \"群聊之火\" ";
            case "LEGEND" -> " \"群聊炽焰\" ";
            case "STRONG_NEWBIE" -> " \"冒尖小春笋\" ";
            case "EMOTION" -> " \"快乐源泉\" ";
            case "ACTIVE" -> " \"活跃头衔\" ";
            case "EXCLUSIVE" -> " \"特殊头衔\" ";
            case "MANAGE" -> " \"管理头衔\" ";
            default -> null;
        };
    }

    //向QQ群上传头像
    public static Image UploadAvatarImageToGroup(Group group, Member member) throws IOException {
        return UploadImageToGroup(group, FileUtil.GetAvatarImageFile(member.getId()));
    }
    //向QQ群上传图片
    public static Image UploadImageToGroup(Group group, File imageFile) throws IOException {
        ExternalResource externalResource = ExternalResource.create(imageFile);
        Image image = group.uploadImage(externalResource);
        externalResource.close();
        return image;
    }


    /**
     * 统一的用户提及文案，无空格无换行
     */
    public static Message UserNotify(Member member, boolean needAt) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("\"" + NameOfMember(member) + "\" " + "(" + member.getId() + ")");
        if (needAt) {
            messageChainBuilder.add(" ");
            messageChainBuilder.add(new At(member.getId()));
        }
        return messageChainBuilder.asMessageChain();
    }

    /**
    * 等待API返回消息时的提醒，在获取到API返回的消息后会撤回
    * */
    public static MessageReceipt<Contact> WaitingForAPI(GroupMessage groupMessage) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add(UserNotify(groupMessage.getGroupMessageEvent().getSender(), true));
        messageChainBuilder.add("正在等待 API 返回数据...");
        return groupMessage.getGroupMessageEvent().getGroup().sendMessage(messageChainBuilder.asMessageChain());
    }
}
