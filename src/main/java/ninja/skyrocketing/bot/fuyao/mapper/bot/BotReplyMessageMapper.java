package ninja.skyrocketing.bot.fuyao.mapper.bot;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ninja.skyrocketing.bot.fuyao.pojo.bot.BotReplyMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BotReplyMessageMapper extends BaseMapper<BotReplyMessage> {
    @Select("select reply_key, reply_value from bot_reply_message")
    List<BotReplyMessage> selectAllBotReplyMessage();
}