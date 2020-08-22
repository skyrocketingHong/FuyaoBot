package ninja.skyrocketing.robot.dao;

import ninja.skyrocketing.robot.entity.datebase.Trigger;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-14 014 17:07:08
 */
public interface TriggerDao extends JpaRepository<Trigger, Integer> {
}
