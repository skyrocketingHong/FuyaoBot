package ninja.skyrocketing.bot.fuyao.mapper.bot;

import ninja.skyrocketing.bot.fuyao.pojo.bot.BotGameFishing;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BotGameFishingMapper {
    int deleteByPrimaryKey(String fishId);

    int insert(BotGameFishing record);

    int insertSelective(BotGameFishing record);

    BotGameFishing selectByPrimaryKey(String fishId);

    int updateByPrimaryKeySelective(BotGameFishing record);

    int updateByPrimaryKey(BotGameFishing record);

    List<BotGameFishing> getAllFish();
}