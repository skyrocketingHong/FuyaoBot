package ninja.skyrocketing.bot.fuyao.pojo.user;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserExp {
    private Long userId;

    private Long exp;

    private Date signInDate;
}