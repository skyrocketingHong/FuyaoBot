package ninja.skyrocketing.robot.dao;

import ninja.skyrocketing.robot.entity.datebase.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-14 014 17:08:07
 * @Version 1.0
 */
public interface UserIdDao extends JpaRepository<UserId, Integer> {
}
