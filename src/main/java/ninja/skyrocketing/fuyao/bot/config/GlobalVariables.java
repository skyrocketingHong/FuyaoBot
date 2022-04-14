package ninja.skyrocketing.fuyao.bot.config;

import lombok.Getter;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.MessageReceipt;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupMessageInfo;
import ninja.skyrocketing.fuyao.bot.pojo.user.User;
import ninja.skyrocketing.fuyao.util.FileUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author skyrocketing Hong
 * @date 2021-07-26 21:23
 */

@Getter
public class GlobalVariables {
	/**
	 * 获取GlobalVariables
	 * */
	public static GlobalVariables getGlobalVariables() {
		return MiraiBotConfig.globalVariables;
	}
	
	/**
	 * 全局jar根目录
	 * */
	private String JarPath =  FileUtil.getPath();
	
	/**
	 * 全局cache目录
	 * */
	private String CachePath = JarPath + FileUtil.separator + "cache";
	
	/**
	 * 全局log文件的File对象
	 * */
	private String LogFilePath = CachePath + FileUtil.separator + "log" + FileUtil.separator;
	
	/**
	 * 全局hs卡牌缓存目录
	 * */
	private String HearthstoneFilePath = CachePath + FileUtil.separator + "hearthstone";
	
	/**
	 * 全局群成员头像缓存目录
	 * */
	private String GroupMemberAvatarPath = CachePath + FileUtil.separator + "groupmemberavatar";
	
	/**
	 * 全局防止滥用变量
	 * */
	private Map<User, Long> GroupUserTriggerDelay = new HashMap<>();
	
	/**
	 * 全局防止滥用（已通知）变量
	 * */
	private List<User> UserTriggerDelayNotified = new ArrayList<>();
	
	/**
	 * 群消息Map（复读消息判断）
	 * */
	private Map<Long, List<String>> GroupMessageMap = new HashMap<>();
	
	/**
	 * 已复读消息Map
	 * */
	private Map<Long, String> GroupRepeatedMessage = new HashMap<>();
	
	/**
	 * 被触发后发送消息的回执
	 * */
	private Map<GroupMessageInfo, MessageReceipt<Group>> GroupSentMessageReceipt = new HashMap<>();
	
	/**
	 * 触发消息
	 * Key为GroupMessageInfo，Value为是否为触发消息的前驱
	 * */
	private Map<GroupMessageInfo, Boolean> TriggerGroupMessageInfoMap = new HashMap<>();
	
	/**
	 * 撤回消息并删除触发消息和被触发后发送消息的回执
	 * */
	public void recallAndDeleteMessageByGroupMessageInfo(GroupMessageInfo groupMessageInfo) {
		GroupSentMessageReceipt.get(groupMessageInfo).recall();
		GroupSentMessageReceipt.remove(groupMessageInfo);
		TriggerGroupMessageInfoMap.remove(groupMessageInfo);
	}
}
