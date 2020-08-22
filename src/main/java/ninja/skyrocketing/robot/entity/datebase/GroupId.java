package ninja.skyrocketing.robot.entity.datebase;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-14 014 16:31:00
 */

@Data
@Entity
@Table(name = "id_group")
public class GroupId {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "admin")
	private boolean admin;
	
	@Column(name = "banned")
	private boolean banned;
	
	@Column(name = "flash_image")
	private boolean flashImage;
	
	public GroupId() {
	}
	
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
	
	public boolean isFlashImage() {
		return flashImage;
	}
	
	public void setFlashImage(boolean flashImage) {
		this.flashImage = flashImage;
	}
}
