package ninja.skyrocketing.robot.dao.impl;

import ninja.skyrocketing.robot.dao.SignInDao;
import ninja.skyrocketing.robot.handler.impl.BeanHandler;
import ninja.skyrocketing.robot.pojo.SignIn;
import ninja.skyrocketing.util.JdbcTemplate;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-07 007 15:04:31
 * @Version 1.0
 */
public class SignInImpl implements SignInDao {
	
	@Override
	public void save(SignIn signIn) {
		String sql = "INSERT INTO sign_in(user_id, group_id, exp) VALUES(?, ?, ?)";
		Object[] arguments = new Object[] { signIn.getUserId(), signIn.getGroupId(), signIn.getExp() };
		JdbcTemplate.update(sql, arguments);
	}
	
	@Override
	public void delete(Long id) {
	
	}
	
	@Override
	public void update(SignIn signIn) {
	
	}
	
	@Override
	public int get(Long userId, Long groupId) {
		String sql = "SELECT exp FROM sign_in WHERE user_id = ? and group_id = ?";
		return (int) JdbcTemplate.query(sql, new BeanHandler<>(SignIn.class), userId, groupId);
	}
}
