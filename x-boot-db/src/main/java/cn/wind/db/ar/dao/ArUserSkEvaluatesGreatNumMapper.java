package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserSkEvaluatesGreatNum;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 图库找图用户评论点赞次数数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-13
 */
public interface ArUserSkEvaluatesGreatNumMapper extends BaseMapper<ArUserSkEvaluatesGreatNum> {

    String RESULT_COLUMN = "id,sk_evaluate_id,user_id,reply_id,num,create_time,modify_time,create_by,modify_by";

    @Select("select "+ RESULT_COLUMN +" from cx_ar_user_sk_evaluates_great_num where sk_evaluate_id = #{skEvaluateId} and user_id = #{userId} limit 1")
    ArUserSkEvaluatesGreatNum findOneByEvaluteIdAndUserId(@Param("skEvaluateId") Long skEvaluateId, @Param("userId") Long userId);
}
