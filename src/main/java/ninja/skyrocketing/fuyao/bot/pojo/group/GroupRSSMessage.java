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
public class GroupRSSMessage {
	@TableField
	private int id;
	
	private long groupId;
	
	private long userId;
	
	private String rssUrl;
	
	private Date addDate;
	
	private boolean enabled;
	
	private Date lastNotifiedDate;
}
