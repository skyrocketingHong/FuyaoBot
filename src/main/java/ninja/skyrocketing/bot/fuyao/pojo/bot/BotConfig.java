package ninja.skyrocketing.bot.fuyao.pojo.bot;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BotConfig {
    private String configName;

    private String configValue;

    private Date addDate;
}