package ninja.skyrocketing.bot.fuyao.pojo.user;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserCoin {
    private Long userId;

    private Long coin;

    private Date getDate;
}