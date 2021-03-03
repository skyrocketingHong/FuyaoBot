package ninja.skyrocketing.bot.fuyao.pojo.group;

import lombok.*;

/**
 * @Author skyrocketing Hong
 * @Date 2021-03-03 13:12:45
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GroupRepeaterMessage {
    private String message;

    private Integer times;
}
