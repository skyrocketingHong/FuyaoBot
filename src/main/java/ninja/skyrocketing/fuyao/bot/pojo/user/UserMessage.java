package ninja.skyrocketing.fuyao.bot.pojo.user;

import lombok.*;
import net.mamoe.mirai.event.events.FriendMessageEvent;
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
public class UserMessage {
    String message;

    User user = new User();

    GroupMessageEvent groupMessageEvent;
    
    FriendMessageEvent friendMessageEvent;

    String functionName;

    MessageChainBuilder messageChainBuilder = new MessageChainBuilder();

    public UserMessage(GroupMessageEvent event) {
        this.message = event.getMessage().contentToString().replaceFirst("[~～/]", "");
        this.user.setGroupId(event.getGroup().getId());
        this.user.setUserId(event.getSender().getId());
        this.groupMessageEvent = event;
    }
    
    public UserMessage(FriendMessageEvent event) {
        this.message = event.getMessage().contentToString().replaceFirst("[~～/]", "");
        this.user.setGroupId(0L);
        this.user.setUserId(event.getSender().getId());
        this.friendMessageEvent = event;
    }

    public MessageChain getMessageChainBuilderAsMessageChain() {
        return messageChainBuilder.asMessageChain();
    }
    
    public boolean isFriendMessage() {
        return this.user.getGroupId() == 0L;
    }
}
