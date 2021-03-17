package ninja.skyrocketing.fuyao.bot.service.impl.group;

import ninja.skyrocketing.fuyao.bot.mapper.group.GroupCoinMapper;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupCoin;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupUser;
import ninja.skyrocketing.fuyao.bot.service.group.GroupCoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author skyrocketing Hong
 * @date 2020-11-28 21:26:06
 */

@Service
public class GroupCoinServiceImpl implements GroupCoinService {
    private static GroupCoinMapper groupCoinMapper;
    @Autowired
    public GroupCoinServiceImpl(GroupCoinMapper groupCoinMapper) {
        GroupCoinServiceImpl.groupCoinMapper = groupCoinMapper;
    }

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
