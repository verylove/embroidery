package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserFollows;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户关注数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-16
 */
public interface ArUserFollowsMapper extends BaseMapper<ArUserFollows> {

    String RESULT_COLUMN="id,user_id,follow_id,status,create_time,modify_time,create_by,modify_by";

    @Select("select "+RESULT_COLUMN+" from cx_ar_user_follows where user_id = #{userId} and follow_id = #{followId} limit 1")
    ArUserFollows findOneByUserIdAndFollowId(@Param("userId") Long userId, @Param("followId") Long followId);

    @Select("select follow_id from cx_ar_user_follows where user_id = #{userId}")
    List<Long> findAllFollowIdsByUserId(@Param("userId") Long userId);
}
