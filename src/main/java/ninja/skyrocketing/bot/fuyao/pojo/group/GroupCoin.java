package ninja.skyrocketing.bot.fuyao.pojo.group;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
public class GroupCoin extends GroupKey {
    private Long coin;

    private Date getDate;

    public GroupCoin(Long groupId, Long userId, Long coin, Date getDate) {
        super(groupId, userId);
        this.coin = coin;
        this.getDate = getDate;
    }
}