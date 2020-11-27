package ninja.skyrocketing.bot.fuyao.pojo.group;

import lombok.*;

@Getter
@Setter
@ToString
public class GroupKey {
    private Long groupId;

    private Long userId;

    public GroupKey(Long groupId, Long userId) {
        this.groupId = groupId;
        this.userId = userId;
    }
}