package ninja.skyrocketing.fuyao.bot.mapper.bot;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ninja.skyrocketing.fuyao.bot.pojo.bot.BotBanedUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BotBanedUserMapper extends BaseMapper<BotBanedUser> {

}