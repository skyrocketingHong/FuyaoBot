package ninja.skyrocketing.bot.fuyao.mapper.hearthstone;

import ninja.skyrocketing.bot.fuyao.pojo.hearthstone.HsCard;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HsCardMapper {
    int deleteByPrimaryKey(String id);

    int insert(HsCard record);

    int insertSelective(HsCard record);

    HsCard selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(HsCard record);

    int updateByPrimaryKey(HsCard record);

    List<HsCard> selectBySetOrderByRandom(String setName);
}