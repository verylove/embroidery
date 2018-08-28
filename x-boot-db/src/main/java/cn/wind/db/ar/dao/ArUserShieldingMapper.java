package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArUserShielding;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户屏蔽数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-08-21
 */
public interface ArUserShieldingMapper extends BaseMapper<ArUserShielding> {

    String RESULT_COLUMN = "id,user_id,shielding_id,status,create_time,modify_time,create_by,modify_by";

    @Select("select DISTINCT shielding_id FROM cx_ar_user_shielding where user_id = #{userId} and status = 1")
    List<Long> findAllShieldIdsByUserId(@Param("userId") long userId);

    @Select("select DISTINCT user_id FROM cx_ar_user_shielding where shielding_id = #{shieldId} and status = 1")
    List<Long> findAllUserIdsByShieldId(@Param("shieldId")long shieldId);

    @Select("select "+RESULT_COLUMN+" FROM cx_ar_user_shielding where user_id = #{userId} and shielding_id = #{shieldId}")
    ArUserShielding findOneByUserIdAndShieldId(@Param("userId")long userId, @Param("shieldId")long shieldId);
}
