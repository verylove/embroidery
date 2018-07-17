package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserOtEvaluatesGreatNum;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 纹纹达人用户评论点赞次数数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-17
 */
public interface ArUserOtEvaluatesGreatNumMapper extends BaseMapper<ArUserOtEvaluatesGreatNum> {

    String RESULT_COLUMN = "id,ot_evaluate_id,user_id,reply_id,num,create_time,modify_time,create_by,modify_by";

    @Select("select "+RESULT_COLUMN+" from cx_ar_user_ot_evaluates_great_num where ot_evaluate_id = #{otEvaluateId} and user_id = #{userId} limit 1")
    ArUserOtEvaluatesGreatNum findOneByEvaluteIdAndUserId(@Param("otEvaluateId") Long otEvaluateId, @Param("userId") Long userId);
}
