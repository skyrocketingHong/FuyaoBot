package ninja.skyrocketing.bot.fuyao.mapper.bot;

import ninja.skyrocketing.bot.fuyao.pojo.bot.BotConfig;

public interface BotConfigMapper {
    int deleteByPrimaryKey(String configName);

    int insert(BotConfig record);

    int insertSelective(BotConfig record);

    BotConfig selectByPrimaryKey(String configName);

    int updateByPrimaryKeySelective(BotConfig record);

    int updateByPrimaryKey(BotConfig record);
}