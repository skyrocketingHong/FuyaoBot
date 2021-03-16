package ninja.skyrocketing.bot.fuyao.pojo.user;

import com.baomidou.mybatisplus.annotation.TableId;
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
public class UserExp {
    @TableId
    private Long userId;

    private Long exp;

    private Date signInDate;
}