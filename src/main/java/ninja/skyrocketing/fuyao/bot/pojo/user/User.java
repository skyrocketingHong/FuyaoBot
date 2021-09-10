package ninja.skyrocketing.fuyao.bot.pojo.user;

import lombok.*;

/**
 * @author skyrocketing Hong
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class User {
    private Long groupId = 0L;

    private Long userId;
}