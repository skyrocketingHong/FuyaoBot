package ninja.skyrocketing.fuyao.bot.pojo.group;

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
public class GroupUser {
    private Long groupId;

    private Long userId;
}