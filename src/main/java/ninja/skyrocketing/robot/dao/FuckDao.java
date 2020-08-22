package ninja.skyrocketing.robot.dao;

import ninja.skyrocketing.robot.entity.datebase.Fuck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-15 015 12:55:53
 */

@Repository
public interface FuckDao extends JpaRepository<Fuck, Integer> {
}
