package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserDailyRecord;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;

/**
 * <p>
 * 用户每日新增数据记录数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
public interface ArUserDailyRecordMapper extends BaseMapper<ArUserDailyRecord> {

    String RESULT_COLUMN = "id,create_time,modify_time,create_by,modify_by,user_id, daily_active_num, daily_charm_num, daily_wealth_num, daily_great_num, daily_praise_num, daily_sentiment, daily_focus, daily_follow, daily_new_works, daily_works_great, daily_works_share, daily_circle_great, now_date, score_new_works, score_work_great, score_work_share, score_cricle_great, score_follow";

    @Select("select "+RESULT_COLUMN+" from cx_ar_user_daily_record where user_id = #{userId} and now_date = #{date}")
    ArUserDailyRecord findOneByUserIdAndDate(@Param("userId") Long userId, @Param("date") LocalDate date);
}
