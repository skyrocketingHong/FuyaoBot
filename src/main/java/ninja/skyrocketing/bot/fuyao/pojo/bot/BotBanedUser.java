package ninja.skyrocketing.bot.fuyao.pojo.bot;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BotBanedUser {
    private Long userId;

    private Long addUser;

    private Date addDate;
}