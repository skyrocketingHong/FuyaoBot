package ninja.skyrocketing.robot.dao;

import ninja.skyrocketing.robot.entity.datebase.MessageQueue;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-14 014 17:06:42
 * @Version 1.0
 */
public interface MessageQueueDao extends JpaRepository<MessageQueue, Integer> {
}
