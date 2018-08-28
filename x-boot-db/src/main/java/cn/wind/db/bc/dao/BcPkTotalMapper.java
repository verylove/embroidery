package cn.wind.db.bc.dao;

import cn.wind.db.bc.entity.BcPkTotal;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * <p>
 * 主播PK总数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-08-28
 */
public interface BcPkTotalMapper extends BaseMapper<BcPkTotal> {

    String RESULT_COLUMN = "id,user_id,room_id,pk_wins,win_num,failure_num,draw_num,city_id,live_earnings,create_time,modify_time,create_by,modify_by";

    @Select("select "+RESULT_COLUMN+" from cx_bc_pk_total where user_id = #{userId}")
    BcPkTotal findOneByUserId(@Param(value = "userId") long userId);

    Long findRankInCity(Map<String,Object> map);
}
