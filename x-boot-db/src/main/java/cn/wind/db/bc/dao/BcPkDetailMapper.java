package cn.wind.db.bc.dao;

import cn.wind.db.bc.entity.BcPkDetail;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 主播PK详情数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-08-29
 */
public interface BcPkDetailMapper extends BaseMapper<BcPkDetail> {

    String RESULT_COLUMN="id,invite_user_id,en_invite_user_id,winner,invite_amount,en_invite_amount,create_time,modify_time,create_by,modify_by,category";

    @Select("select "+RESULT_COLUMN+" from cx_bc_pk_detail where invite_user_id = #{inviteUserID} and en_invite_user_id = #{enInviteUserID}")
    BcPkDetail findOneByIds(@Param("inviteUserID") long inviteUserID, @Param("enInviteUserID") long enInviteUserID);
}
