package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserTbGreatNum;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户贴吧话题点赞次数数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-16
 */
public interface ArUserTbGreatNumMapper extends BaseMapper<ArUserTbGreatNum> {

    String RESULT_COLUMN= "id,tb_topic_id,user_id,author_id,num,create_time,modify_time,create_by,modify_by";

    @Select("select "+RESULT_COLUMN+" from cx_ar_user_tb_great_num where tb_topic_id = #{tbTopicId} and user_id = #{userId} limit 1")
    ArUserTbGreatNum findOneByTopicIdAndUserId(@Param("tbTopicId") Long tbTopicId, @Param("userId") Long userId);
}
