package cn.wind.db.sys.service;

import cn.wind.db.sys.entity.SysPrivilege;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 角色资源表 服务类
 * </p>
 *
 * @author xukk
 * @since 2018-06-21
 */
public interface ISysPrivilegeService extends IService<SysPrivilege> {
    /**
     * 通过permissionId获取
     * @param permissionId
     * @return
     */
    List<SysPrivilege> findByPermissionId(Long permissionId);

    Boolean deleteByRoleId(Long roleId);
}
