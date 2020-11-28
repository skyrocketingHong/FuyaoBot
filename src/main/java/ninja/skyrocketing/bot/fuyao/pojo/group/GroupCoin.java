package ninja.skyrocketing.bot.fuyao.pojo.group;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GroupCoin {
    private Long groupId;

    private Long userId;

    private Long coin;

    private Date getDate;

    public GroupCoin(GroupUser groupUser, Long coin) {
        this.groupId = groupUser.getGroupId();
        this.userId = groupUser.getUserId();
        this.coin = coin;
    }

    public GroupCoin(GroupUser groupUser, Long coin, Date nowDate) {
        this.groupId = groupUser.getGroupId();
        this.userId = groupUser.getUserId();
        this.coin = coin;
        this.getDate = nowDate;
    }
}