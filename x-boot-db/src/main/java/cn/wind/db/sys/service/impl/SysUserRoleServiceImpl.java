package cn.wind.db.sys.service.impl;

import cn.wind.db.sys.dao.SysUserRoleMapper;
import cn.wind.db.sys.entity.SysUserRole;
import cn.wind.db.sys.service.ISysUserRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xukk
 * @since 2018-06-21
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {
    @Override
    public List<SysUserRole> findByRoleId(Long roleId) {
        return this.baseMapper.findByRoleId(roleId);
    }
}
