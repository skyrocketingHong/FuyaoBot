package ninja.skyrocketing.robot.entity.datebase;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-14 014 15:40:32
 * @Version 1.0
 */

@Data
@Entity
@Table(name = "config")
public class Config implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "value")
	private String value;
	
	public Config() {
	}
	
	public Config(Integer id, String name, String value) {
		this.id = id;
		this.name = name;
		this.value = value;
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
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
