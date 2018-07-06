package cn.wind.db.sys.service;

import cn.wind.db.sys.entity.SysRole;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author xukk
 * @since 2018-06-07
 */
public interface ISysRoleService extends IService<SysRole> {
    /**
     *  根据用户ID获取角色
     * @param userId
     * @return
     */
    List<SysRole> findByUserId(Long userId);
}
