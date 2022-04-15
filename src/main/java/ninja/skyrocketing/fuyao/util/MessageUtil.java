package ninja.skyrocketing.fuyao.util;

import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.data.GroupHonorType;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.utils.ExternalResource;
import ninja.skyrocketing.fuyao.bot.config.GlobalVariables;
import ninja.skyrocketing.fuyao.bot.pojo.bot.BotFunctionTrigger;
import ninja.skyrocketing.fuyao.bot.pojo.user.User;
import ninja.skyrocketing.fuyao.bot.pojo.user.UserMessage;
import ninja.skyrocketing.fuyao.bot.sender.group.GroupMessageSender;
import ninja.skyrocketing.fuyao.bot.service.bot.BotAdminUserService;
import ninja.skyrocketing.fuyao.bot.service.bot.BotFunctionTriggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author skyrocketing Hong
 * @date 2020-12-06 19:09:48
 */

@Component
public class MessageUtil {
    private static BotFunctionTriggerService botFunctionTriggerService;
    private static BotAdminUserService botAdminUserService;
    @Autowired
    public MessageUtil(
            BotFunctionTriggerService botFunctionTriggerService,
            BotAdminUserService botAdminUserService
    ) {
        MessageUtil.botFunctionTriggerService = botFunctionTriggerService;
        MessageUtil.botAdminUserService = botAdminUserService;
    }

    /**
     * 根据消息获取对应的实现类
     */
    public static String matchedClass(UserMessage userMessage) {
        //保存消息便于便利
        String msg = userMessage.getMessage();
        //遍历所有功能
        for (BotFunctionTrigger botFunctionTrigger : botFunctionTriggerService.getAllTrigger()) {
            //如果包含对应关键词，触发消息
            if (msg.matches(botFunctionTrigger.getKeyword())) {
                //存储功能名
                userMessage.setFunctionName(botFunctionTrigger.getTriggerName());
                //判断功能是否开启
                if (botFunctionTrigger.getEnabled()) {
                    //判断是否为管理员功能
                    if(botFunctionTrigger.getIsAdmin()) {
                        //判断用户是否为管理员
                        if (botAdminUserService.isAdmin(userMessage.getUser().getUserId())) {
                            return botFunctionTrigger.getImplClass();
                        }
                        //非管理员提醒
                        return "function.FunctionDisabledMessage.adminFunction";
                    }
                    return botFunctionTrigger.getImplClass();
                } else {
                    //禁用消息提醒
                    return "function.FunctionDisabledMessage.FunctionDisabled";
                }
            }
        }
        return null;
    }

    /**
     * 根据实现类字符串执行对应的代码
     */
    public static Message runByInvoke(String str, UserMessage userMessage) {
        try {
            return InvokeUtil.runByInvoke(str, userMessage);
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(MessageUtil.class);
            logger.error("查找运行类时错误，错误详情: " + e.getMessage());
            return null;
        }
    }

    //当群名片为空时返回昵称
    public static String nameOfMember(Member member) {
        return member.getNameCard().isEmpty() ? member.getNick() : member.getNameCard();
    }

    //根据@获取QQ号
    public static ArrayList<Long> getQQNumberFromAt(MessageChain messageChain) {
        ArrayList<Long> qqIdList = new ArrayList<>();
        for (SingleMessage singleMessage : messageChain) {
            if (singleMessage.toString().contains("mirai:at:")) {
                qqIdList.add(Long.parseLong(singleMessage.toString().split(",")[0].split(":")[2]));
            }
        }
        return qqIdList;
    }

    //获取群成员荣誉信息名称
    public static String getGroupHonorTypeName(GroupHonorType type) {
        return switch (type) {
            case TALKATIVE -> "龙王";
            case PERFORMER -> "群聊之火";
            case LEGEND -> "群聊炽焰";
            case STRONG_NEWBIE -> "冒尖小春笋";
            case EMOTION -> "快乐源泉";
            case ACTIVE -> "活跃头衔";
            case EXCLUSIVE -> "特殊头衔";
            case MANAGE -> "管理头衔";
        };
    }

    //向QQ群上传头像
    public static Image uploadAvatarImageToGroup(Group group, Member member) throws IOException {
        return uploadImageToGroup(group, FileUtil.getAvatarImageFile(member.getId()));
    }

    //向QQ群上传图片
    public static Image uploadImageToGroup(Group group, File imageFile) throws IOException {
        ExternalResource externalResource = ExternalResource.create(imageFile);
        Image image = group.uploadImage(externalResource);
        externalResource.close();
        return image;
    }


    /**
     * 统一的用户提及文案，无空格无换行
     */
    public static Message userNotify(Member member, boolean needAt) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        messageChainBuilder.add("\"" + nameOfMember(member) + "\" " + "(" + member.getId() + ")");
        if (needAt) {
            messageChainBuilder.add(" ");
            messageChainBuilder.add(new At(member.getId()));
        }
        return messageChainBuilder.asMessageChain();
    }

    /**
     * 等待时发送的消息
     * @return
     */
    public static MessageReceipt<?> waitingMessage(UserMessage userMessage, String waitingMsg) {
        MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
        if (userMessage.isFriendMessage()) {
            messageChainBuilder.add(waitingMsg);
            return userMessage.getFriendMessageEvent().getFriend().sendMessage(messageChainBuilder.asMessageChain());
        }
        messageChainBuilder.add(userNotify(userMessage.getGroupMessageEvent().getSender(), true));
        messageChainBuilder.add("\n");
        messageChainBuilder.add(waitingMsg);
        return userMessage.getGroupMessageEvent().getGroup().sendMessage(messageChainBuilder.asMessageChain());
    }

    /**
    * 将Message toString 后去除 source
    * */
    public static String removeSource(Message message) {
        return message.toString().replaceFirst("\\[mirai:source:\\[-?\\d+],\\[-?\\d+]]","");
    }

    /**
     * 从Message中提取消息ID
     * */
    public static int getMessageIDInGroup(Message message) {
        return Integer.parseInt(message.toString().replaceAll("\n|\\n", "").replaceAll("\\[mirai:source:\\[","").replaceAll("],\\[-?\\d+]].*", ""));
    }

    /**
     * 判断消息是否相等
     * */
    public static boolean isSame(String a, String b, String c) {
        return a.equals(b) && a.equals(c);
    }

    /**
     * 防止滥用
     * */
    public static boolean preventingAbuse(long timestamp, User user, GroupMessageEvent event) {
        //当触发用户在防止滥用的Map中时，不发送消息
        if (GlobalVariables.getGlobalVariables().getGroupUserTriggerDelay().containsKey(user)) {
            //如果该用户已被提醒过，则不执行任何操作
            if (GlobalVariables.getGlobalVariables().getUserTriggerDelayNotified().contains(user)) {
                return true;
            }
            //计算冷却时间
            long coolDownTime = (timestamp - GlobalVariables.getGlobalVariables().getGroupUserTriggerDelay().get(user)) % 10;
            if (coolDownTime <= 0) {
                return false;
            }
            //生成回复消息
            MessageChainBuilder messageChainBuilder = new MessageChainBuilder();
            messageChainBuilder.add(MessageUtil.userNotify(event.getSender(), true));
            messageChainBuilder.add("\n你的冷却时间尚未结束，请等待 " + coolDownTime + "s 后再操作");
            messageChainBuilder.add("\n(提醒消息将在冷却时间结束后撤回)");
            //发送消息，并在冷却时间内撤回
            GlobalVariables.getGlobalVariables().getUserTriggerDelayNotified().add(user);
            GroupMessageSender.sendMessageByGroupId(messageChainBuilder, event.getGroup(), coolDownTime * 1000);
            return true;
        }
        return false;
    }

    /**
     * 根据数字获取对应的emoji
     * */
    public static String getEmojiNumber(int num) {
        return getEmojiNumber(String.valueOf(num));
    }
    public static String getEmojiNumber(String num) {
        if (!num.matches("^[0-9]+$")) {
            return null;
        }
        if (Objects.equals(num, "10")) {
            return "🔟";
        }
        StringBuilder result = new StringBuilder();
        for (char c : num.toCharArray()) {
            switch (c) {
                case '1' -> result.append("1️⃣");
                case '2' -> result.append("2️⃣");
                case '3' -> result.append("3️⃣");
                case '4' -> result.append("4️⃣");
                case '5' -> result.append("5️⃣");
                case '6' -> result.append("6️⃣");
                case '7' -> result.append("7️⃣");
                case '8' -> result.append("8️⃣");
                case '9' -> result.append("9️⃣");
                case '0' -> result.append("0️⃣");
            }
        }
        return result.toString();
    }

    /**
     * 布尔值可视化
     * */
    public static String getBooleanEmoji(Boolean bool) {
        return getBooleanEmoji(bool.toString());
    }
    public static String getBooleanEmoji(String boolString) {
        if (boolString.equals("true")) {
            return "\uD83D\uDE4B";
        } else if (boolString.equals("false")) {
            return "\uD83D\uDE45";
        } else {
            return null;
        }
    }

    /**
     * 根据GroupMessageEvent生成对应的信息
     * */
    public static String getGroupInfo(GroupMessageEvent event) {
        return getGroupInfo(event.getGroup());
    }

    /**
     * 根据Group生成对应的信息
     * */
    public static String getGroupInfo(Group group) {
        return "\"" + group.getName() + "\" (" + group.getId() + ")";
    }

    /**
     * 根据FriendMessageEvent生成对应的信息
     * */
    public static String getFriendInfo(FriendMessageEvent event) {
        return getFriendInfo(event.getFriend());
    }

    /**
     * 根据Friend生成对应的信息
     * */
    public static String getFriendInfo(Friend friend) {
        return "\"" + friend.getNick() + "\" (" + friend.getId() + ")";
    }

    /**
     * 根据Mender生成对应的信息
     * */
    public static String getMemberInfo(Member friend) {
        return "\"" + friend.getNick() + "\" (" + friend.getId() + ")";
    }
}
