package ninja.skyrocketing.robot.entity.datebase;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-17 017 09:44:20
 * @Version 1.0
 */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class UserExpIds implements Serializable {
	@Column(name = "id")
	private Long userId;
	
	@Column(name = "group_id")
	private Long groupId;
	
	public UserExpIds(Long id) {
		this.userId = id;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public Long getGroupId() {
		return groupId;
	}
	
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
}
