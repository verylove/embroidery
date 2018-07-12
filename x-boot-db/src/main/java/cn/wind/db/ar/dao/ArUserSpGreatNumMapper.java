package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserSpGreatNum;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户特价纹身点赞次数数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-11
 */
public interface ArUserSpGreatNumMapper extends BaseMapper<ArUserSpGreatNum> {

    String RESULT_COLUMN = "id,special_tattoo_id,user_id,author_id,num,create_time,modify_time,create_by,modify_by";

    @Select("select "+RESULT_COLUMN+" from cx_ar_user_sp_great_num where special_tattoo_id = #{spTattooId} and user_id = #{userId} and num >0 limit 1")
    ArUserSpGreatNum findOneByTattooIdAndUserId(@Param("spTattooId") Long spTattooId, @Param("userId") Long userId);
}
