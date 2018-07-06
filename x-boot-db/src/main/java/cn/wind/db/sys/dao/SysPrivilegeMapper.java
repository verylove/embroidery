package cn.wind.db.sys.dao;

import cn.wind.db.sys.entity.SysPrivilege;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 角色资源表 Mapper 接口
 * </p>
 *
 * @author xukk
 * @since 2018-06-21
 */
public interface SysPrivilegeMapper extends BaseMapper<SysPrivilege> {
    /**
     *
     * @param permissionId
     * @return
     */
    @Select("select * from t_sys_privilege where permission_id=#{permissionId}")
    List<SysPrivilege> findByPermissionId(@Param("permissionId") Long permissionId);
    @Delete("delete from t_sys_privilege where role_id=#{roleId}")
    Integer deleteByRoleId(@Param("roleId")Long roleId);
}
