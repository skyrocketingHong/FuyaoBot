package ninja.skyrocketing.fuyao.bot.service.impl.group;

import ninja.skyrocketing.fuyao.bot.mapper.group.GroupExpRankNameMapper;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupExpRankName;
import ninja.skyrocketing.fuyao.bot.service.group.GroupExpRankNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author skyrocketing Hong
 * @date 2020-11-28 20:22:12
 */
@Service
public class GroupExpRankNameServiceImpl implements GroupExpRankNameService {
    private static GroupExpRankNameMapper groupExpRankNameMapper;
    @Autowired
    public GroupExpRankNameServiceImpl(GroupExpRankNameMapper groupExpRankNameMapper) {
        GroupExpRankNameServiceImpl.groupExpRankNameMapper = groupExpRankNameMapper;
    }

    @Override
    public GroupExpRankName getGroupExpRankNameById(Long id) {
        return groupExpRankNameMapper.selectById(id);
    }
}
