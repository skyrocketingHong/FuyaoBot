package ninja.skyrocketing.fuyao.bot.service.bot;

import ninja.skyrocketing.fuyao.bot.pojo.bot.BotReplyMessage;

import java.util.List;

/**
 * @author skyrocketing Hong
 * @date 2020-11-29 02:31:23
 */
public interface BotReplyMessageService {
    List<BotReplyMessage> getAllReplyMessage();
}
