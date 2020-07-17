package ninja.skyrocketing.robot.entity.datebase;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-14 014 17:03:52
 * @Version 1.0
 */

@Data
@Entity
@Table(name = "user_exp")
public class UserExp {
	@EmbeddedId
	private UserExpIds userExpIds;
	
	@Column(name = "exp")
	private Integer exp;
	
	@Column(name = "sign_date")
	private Date signDate;
	
	@Column(name = "next_sign_date")
	private Date nextSignDate;
	
	public UserExp() {
	}
	
	public UserExp(Long id, Long groupId, Integer exp, Date signDate) {
		this.userExpIds = new UserExpIds(id, groupId);
		this.exp = exp;
		this.signDate = signDate;
		this.nextSignDate = new Date(signDate.getTime() + 6 * 60 * 60 * 1000L);
		this.userExpIds = new UserExpIds(id, groupId);
	}
	
	public Long getId() {
		return userExpIds.getUserId();
	}
	
	public void setId(Long id) {
		this.userExpIds.setUserId(id);
	}
	
	public Integer getExp() {
		return exp;
	}
	
	public void setExp(Integer exp) {
		this.exp = exp;
	}
	
	public Date getSignDate() {
		return signDate;
	}
	
	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}
	
	public Long getGroupId() {
		return userExpIds.getGroupId();
	}
	
	public void setGroupId(Long groupId) {
		this.userExpIds.setGroupId(groupId);
	}
	
	public Date getNextSignDate() {
		return nextSignDate;
	}
	
	public void setNextSignDate(Date nextSignDate) {
		this.nextSignDate = nextSignDate;
	}
	
	public UserExpIds getUserExpIds() {
		return this.userExpIds;
	}
}
