package ninja.skyrocketing.robot.dao;

import ninja.skyrocketing.robot.entity.datebase.GroupId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-14 014 17:05:59
 */
public interface GroupIdDao extends JpaRepository<GroupId, Integer> {
}
