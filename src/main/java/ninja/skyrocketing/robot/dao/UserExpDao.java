package ninja.skyrocketing.robot.dao;

import ninja.skyrocketing.robot.entity.datebase.UserExp;
import ninja.skyrocketing.robot.entity.datebase.UserExpIds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author skyrocketing Hong
 * @Date 2020-07-14 014 17:07:41
 * @Version 1.0
 */
public interface UserExpDao extends JpaRepository<UserExp, Integer> {
	@Query("select userExp.userExpIds from UserExp userExp where userExp.userExpIds.groupId = :groupId order by userExp.exp desc")
	List<UserExpIds> findUserExpByGroupId(Long groupId);
}
