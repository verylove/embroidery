package cn.wind.db.sys.dao;

import cn.wind.db.sys.entity.SysUserRole;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xukk
 * @since 2018-06-21
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    @Select("select * from t_sys_user_role where role_id=#{roleId}")
    List<SysUserRole> findByRoleId(@Param("roleId")Long roleId) ;


}
