package ninja.skyrocketing.fuyao.bot.pojo.group;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

/**
 * @author skyrocketing Hong
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class GroupExpRankName {
    @TableId
    private Long groupId;

    private Integer expOffset;

    private String rank1;

    private String rank2;

    private String rank3;

    private String rank4;

    private String rank5;

    private String rank6;

    private String rank7;
}