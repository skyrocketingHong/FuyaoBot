package ninja.skyrocketing.fuyao.bot.pojo.bot;

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
public class BotFunctionTrigger {
    @TableId
    private Integer id;

    private String charId;

    private String triggerName;

    private String triggerComment;

    private String keyword;

    private String implClass;

    private Boolean enabled;

    private Boolean shown;
}