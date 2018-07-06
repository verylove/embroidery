package cn.wind.db.sys.service.impl;

import cn.wind.db.sys.entity.SysPrivilege;
import cn.wind.db.sys.dao.SysPrivilegeMapper;
import cn.wind.db.sys.service.ISysPrivilegeService;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色资源表 服务实现类
 * </p>
 *
 * @author xukk
 * @since 2018-06-21
 */
@Service
public class SysPrivilegeServiceImpl extends ServiceImpl<SysPrivilegeMapper, SysPrivilege> implements ISysPrivilegeService {
    @Override
    public List<SysPrivilege> findByPermissionId(Long permissionId) {
        return this.baseMapper.findByPermissionId(permissionId);
    }

    @Override
    public Boolean deleteByRoleId(Long roleId) {
        return SqlHelper.delBool(this.baseMapper.deleteByRoleId(roleId));
    }
}
