package ninja.skyrocketing.fuyao.bot.config;

import lombok.Getter;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.MessageReceipt;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupMessageInfo;

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
	public void recallAndDeleteByGroupMessageInfo(GroupMessageInfo groupMessageInfo) {
		GroupSentMessageReceipt.get(groupMessageInfo).recall();
		GroupSentMessageReceipt.remove(groupMessageInfo);
		TriggerGroupMessageInfoMap.remove(groupMessageInfo);
	}
}
