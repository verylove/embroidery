package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserSpEvaluatesGreatNum;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户特价纹身点赞次数数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-12
 */
public interface ArUserSpEvaluatesGreatNumMapper extends BaseMapper<ArUserSpEvaluatesGreatNum> {

    String RESULT_COLUMN = "id,sp_evaluate_id,user_id,reply_id,num,create_time,modify_time,create_by,modify_by";

    @Select("select "+RESULT_COLUMN+" from cx_ar_user_sp_evaluates_great_num where sp_evaluate_id = #{spEvaluateId} and user_id = #{userId} limit 1")
    ArUserSpEvaluatesGreatNum findOneByEvaluteIdAndUserId(@Param(value = "spEvaluateId") Long spEvaluateId, @Param(value = "userId")Long userId);
}
