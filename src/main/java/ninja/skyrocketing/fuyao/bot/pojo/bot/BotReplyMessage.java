package ninja.skyrocketing.fuyao.bot.pojo.bot;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.util.Date;

/**
 * @author skyrocketing Hong
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BotReplyMessage {
    @TableId
    private String replyKey;

    private String replyValue;

    private Date addDate;

    public BotReplyMessage(String replyKey, String replyValue) {
        this.replyKey = replyKey;
        this.replyValue = replyValue;
    }
}