package ninja.skyrocketing.bot.fuyao.pojo.bot;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BotBanedGroup {
    private Long groupId;

    private Long addUser;

    private Date addDate;
}