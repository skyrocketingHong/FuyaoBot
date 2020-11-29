package ninja.skyrocketing.bot.fuyao.service.impl.bot;

import ninja.skyrocketing.bot.fuyao.mapper.bot.BotReplyMessageMapper;
import ninja.skyrocketing.bot.fuyao.pojo.bot.BotReplyMessage;
import ninja.skyrocketing.bot.fuyao.service.bot.BotReplyMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author skyrocketing Hong
 * @Date 2020-11-29 029 02:34:11
 * @Version 1.0
 */

@Service
public class BotReplyMessageServiceImpl implements BotReplyMessageService {
    @Autowired
    BotReplyMessageMapper botReplyMessageMapper;

    @Override
    public List<BotReplyMessage> GetAllReplyMessage() {
        return botReplyMessageMapper.selectAllBotReplyMessage();
    }
}
