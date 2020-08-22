package ninja.skyrocketing.robot.dao;

import ninja.skyrocketing.robot.entity.datebase.OverwatchMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-15 015 10:29:24
 */

@Repository
public interface OverwatchModeDao extends JpaRepository<OverwatchMode, Integer> {
}
