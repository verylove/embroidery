package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserBlacklist;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 用户拉黑数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-08-30
 */
public interface ArUserBlacklistMapper extends BaseMapper<ArUserBlacklist> {

    String RESLUT_COLUMN = "id,user_id,black_id,status,create_time,modify_time,create_by,modify_by";

    @Select("select "+RESLUT_COLUMN+" from cx_ar_user_blacklist where user_id = #{toUserId} and black_id = #{userId}")
    ArUserBlacklist findOneByUserIdAndBlackId(@Param("userId") Long userId, @Param("toUserId") Long toUserId);
}
