package ninja.skyrocketing.robot.entity.datebase;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-15 015 10:26:49
 */

@Data
@Entity
@Table(name = "overwatch_mode")
public class OverwatchMode {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "chn_name")
	private String chnName;
	
	@Column(name = "players")
	private String players;
	
	@Column(name = "image")
	private String image;
	
	public OverwatchMode() {
	}
	
	public OverwatchMode(Integer id, String name, String players, String image) {
		this.id = id;
		this.name = name;
		this.players = players;
		this.image = image;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getChnName() {
		return chnName;
	}
	
	public void setChnName(String chnName) {
		this.chnName = chnName;
	}
	
	public String getPlayers() {
		return players;
	}
	
	public void setPlayers(String players) {
		this.players = players;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
}
