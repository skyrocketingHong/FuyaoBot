package ninja.skyrocketing.fuyao.bot.pojo.group;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;

import java.util.Date;

/**
 * @author skyrocketing Hong
 * @date 2022-04-12 22:34
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class GroupMessageCount {
	@TableId
	private Long groupId;
	
	private Integer messageCount;
	
	private Date lastUpdateTime;
	
	private Integer yesterdayMessageCount;
}
