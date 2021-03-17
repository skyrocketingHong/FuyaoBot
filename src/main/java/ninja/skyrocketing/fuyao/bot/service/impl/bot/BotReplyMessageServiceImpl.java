package ninja.skyrocketing.fuyao.bot.service.impl.bot;

import ninja.skyrocketing.fuyao.bot.mapper.bot.BotReplyMessageMapper;
import ninja.skyrocketing.fuyao.bot.pojo.bot.BotReplyMessage;
import ninja.skyrocketing.fuyao.bot.service.bot.BotReplyMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author skyrocketing Hong
 * @date 2020-11-29 02:34:11
 */

@Service
public class BotReplyMessageServiceImpl implements BotReplyMessageService {
    private static BotReplyMessageMapper botReplyMessageMapper;
    @Autowired
    public BotReplyMessageServiceImpl(BotReplyMessageMapper botReplyMessageMapper) {
        BotReplyMessageServiceImpl.botReplyMessageMapper = botReplyMessageMapper;
    }

    @Override
    public List<BotReplyMessage> getAllReplyMessage() {
        return botReplyMessageMapper.selectAllBotReplyMessage();
    }
}
