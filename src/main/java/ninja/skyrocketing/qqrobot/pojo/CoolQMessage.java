package ninja.skyrocketing.qqrobot.pojo;

import net.lz1998.cq.robot.CoolQ;
import net.lz1998.cq.utils.CQCode;

public class CoolQMessage {
	String msg;
	Long groupId;
	Long userId;
	CoolQ coolQ;
	
	public CoolQMessage() {
	}
	
	public CoolQMessage(String msg, Long groupId, Long userId, CoolQ coolQ) {
		this.msg = msg;
		this.groupId = groupId;
		this.userId = userId;
		this.coolQ = coolQ;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public Long getGroupId() {
		return groupId;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public CoolQ getCoolQ() {
		return coolQ;
	}
	
	public void setCoolQ(CoolQ coolQ) {
		this.coolQ = coolQ;
	}
	
	public void sendGroupMessage(String resultMessage) {
		this.getCoolQ().sendGroupMsg(this.getGroupId(), resultMessage, false);
	}
	
	public void sendGroupSelfMessage() {
		this.getCoolQ().sendGroupMsg(this.getGroupId(), this.getMsg(), false);
	}
	
	public void sendSpecificGroupMessage(Long groupId, String resultMessage) {
		this.getCoolQ().sendGroupMsg(groupId, resultMessage, false);
	}
	
	public String atSomeone() {
		return CQCode.at(this.getUserId());
	}
	
	public boolean msgMatches(String regex) {
		return this.getMsg().matches(regex);
	}
	
	@Override
	public String toString() {
		return "CoolQMessage{" +
				"msg='" + msg + '\'' +
				", groupId=" + groupId +
				", userId=" + userId +
				", coolQ=" + coolQ +
				'}';
	}
}
