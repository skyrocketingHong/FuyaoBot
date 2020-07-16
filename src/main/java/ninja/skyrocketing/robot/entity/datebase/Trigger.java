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
	private Integer id;
	
	@Column(name = "keyword")
	private String keyword;
	
	@Column(name = "implementation")
	private String implementation;
	
	@Column(name = "enable")
	private boolean enable;
	
	@Column(name = "added_date")
	private Date addedDate;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "operate")
	private String operate;
	
	@Column(name = "shown")
	private boolean shown;
	
	public Trigger() {
	}
	
	public Trigger(Integer id, String keyword, String implementation, boolean enable, Date addedDate, String name, String operate, boolean shown) {
		this.id = id;
		this.keyword = keyword;
		this.implementation = implementation;
		this.enable = enable;
		this.addedDate = addedDate;
		this.name = name;
		this.operate = operate;
		this.shown = shown;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getKeyword() {
		return keyword;
	}
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public String getImplementation() {
		return implementation;
	}
	
	public void setImplementation(String implementation) {
		this.implementation = implementation;
	}
	
	public boolean isEnable() {
		return enable;
	}
	
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	
	public String isEnableToString() {
		if (isEnable()) {
			return "启用";
		}
		return "禁用";
	}
	
	public Date getAddedDate() {
		return addedDate;
	}
	
	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getOperate() {
		return operate;
	}
	
	public void setOperate(String operate) {
		this.operate = operate;
	}
	
	public boolean isShown() {
		return shown;
	}
	
	public void setShown(boolean shown) {
		this.shown = shown;
	}
	
	@Override
	public String toString() {
		return "Trigger: \n" +
				"id=" + id +
				", Keyword='" + keyword + '\'' +
				", Implementation='" + implementation + '\'' +
				", Enable=" + enable +
				", AddedDate=" + addedDate +
				", Name='" + name + '\'' +
				", Operate='" + operate + '\'' +
				", Shown=" + shown + "\n";
	}
}
