package ninja.skyrocketing.robot.entity.datebase;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-14 014 16:59:57
 * @Version 1.0
 */

@Data
@Entity
@Table(name = "trigger_list")
public class Trigger {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "keyword")
	private String Keyword;
	
	@Column(name = "implementation")
	private String Implementation;
	
	@Column(name = "enable")
	private boolean Enable;
	
	@Column(name = "added_date")
	private Date AddedDate;
	
	@Column(name = "name")
	private String Name;
	
	@Column(name = "operate")
	private String Operate;
	
	@Column(name = "shown")
	private boolean Shown;
	
	public Trigger() {
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getKeyword() {
		return Keyword;
	}
	
	public void setKeyword(String keyword) {
		Keyword = keyword;
	}
	
	public String getImplementation() {
		return Implementation;
	}
	
	public void setImplementation(String implementation) {
		Implementation = implementation;
	}
	
	public boolean isEnable() {
		return Enable;
	}
	
	public void setEnable(boolean enable) {
		Enable = enable;
	}
	
	public Date getAddedDate() {
		return AddedDate;
	}
	
	public void setAddedDate(Date addedDate) {
		AddedDate = addedDate;
	}
	
	public String getName() {
		return Name;
	}
	
	public void setName(String name) {
		Name = name;
	}
	
	public String getOperate() {
		return Operate;
	}
	
	public void setOperate(String operate) {
		Operate = operate;
	}
	
	public boolean isShown() {
		return Shown;
	}
	
	public void setShown(boolean shown) {
		Shown = shown;
	}
	
	@Override
	public String toString() {
		return "Trigger: \n" +
				"id=" + id +
				", Keyword='" + Keyword + '\'' +
				", Implementation='" + Implementation + '\'' +
				", Enable=" + Enable +
				", AddedDate=" + AddedDate +
				", Name='" + Name + '\'' +
				", Operate='" + Operate + '\'' +
				", Shown=" + Shown + "\n";
	}
}
