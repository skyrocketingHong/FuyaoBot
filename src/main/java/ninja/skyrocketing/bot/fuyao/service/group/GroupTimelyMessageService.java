package ninja.skyrocketing.bot.fuyao.service.group;

import ninja.skyrocketing.bot.fuyao.pojo.group.GroupTimelyMessage;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupUser;

import java.util.List;

/**
 * @Author skyrocketing Hong
 * @Date 2020-12-01 16:09:12
 */
public interface GroupTimelyMessageService {
    List<GroupTimelyMessage> GetAllTimelyMessage();

    int DeleteSentMessageByGroupUser(GroupUser groupUser);

    int DeleteSentMessageById(Long groupId, Long userId);
}
