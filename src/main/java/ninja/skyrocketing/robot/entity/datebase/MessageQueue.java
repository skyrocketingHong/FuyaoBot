package ninja.skyrocketing.robot.entity.datebase;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-14 014 16:33:07
 * @Version 1.0
 */

@Data
@Entity
@Table(name = "message_queue")
public class MessageQueue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long GlobalId;
	
	@Column(name = "internal_id")
	private Long InternalId;
	
	@Column(name = "user_id")
	private Long UserId;
	
	@Column(name = "group_id")
	private Long GroupId;
	
	@Column(name = "message")
	private String Message;
	
	public MessageQueue() {
	}
	
	public Long getGlobalId() {
		return GlobalId;
	}
	
	public void setGlobalId(Long globalId) {
		GlobalId = globalId;
	}
	
	public Long getInternalId() {
		return InternalId;
	}
	
	public void setInternalId(Long internalId) {
		InternalId = internalId;
	}
	
	public Long getUserId() {
		return UserId;
	}
	
	public void setUserId(Long userId) {
		UserId = userId;
	}
	
	public Long getGroupId() {
		return GroupId;
	}
	
	public void setGroupId(Long groupId) {
		GroupId = groupId;
	}
	
	public String getMessage() {
		return Message;
	}
	
	public void setMessage(String message) {
		Message = message;
	}
}
