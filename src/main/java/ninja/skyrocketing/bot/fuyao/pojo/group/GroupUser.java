package ninja.skyrocketing.bot.fuyao.pojo.group;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GroupUser {
    private Long groupId;

    private Long userId;
}