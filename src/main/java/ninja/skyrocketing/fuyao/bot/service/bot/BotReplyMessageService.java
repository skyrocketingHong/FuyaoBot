package ninja.skyrocketing.fuyao.bot.service.bot;

import ninja.skyrocketing.fuyao.bot.pojo.bot.BotReplyMessage;

import java.util.List;

/**
 * @author skyrocketing Hong
 * @date 2020-11-29 02:31:23
 */
public interface BotReplyMessageService {
    List<BotReplyMessage> getAllReplyMessage();
    
    /**
     * 获取加群后群成员对应的称呼
     *
     * @param id 群号
     * @return 称呼
     */
    String getGroupMemberTitleById(String id);
    
    /**
     *  根据ID获取该表中的数据
     *
     * @param id ID
     * @return BotReplyMessage
     */
    BotReplyMessage getBotReplyMessageById(String id);
}
