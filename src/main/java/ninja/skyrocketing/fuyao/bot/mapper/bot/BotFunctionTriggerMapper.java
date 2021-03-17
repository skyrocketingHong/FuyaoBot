package ninja.skyrocketing.fuyao.bot.mapper.bot;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ninja.skyrocketing.fuyao.bot.pojo.bot.BotFunctionTrigger;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author skyrocketing Hong
 */

@Mapper
public interface BotFunctionTriggerMapper extends BaseMapper<BotFunctionTrigger> {
    @Select("select * from bot_function_trigger")
    List<BotFunctionTrigger> selectAllFunctionTrigger();
}