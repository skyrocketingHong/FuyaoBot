package ninja.skyrocketing.bot.fuyao.service.impl.group;

import ninja.skyrocketing.bot.fuyao.mapper.group.GroupExpRankNameMapper;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupExpRankName;
import ninja.skyrocketing.bot.fuyao.service.group.GroupExpRankNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-28 028 20:22:12
 * @Version 1.0
 */
@Service
public class GroupExpRankNameServiceImpl implements GroupExpRankNameService {
    @Autowired
    GroupExpRankNameMapper groupExpRankNameMapper;

    @Override
    public GroupExpRankName GetGroupExpRankNameById(Long id) {
        return groupExpRankNameMapper.selectByPrimaryKey(id);
    }
}