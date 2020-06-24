package ninja.skyrocketing.qqrobot.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "id")
public class IdList {
	private List<Long> user = new ArrayList<>();
	private List<Long> group = new ArrayList<>();
	private List<Long> admin = new ArrayList<>();
	
	public List<Long> getUser() {
		return user;
	}
	
	public void setUser(List<Long> user) {
		this.user = user;
	}
	
	public List<Long> getGroup() {
		return group;
	}
	
	public void setGroup(List<Long> group) {
		this.group = group;
	}
	
	public List<Long> getAdmin() {
		return admin;
	}
	
	public void setAdmin(List<Long> admin) {
		this.admin = admin;
	}
}
