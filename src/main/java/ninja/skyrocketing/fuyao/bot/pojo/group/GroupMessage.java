package ninja.skyrocketing.fuyao.bot.pojo.group;

import lombok.*;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;

/**
 * @author skyrocketing Hong
 * @date 2020-11-28 15:15:48
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

    String functionName;

    MessageChainBuilder messageChainBuilder = new MessageChainBuilder();

    public GroupMessage(GroupMessageEvent event) {
        this.message = event.getMessage().contentToString().replaceFirst("[~～/]", "");
        this.groupUser.setGroupId(event.getGroup().getId());
        this.groupUser.setUserId(event.getSender().getId());
        this.groupMessageEvent = event;
    }

    public MessageChain getMessageChainBuilderAsMessageChain() {
        return messageChainBuilder.asMessageChain();
    }
}
