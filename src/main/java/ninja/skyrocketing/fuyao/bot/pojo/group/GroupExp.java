package ninja.skyrocketing.fuyao.bot.pojo.group;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;
import ninja.skyrocketing.fuyao.bot.pojo.user.User;

import java.util.Date;

/**
 * @author skyrocketing Hong
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GroupExp {
    @TableField
    private Long userId;

    @TableField
    private Long groupId;

    private Long exp;

    private Date signInDate;

    public GroupExp(User user, long exp) {
        this.userId = user.getUserId();
        this.groupId = user.getGroupId();
        this.exp = exp;
    }

    public GroupExp(User user, long exp, Date date) {
        this.userId = user.getUserId();
        this.groupId = user.getGroupId();
        this.exp = exp;
        this.signInDate = date;
    }

    //加经验
    public void addExp(int add) {
        this.exp += add;
    }

    //减经验
    public void minusExp(int minus) {
        this.exp -= minus;
    }

    //将对象的值直接改为下一次需要写回数据库的值
    public void nextExp(int offset, Date nextSignInDate) {
        this.exp += offset;
        this.signInDate = nextSignInDate;
    }
}