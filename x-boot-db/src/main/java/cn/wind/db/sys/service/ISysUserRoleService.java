package cn.wind.db.sys.service;

import cn.wind.db.sys.entity.SysUserRole;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xukk
 * @since 2018-06-21
 */
public interface ISysUserRoleService extends IService<SysUserRole> {
    /**
     * 通过roleId查找
     * @param roleId
     * @return
     */
    List<SysUserRole> findByRoleId(Long roleId);

}
