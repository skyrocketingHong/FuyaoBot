package ninja.skyrocketing.bot.fuyao.pojo.bot;

import lombok.*;

@Data
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BotFunctionTrigger {
    private Integer id;

    private String charId;

    private String triggerName;

    private String triggerComment;

    private String keyword;

    private String implClass;

    private Boolean enabled;

    private Boolean shown;
}