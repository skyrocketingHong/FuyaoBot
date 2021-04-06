package ninja.skyrocketing.fuyao.bot.pojo.bot;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.util.Date;

/**
 * @author skyrocketing Hong
 * @date 2021-04-06 13:06:58
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BotAdminUser {
    @TableId
    private Long userId;

    private Long addUser;

    private Date addDate;
}
