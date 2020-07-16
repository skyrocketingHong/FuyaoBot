package ninja.skyrocketing.robot.entity.datebase;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
	@Id
	@Column(name = "id")
	private Long id;
	
	@Column(name = "group_id")
	private Long groupId;
	
	@Column(name = "exp")
	private Integer exp;
	
	@Column(name = "sign_date")
	private Date signDate;
	
	@Column(name = "next_sign_date")
	private Date nextSignDate;
	
	public UserExp() {
	}
	
	public UserExp(Long id, Long groupId, Integer exp, Date signDate) {
		this.id = id;
		this.groupId = groupId;
		this.exp = exp;
		this.signDate = signDate;
		this.nextSignDate = new Date(signDate.getTime() + 6 * 60 * 60 * 1000L);
	}
	
	public UserExp(Long id, Integer exp) {
		this.id = id;
		this.exp = exp;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
		return groupId;
	}
	
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	
	public Date getNextSignDate() {
		return nextSignDate;
	}
	
	public void setNextSignDate(Date nextSignDate) {
		this.nextSignDate = nextSignDate;
	}
}
