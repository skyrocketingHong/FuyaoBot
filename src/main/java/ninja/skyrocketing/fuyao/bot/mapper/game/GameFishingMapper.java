package ninja.skyrocketing.fuyao.bot.mapper.game;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ninja.skyrocketing.fuyao.bot.pojo.game.GameFishing;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GameFishingMapper extends BaseMapper<GameFishing> {
    @Select("select * from game_fishing order by fish_probability asc")
    List<GameFishing> getAllFish();

    @Select("select fish_name from game_fishing where fish_id = #{fishId,jdbcType=VARCHAR}")
    String selectFishNameByPrimaryKey(String fishId);

    @Select("select fish_value from game_fishing where fish_id = #{fishId,jdbcType=VARCHAR}")
    Long getFishValueById(String id);
}