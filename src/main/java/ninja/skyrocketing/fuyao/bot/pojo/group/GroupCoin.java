package ninja.skyrocketing.fuyao.bot.pojo.group;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;

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