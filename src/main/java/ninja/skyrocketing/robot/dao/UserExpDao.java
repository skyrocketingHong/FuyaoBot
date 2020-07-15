package ninja.skyrocketing.robot.dao;

import ninja.skyrocketing.robot.entity.datebase.UserExp;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-14 014 17:07:41
 * @Version 1.0
 */
public interface UserExpDao extends JpaRepository<UserExp, Integer> {
}
