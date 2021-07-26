package ninja.skyrocketing.fuyao.bot.pojo.group;

import lombok.*;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.Message;
import ninja.skyrocketing.fuyao.util.MessageUtil;

/**
 * @author skyrocketing Hong
 * @date 2021-07-26 22:45
 */

@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class GroupMessageInfo {
	private long groupId;
	
	private int messageId;
	
	public GroupMessageInfo(Group group, Message message) {
		this.groupId = group.getId();
		this.messageId = MessageUtil.getMessageIDInGroup(message);
	}
	
	public GroupMessageInfo(GroupMessageEvent event) {
		this.groupId = event.getGroup().getId();
		this.messageId = MessageUtil.getMessageIDInGroup(event.getMessage());
	}
}
