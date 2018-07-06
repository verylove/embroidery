package cn.wind.db.sys.service.impl;

import cn.wind.db.sys.dao.SysPermissionMapper;
import cn.wind.db.sys.entity.SysPermission;
import cn.wind.db.sys.service.ISysPermissionService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author xukk
 * @since 2018-06-07
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {
    @Override
    public List<SysPermission> findByUserId(Long userId) {
        return this.baseMapper.findByUserId(userId);
    }

    @Override
    public List<SysPermission> findAll() {
        return this.baseMapper.selectList(new EntityWrapper<SysPermission>());
    }

    @Override
    public List<SysPermission> findByLevelOrderBySort(Integer level) {
        EntityWrapper<SysPermission> ew = new EntityWrapper<>();
        ew.setEntity(new SysPermission());
        ew.where("level={0}",level).orderBy("sort");
        return this.baseMapper.selectList(ew);
    }

    @Override
    public List<SysPermission> findByParentIdOrderBySort(Long parentId) {
        EntityWrapper<SysPermission> ew = new EntityWrapper<>();
        ew.setEntity(new SysPermission());
        ew.where("p_id={0}",parentId).orderBy("sort");
        return this.baseMapper.selectList(ew);
    }

    @Override
    public List<SysPermission> findByRoleId(Long roleId) {
        return this.baseMapper.findByRoleId(roleId);
    }
}
