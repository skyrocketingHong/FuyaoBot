package ninja.skyrocketing.robot.entity.datebase;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-15 015 12:54:22
 * @Version 1.0
 */

@Data
@Entity
@Table(name = "fuck")
public class Fuck {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "value")
	private String fuckWords;
	
	public Fuck() {
	}
	
	public Fuck(Integer id, String fuckWords) {
		this.id = id;
		this.fuckWords = fuckWords;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getFuckWords() {
		return fuckWords;
	}
	
	public void setFuckWords(String fuckWords) {
		this.fuckWords = fuckWords;
	}
}
