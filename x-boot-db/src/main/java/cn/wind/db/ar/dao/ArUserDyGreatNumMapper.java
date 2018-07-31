package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserDyGreatNum;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 动态作品点赞次数数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
public interface ArUserDyGreatNumMapper extends BaseMapper<ArUserDyGreatNum> {

    String RESULT_COLUMN = "id,dy_works_id,user_id,author_id,num,create_time,modify_time,create_by,modify_by";

    @Select("select "+RESULT_COLUMN+" from cx_ar_user_dy_great_num where dy_works_id = #{dyWorksId} and user_id = #{userId} limit 1")
    ArUserDyGreatNum findOneByDyWorksIdAndUserId(Long dyWorksId, Long userId);
}
