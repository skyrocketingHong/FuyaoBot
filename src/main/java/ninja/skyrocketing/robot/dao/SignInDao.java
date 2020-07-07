package ninja.skyrocketing.robot.dao;

import ninja.skyrocketing.robot.pojo.SignIn;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-07 007 15:03:25
 * @Version 1.0
 */
public interface SignInDao {
	void save(SignIn signIn);
	
	void delete(Long id);
	
	void update(SignIn signIn);
	
	int get(Long userId, Long groupId);
}
