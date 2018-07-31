package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserGreats;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户被点赞次数数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-20
 */
public interface ArUserGreatsMapper extends BaseMapper<ArUserGreats> {

    String RESULT_COLUMN = "id,user_id,great_id,num,create_time,modify_time,create_by,modify_by";

    @Select("select "+RESULT_COLUMN+" from cx_ar_user_greats where user_id = #{userId} and great_id = #{greatId}")
    ArUserGreats findByUserIdAndGreatId(@Param("userId") Long userId, @Param("greatId") Long greatId);
}
