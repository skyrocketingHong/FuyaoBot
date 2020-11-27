package ninja.skyrocketing.bot.fuyao.pojo.group;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
public class GroupExp extends GroupKey {
    private Long exp;

    private Date signInDate;

    public GroupExp(Long userId, Long groupId, Long exp, Date signInDate) {
        super(userId, groupId);
        this.exp = exp;
        this.signInDate = signInDate;
    }
}