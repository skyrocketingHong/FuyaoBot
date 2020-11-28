package ninja.skyrocketing.bot.fuyao.pojo.bot;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BotReplyMessage {
    private String replyKey;

    private String replyValue;

    private Date addDate;
}