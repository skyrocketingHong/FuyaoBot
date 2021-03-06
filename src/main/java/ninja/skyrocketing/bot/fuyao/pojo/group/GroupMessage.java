package ninja.skyrocketing.bot.fuyao.pojo.group;

import lombok.*;
import net.mamoe.mirai.event.events.GroupMessageEvent;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-28 15:15:48
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
        this.message = event.getMessage().contentToString().replaceFirst("[~～/]", "");
        this.groupUser.setGroupId(event.getGroup().getId());
        this.groupUser.setUserId(event.getSender().getId());
        this.groupMessageEvent = event;
    }
}
