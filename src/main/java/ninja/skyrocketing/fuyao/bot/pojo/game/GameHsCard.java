package ninja.skyrocketing.fuyao.bot.pojo.game;

import com.baomidou.mybatisplus.annotation.TableField;
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
    private Integer id;

    @TableField(value = "`set`")
    private String set;

    @TableField(value = "`name`")
    private String name;

    private String rarity;

    private String imgurl;

    public String getRarity() {
        return switch (rarity) {
            case "FREE" -> "⚫ 免费";
            case "RARE" -> "🔵 稀有";
            case "COMMON" -> "⚪ 普通";
            case "EPIC" -> "🟣 史诗";
            case "LEGENDARY" -> "🟡 传说";
            default -> rarity;
        };
    }

    public String getSet() {
        return switch (set) {
            case "THE_BARRENS" -> "贫瘠之地的锤炼";
            default -> set;
        };
    }
}