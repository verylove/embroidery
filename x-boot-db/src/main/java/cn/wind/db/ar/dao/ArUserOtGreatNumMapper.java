package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserOtGreatNum;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 纹纹达人点赞次数数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-17
 */
public interface ArUserOtGreatNumMapper extends BaseMapper<ArUserOtGreatNum> {

    String RESULT_COLUMN = "id,ot_tattoo_id,user_id,author_id,num,create_time,modify_time,create_by,modify_by";

    @Select("select "+RESULT_COLUMN+" from cx_ar_user_ot_great_num where ot_tattoo_id = #{otTattooId} and user_id = #{userId} limit 1")
    ArUserOtGreatNum findOneByOtTattooIdAndUserId(@Param("otTattooId") Long otTattooId, @Param("userId") Long userId);
}
