package ninja.skyrocketing.fuyao.bot.service.impl.group;

import ninja.skyrocketing.fuyao.bot.mapper.group.GroupCoinMapper;
import ninja.skyrocketing.fuyao.bot.pojo.group.GroupCoin;
import ninja.skyrocketing.fuyao.bot.pojo.user.User;
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
    public GroupCoin getCoinByGroupUser(User user) {
        return groupCoinMapper.selectByPrimaryKey(user.getGroupId(), user.getUserId());
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
    public int deleteCoin(User user) {
        return groupCoinMapper.deleteByPrimaryKey(user.getGroupId(), user.getUserId());
    }

    @Override
    public int deleteCoinByGroupId(Long groupId) {
        return groupCoinMapper.deleteByGroupId(groupId);
    }
}
