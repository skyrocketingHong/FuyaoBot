package ninja.skyrocketing.fuyao.bot.pojo.game;

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
public class GameHsCard {
    @TableId
    private String id;

    private String set;

    private String name;

    private String rarity;

    private String imgurl;

    public String getRarity() {
        return switch (rarity) {
            case "FREE" -> "免费";
            case "RARE" -> "稀有";
            case "COMMON" -> "普通";
            case "EPIC" -> "史诗";
            case "LEGENDARY" -> "传说";
            default -> "";
        };
    }
}