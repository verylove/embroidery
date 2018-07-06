package cn.wind.db.sys.dao;

import cn.wind.db.sys.entity.SysPermission;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 资源表 Mapper 接口
 * </p>
 *
 * @author xukk
 * @since 2018-06-07
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    /**
     * x
     * @param userId
     * @return
     */
    @Select("select distinct p.* from t_sys_permission p inner join t_sys_privilege rp on p.id = rp.permission_id inner join t_sys_user_role ru  on ru.role_id = rp.role_id where ru.user_id=#{userId}"
            +" union "
            +"select distinct p.* from t_sys_permission p INNER JOIN t_sys_privilege rp on p.id=rp.permission_id INNER JOIN t_sys_user_group_role_ship ugrs on ugrs.role_id=rp.role_id INNER JOIN t_sys_user_group_ship ugs on ugs.group_id=ugrs.group_id where ugs.user_id=#{userId}"
    )
    List<SysPermission> findByUserId(@Param("userId")Long userId);
    @Select("select spm.* from t_sys_privilege spv left join t_sys_permission spm on spv.permission_id=spm.id where spv.role_id=#{roleId}")
    List<SysPermission> findByRoleId(@Param("roleId")Long roleId);

}
