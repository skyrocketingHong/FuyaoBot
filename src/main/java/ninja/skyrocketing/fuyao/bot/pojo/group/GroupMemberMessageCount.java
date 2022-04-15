package ninja.skyrocketing.fuyao.bot.pojo.group;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;

import java.util.Date;

/**
 * @author skyrocketing Hong
 * @date 2022-04-15 00:51
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class GroupMemberMessageCount {
	@TableField
	private Long groupId;
	
	@TableField
	private Long userId;
	
	private Date lastUpdateTime;
	
	private Integer messageCount;
}
