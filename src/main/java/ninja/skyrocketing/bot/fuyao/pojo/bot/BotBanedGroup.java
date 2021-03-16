package ninja.skyrocketing.bot.fuyao.pojo.bot;

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
public class BotBanedGroup {
    @TableId
    private Long groupId;

    private Long addUser;

    private Date addDate;
}