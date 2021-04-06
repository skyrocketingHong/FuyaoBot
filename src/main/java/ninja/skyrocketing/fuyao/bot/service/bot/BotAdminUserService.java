package ninja.skyrocketing.fuyao.bot.service.bot;

/**
 * @Author skyrocketing Hong
 * @Date 2021-04-06 13:13:16
 */
public interface BotAdminUserService {
    /**
     * 判断用户是否为管理员
     * @param id 用户QQ号
     * @return true或false
     * */
    boolean isAdmin(Long id);
}
