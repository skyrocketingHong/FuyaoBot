package ninja.skyrocketing.fuyao.bot.service.group;

import ninja.skyrocketing.fuyao.bot.pojo.group.GroupTimelyMessage;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupUser;

import java.util.List;

/**
 * @author skyrocketing Hong
 * @date 2020-12-01 16:09:12
 */
public interface GroupTimelyMessageService {
    List<GroupTimelyMessage> getAllTimelyMessage();

    int deleteSentMessageByGroupUser(GroupUser groupUser);

    int deleteSentMessageById(Long groupId, Long userId);
}
