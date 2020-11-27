package ninja.skyrocketing.bot.fuyao.mapper.bot;

import ninja.skyrocketing.bot.fuyao.pojo.bot.BotFunctionTrigger;

public interface BotFunctionTriggerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BotFunctionTrigger record);

    int insertSelective(BotFunctionTrigger record);

    BotFunctionTrigger selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BotFunctionTrigger record);

    int updateByPrimaryKey(BotFunctionTrigger record);
}