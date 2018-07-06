package cn.wind.db.sys.service;

import cn.wind.db.sys.entity.SysRole;
import cn.wind.db.sys.entity.SysUserGroup;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 用户组 服务类
 * </p>
 *
 * @author xukk
 * @since 2018-06-07
 */
public interface ISysUserGroupService extends IService<SysUserGroup> {
    /**
     * 根据用户ID获取用户组
     * @param userId
     * @return
     */
    List<SysUserGroup> findByUserId(Long userId);
    /**
     * 根据单个用户组ID获取角色
     * @param groupId
     * @return
     */
    List<SysRole> findByGroupId(Long groupId);
    /**
     * 根据多个用户组ID获取角色
     * @param groupIds
     * @return
     */
    List<SysRole> findByGroupIds( List<Long> groupIds);

}
