package ninja.skyrocketing.bot.fuyao.mapper.bot;

import ninja.skyrocketing.bot.fuyao.pojo.bot.BotFunctionTrigger;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BotFunctionTriggerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BotFunctionTrigger record);

    int insertSelective(BotFunctionTrigger record);

    BotFunctionTrigger selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BotFunctionTrigger record);

    int updateByPrimaryKey(BotFunctionTrigger record);

    List<BotFunctionTrigger> selectAllFunctionTrigger();
}