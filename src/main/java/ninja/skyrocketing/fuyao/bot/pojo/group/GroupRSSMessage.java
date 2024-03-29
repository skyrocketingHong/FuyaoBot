package ninja.skyrocketing.fuyao.bot.pojo.group;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@TableName("group_rss_message")
public class GroupRSSMessage {
	private Integer id;
	
	private Long groupId;
	
	private Long userId;
	
	private String rssUrl;
	
	private Date addDate;
	
	private boolean enabled;
	
	private String notifyKeywords;
	
	private Date lastNotifiedDate;
	
	private String lastNotifiedUrl;
}
