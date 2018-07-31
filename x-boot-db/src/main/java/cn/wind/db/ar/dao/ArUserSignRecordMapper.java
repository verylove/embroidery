package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserSignRecord;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户签到记录表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-09
 */
public interface ArUserSignRecordMapper extends BaseMapper<ArUserSignRecord> {

    String RESULT_COLUMN = "id,user_id,sign_date,create_time,modify_time,create_by,modify_by";

    @Select("select "+RESULT_COLUMN+" from cx_ar_user_sign_record where sign_date >= #{firstDayByNow} and sign_date <= #{lastDayByNow} and user_id = #{userId}")
    List<ArUserSignRecord> findAllBetweenDaysAndUserId(@Param("userId")Long userId, @Param("firstDayByNow") LocalDate firstDayByNow, @Param("lastDayByNow")LocalDate lastDayByNow);

    @Select("select "+RESULT_COLUMN+" from cx_ar_user_sign_record where user_id = #{userId} and sign_date = #{dateBefore} limit 1" )
    ArUserSignRecord findAllByDateAndUserId(@Param(value = "userId") Long userId, @Param(value = "dateBefore") LocalDate dateBefore);
}
