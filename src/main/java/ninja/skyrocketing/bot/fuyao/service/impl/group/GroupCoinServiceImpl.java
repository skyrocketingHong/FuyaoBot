package ninja.skyrocketing.bot.fuyao.service.impl.group;

import ninja.skyrocketing.bot.fuyao.mapper.group.GroupCoinMapper;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupCoin;
import ninja.skyrocketing.bot.fuyao.pojo.group.GroupUser;
import ninja.skyrocketing.bot.fuyao.service.group.GroupCoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author skyrocketing Hong
 * @date 2020-11-28 21:26:06
 */

@Service
public class GroupCoinServiceImpl implements GroupCoinService {
    @Autowired
    GroupCoinMapper groupCoinMapper;

    @Override
    public GroupCoin getCoinByGroupUser(GroupUser groupUser) {
        return groupCoinMapper.selectByPrimaryKey(groupUser.getGroupId(), groupUser.getUserId());
    }

    @Override
    public int insertCoin(GroupCoin groupCoin) {
        return groupCoinMapper.insertSelective(groupCoin);
    }

    @Override
    public int updateCoin(GroupCoin groupCoin) {
        return groupCoinMapper.updateByPrimaryKeySelective(groupCoin);
    }

    @Override
    public int deleteCoin(GroupUser groupUser) {
        return groupCoinMapper.deleteByPrimaryKey(groupUser.getGroupId(), groupUser.getUserId());
    }

    @Override
    public int deleteCoinByGroupId(Long groupId) {
        return groupCoinMapper.deleteByGroupId(groupId);
    }
}
