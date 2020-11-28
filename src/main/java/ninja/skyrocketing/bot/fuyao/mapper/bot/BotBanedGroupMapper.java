package ninja.skyrocketing.bot.fuyao.mapper.bot;

import ninja.skyrocketing.bot.fuyao.pojo.bot.BotBanedGroup;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BotBanedGroupMapper {
    int deleteByPrimaryKey(Long groupId);

    int insert(BotBanedGroup record);

    int insertSelective(BotBanedGroup record);

    BotBanedGroup selectByPrimaryKey(Long groupId);

    int updateByPrimaryKeySelective(BotBanedGroup record);

    int updateByPrimaryKey(BotBanedGroup record);
}