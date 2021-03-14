package ninja.skyrocketing.bot.fuyao.service.bot;

import ninja.skyrocketing.bot.fuyao.pojo.bot.BotReplyMessage;

import java.util.List;

/**
 * @author skyrocketing Hong
 * @date 2020-11-29 02:31:23
 */
public interface BotReplyMessageService {
    List<BotReplyMessage> getAllReplyMessage();
}
