package ninja.skyrocketing.bot.fuyao.pojo.group;

import lombok.*;
import net.mamoe.mirai.message.GroupMessageEvent;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-28 028 15:15:48
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GroupMessage {
    String message;

    GroupUser groupUser = new GroupUser();

    GroupMessageEvent groupMessageEvent;

    public GroupMessage(GroupMessageEvent event) {
        this.message = event.getMessage().contentToString().replaceFirst("~|～", "");
        this.groupUser.setGroupId(event.getGroup().getId());
        this.groupUser.setUserId(event.getSender().getId());
        this.groupMessageEvent = event;
    }
}
