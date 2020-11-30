package ninja.skyrocketing.bot.fuyao.pojo.bot;

import lombok.*;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-28 11:33:05
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BotQQ {
    private Long qqId;

    private String qqPassword;
}
