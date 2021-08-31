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
public class GroupCoin {
    @TableField
    private Long groupId;

    @TableField
    private Long userId;

    private Long coin;

    private Date getDate;

    public GroupCoin(User user, Long coin) {
        this.groupId = user.getGroupId();
        this.userId = user.getUserId();
        this.coin = coin;
    }

    public GroupCoin(User user, Long coin, Date nowDate) {
        this.groupId = user.getGroupId();
        this.userId = user.getUserId();
        this.coin = coin;
        this.getDate = nowDate;
    }

    //减金币
    public void minusCoin(Long minus) {
        this.coin -= minus;
    }

    //加金币
    public void addCoin(Long add) {
        this.coin += add;
    }

    //将对象的值直接改为下一次需要写回数据库的值
    public void nextCoin(Long offset, Date nextSignInDate) {
        this.coin += offset;
        this.getDate = nextSignInDate;
    }
}