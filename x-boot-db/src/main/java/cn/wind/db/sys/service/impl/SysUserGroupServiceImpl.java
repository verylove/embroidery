package cn.wind.db.sys.service.impl;

import cn.wind.db.sys.dao.SysUserGroupMapper;
import cn.wind.db.sys.entity.SysRole;
import cn.wind.db.sys.entity.SysUserGroup;
import cn.wind.db.sys.service.ISysUserGroupService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户组 服务实现类
 * </p>
 *
 * @author xukk
 * @since 2018-06-07
 */
@Service
public class SysUserGroupServiceImpl extends ServiceImpl<SysUserGroupMapper, SysUserGroup> implements ISysUserGroupService {
    @Override
    public List<SysUserGroup> findByUserId(Long userId) {
        return this.baseMapper.findByUserId(userId);
    }

    @Override
    public List<SysRole> findByGroupId(Long groupId) {
        return this.baseMapper.findByGroupId(groupId);
    }

    @Override
    public List<SysRole> findByGroupIds(List<Long> groupIds) {
        return this.baseMapper.findByGroupIds(groupIds);
    }
}
