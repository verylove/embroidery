package cn.wind.db.sys.service.impl;

import cn.wind.db.sys.dao.SysRoleMapper;
import cn.wind.db.sys.entity.SysRole;
import cn.wind.db.sys.service.ISysRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author xukk
 * @since 2018-06-07
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    @Override
    public List<SysRole> findByUserId(Long userId) {
        return this.baseMapper.findByUserId(userId);
    }
}
