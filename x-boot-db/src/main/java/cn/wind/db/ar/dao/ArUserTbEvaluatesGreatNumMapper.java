package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserTbEvaluatesGreatNum;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户话题贴吧评论点赞次数数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-16
 */
public interface ArUserTbEvaluatesGreatNumMapper extends BaseMapper<ArUserTbEvaluatesGreatNum> {

    String RESULT_COLUMN = "id,tb_evaluate_id,user_id,reply_id,num,create_time,modify_time,create_by,modify_by";

    @Select("select "+RESULT_COLUMN+" from cx_ar_user_tb_evaluates_great_num where tb_evaluate_id = #{tbEvaluateId} and user_id = #{userId} limit 1")
    ArUserTbEvaluatesGreatNum findOneByEvaluteIdAndUserId(@Param("tbEvaluateId") Long tbEvaluateId, @Param("userId") Long userId);
}
