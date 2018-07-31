package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserDyEvaluatesGreatNum;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 动态作品用户评论点赞次数数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
public interface ArUserDyEvaluatesGreatNumMapper extends BaseMapper<ArUserDyEvaluatesGreatNum> {

    String RESLUT_COLUMN="id,dy_evaluate_id,user_id,reply_id,num,create_time,modify_time,create_by,modify_by";

    @Select("select "+RESLUT_COLUMN+" from cx_ar_user_dy_evaluates_great_num where dy_evaluate_id = #{dyEvaluateId} and user_id = #{userId} limit 1")
    ArUserDyEvaluatesGreatNum findOneByEvaluteIdAndUserId(@Param("dyEvaluateId") Long dyEvaluateId, @Param("userId") Long userId);
}
