package ninja.skyrocketing.bot.fuyao.pojo.bot;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BotGameFishing {
    private String fishId;

    private String fishName;

    private Double fishProbability;

    private Long fishValue;

    private Boolean isSpecial;

    private Long specialGroup;
}
