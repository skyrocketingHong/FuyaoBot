package ninja.skyrocketing.robot.entity.datebase;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-14 014 16:32:34
 * @Version 1.0
 */

@Data
@Entity
@Table(name = "id_user")
public class UserId {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "admin")
	private boolean admin;
	
	@Column(name = "banned")
	private boolean banned;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public boolean isAdmin() {
		return admin;
	}
	
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	public boolean isBanned() {
		return banned;
	}
	
	public void setBanned(boolean banned) {
		this.banned = banned;
	}
}
