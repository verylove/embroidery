package cn.wind.db.sys.service.impl;

import cn.wind.common.utils.BlankUtil;
import cn.wind.db.sys.dao.SysUserMapper;
import cn.wind.db.sys.entity.*;
import cn.wind.db.sys.service.*;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xukk
 * @since 2018-06-07
 */
@Service
@Transactional
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    @Autowired
    private ISysPermissionService sysResourceService;
    @Autowired
    private ISysRoleService sysRoleService;
    @Autowired
    private ISysUserRoleService userRoleService;
    @Autowired
    private ISysUserGroupShipService userGroupShipService;
    @Override
    public void updateForAccountNonLocked(Boolean accountNonLocked, String username) {
        this.baseMapper.updateForAccountNonLocked(accountNonLocked,username);
    }

    @Override
    public SysUser findByUsername(String username) {
        SysUser sysUser=this.baseMapper.findByUsername(username);
        if(sysUser!=null){
            List<SysRole> roles=sysRoleService.findByUserId(sysUser.getId());
            List<SysPermission> permissons=sysResourceService.findByUserId(sysUser.getId());
            sysUser.setRoles(roles);
            sysUser.setPermissions(permissons);
        }
        return sysUser;
    }

    @Override
    public boolean save(SysUser sysUser) {
        if(BlankUtil.isBlank(sysUser)){
            throw new RuntimeException("user is null");
        }
        this.baseMapper.insert(sysUser);
        Long userId=sysUser.getId();
        List<SysUserRole> sysUserRoles= Lists.newArrayList();
        if(BlankUtil.isNotBlank(sysUser.getRoles())){
            sysUser.getRoles().forEach(v->{
                SysUserRole userRole=new SysUserRole();
                userRole.setRoleId(v.getId());
                userRole.setUserId(userId);
                sysUserRoles.add(userRole);
            });
            userRoleService.insertBatch(sysUserRoles);
        }
        if(BlankUtil.isNotBlank(sysUser.getGroups())){
            List<SysUserGroupShip> userGroupShips=Lists.newArrayList();
            sysUser.getGroups().forEach(v->{
                SysUserGroupShip userGroupShip=new SysUserGroupShip();
                userGroupShip.setGroupId(v.getId());
                userGroupShip.setUserId(userId);
                userGroupShips.add(userGroupShip);
            });
            userGroupShipService.insertBatch(userGroupShips);
        }
       return  true;
    }

    @Override
    public SysUser findUserInfoById(Long userId) {
       return this.baseMapper.findUserInfoById(userId);
    }

    @Override
    public Page<SysUser> findByCondition(Page page,Map<String, Object> var1) {
        return page.setRecords(this.baseMapper.findByCondition(page,var1));
    }

    @Override
    public boolean updatePasswordById(String password,Long id) {
        return SqlHelper.delBool(this.baseMapper.updatePassowrdById(password,id));
    }
}
