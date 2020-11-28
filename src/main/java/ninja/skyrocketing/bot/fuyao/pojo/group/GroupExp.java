package ninja.skyrocketing.bot.fuyao.pojo.group;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GroupExp {
    private Long userId;

    private Long groupId;

    private Long exp;

    private Date signInDate;

    public GroupExp(GroupUser groupUser, long exp) {
        this.userId = groupUser.getUserId();
        this.groupId = groupUser.getGroupId();
        this.exp = exp;
    }

    public GroupExp(GroupUser groupUser, long exp, Date date) {
        this.userId = groupUser.getUserId();
        this.groupId = groupUser.getGroupId();
        this.exp = exp;
        this.signInDate = date;
    }
}