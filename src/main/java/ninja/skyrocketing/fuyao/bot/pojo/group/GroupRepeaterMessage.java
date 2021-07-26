package ninja.skyrocketing.fuyao.bot.pojo.group;

import lombok.*;

/**
 * @author skyrocketing Hong
 * @date 2021-03-03 13:12:45
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Deprecated
public class GroupRepeaterMessage {
    private String message;

    private Integer times;
}
