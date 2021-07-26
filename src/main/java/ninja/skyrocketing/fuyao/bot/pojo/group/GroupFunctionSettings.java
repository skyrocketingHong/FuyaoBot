package ninja.skyrocketing.fuyao.bot.pojo.group;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class GroupFunctionSettings {
	@TableField
	private Long groupId;
	
	@TableField
	private String functionId;
	
	private boolean enabled;
	
	private String parameter;
	
	private Long userId;
	
	private Date modifiedDate;
}
