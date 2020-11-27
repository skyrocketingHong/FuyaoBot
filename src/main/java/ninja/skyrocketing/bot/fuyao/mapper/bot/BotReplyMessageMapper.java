package ninja.skyrocketing.bot.fuyao.mapper.bot;

import ninja.skyrocketing.bot.fuyao.pojo.bot.BotReplyMessage;

public interface BotReplyMessageMapper {
    int deleteByPrimaryKey(String replyKey);

    int insert(BotReplyMessage record);

    int insertSelective(BotReplyMessage record);

    BotReplyMessage selectByPrimaryKey(String replyKey);

    int updateByPrimaryKeySelective(BotReplyMessage record);

    int updateByPrimaryKey(BotReplyMessage record);
}