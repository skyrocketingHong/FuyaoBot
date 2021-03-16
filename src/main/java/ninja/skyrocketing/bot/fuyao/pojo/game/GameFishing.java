package ninja.skyrocketing.bot.fuyao.pojo.game;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * @author skyrocketing Hong
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GameFishing {
    @TableId
    private String fishId;

    private String fishName;

    private Double fishProbability;

    private Long fishValue;

    private Boolean isSpecial;

    private Long specialGroup;
}
