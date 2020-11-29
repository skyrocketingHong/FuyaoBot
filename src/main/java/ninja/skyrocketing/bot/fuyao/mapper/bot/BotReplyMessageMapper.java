package ninja.skyrocketing.bot.fuyao.mapper.bot;

import ninja.skyrocketing.bot.fuyao.pojo.bot.BotReplyMessage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BotReplyMessageMapper {
    int deleteByPrimaryKey(String replyKey);

    int insert(BotReplyMessage record);

    int insertSelective(BotReplyMessage record);

    BotReplyMessage selectByPrimaryKey(String replyKey);

    int updateByPrimaryKeySelective(BotReplyMessage record);

    int updateByPrimaryKey(BotReplyMessage record);

    List<BotReplyMessage> selectAllBotReplyMessage();
}