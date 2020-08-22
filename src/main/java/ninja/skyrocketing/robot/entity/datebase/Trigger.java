package ninja.skyrocketing.robot.entity.datebase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-14 014 16:59:57
 */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Entity
@Table(name = "trigger_list")
public class Trigger {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "keyword_regex")
	private String keywordRegex;
	
	@Column(name = "implementation")
	private String implementation;
	
	@Column(name = "enable")
	private boolean enable;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "operate")
	private String operate;
	
	@Column(name = "shown")
	private boolean shown;
	
	@Column(name = "command")
	private String command;
	
	@Column(name = "admin")
	private boolean admin;
	
	@Column(name = "commandable")
	private boolean commandable;
	
	public String isEnableToString() {
		if (isEnable()) {
			return "启用";
		}
		return "禁用";
	}
	
	public boolean isCommandable() {
		return commandable;
	}
	
	public void setCommandable(boolean commandable) {
		this.commandable = commandable;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getKeywordRegex() {
		return keywordRegex;
	}
	
	public void setKeywordRegex(String keywordRegex) {
		this.keywordRegex = keywordRegex;
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
	
	public String getCommand() {
		return command;
	}
	
	public void setCommand(String command) {
		this.command = command;
	}
	
	public boolean isAdmin() {
		return admin;
	}
	
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	@Override
	public String toString() {
		return "Trigger{" +
				"id=" + id +
				", keywordRegex='" + keywordRegex + '\'' +
				", implementation='" + implementation + '\'' +
				", enable=" + enable +
				", name='" + name + '\'' +
				", operate='" + operate + '\'' +
				", shown=" + shown +
				", command='" + command + '\'' +
				", admin=" + admin +
				'}';
	}
}
