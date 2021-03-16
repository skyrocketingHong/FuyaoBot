package ninja.skyrocketing.bot.fuyao.pojo.bot;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * @author skyrocketing Hong
 * @date 2020-11-28 11:33:05
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BotQQ {
    @TableId
    private Long qqId;

    private String qqPassword;
}
