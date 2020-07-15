package ninja.skyrocketing.robot.dao;

import ninja.skyrocketing.robot.entity.datebase.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-14 014 15:42:59
 * @Version 1.0
 */

@Repository
public interface ConfigDao extends JpaRepository<Config, Integer> {
}
