package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserSignNum;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户累计签到次数数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-09
 */
public interface ArUserSignNumMapper extends BaseMapper<ArUserSignNum> {

    String RESULT_COLUMN = "id,user_id,continue_days,create_time,modify_time,create_by,modify_by";

    @Select("select "+RESULT_COLUMN+" from cx_ar_user_sign_num where user_id = #{userId} limit 1")
    ArUserSignNum findOneByUserId(@Param("userId") Long userId);
}
