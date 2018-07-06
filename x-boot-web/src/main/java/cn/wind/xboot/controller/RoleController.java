package cn.wind.xboot.controller;

import cn.wind.common.res.ApiRes;
import cn.wind.db.sys.entity.SysPermission;
import cn.wind.db.sys.entity.SysPrivilege;
import cn.wind.db.sys.entity.SysRole;
import cn.wind.db.sys.entity.SysUserRole;
import cn.wind.klog.annotation.Log;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.sys.SysRoleVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>Title: RoleController</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/25
 */
@Slf4j
@RestController
@Api(value = "role",tags = "角色管理接口")
@RequestMapping("role")
public class RoleController extends BaseController<SysRoleVo, Long> {
    @Override
    public String getBase() {
        return SysRole.class.getPackage().getName();
    }

    @Override
    public IService getService() {
        return sysRoleService;
    }

    @RequestMapping(value = "/getAllList", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部角色")
    public ApiRes roleGetAll() {

        List<SysRole> list = sysRoleService.selectList(new EntityWrapper<SysRole>());
        return ApiRes.Custom().success().addData(list);
    }

    @RequestMapping(value = "/getAllByPage", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取角色")
    public ApiRes getRoleByPage(@ModelAttribute PageVo<SysRole> pageVo) {
        Page<SysRole> list = sysRoleService.selectPage(pageVo.initPage(), new EntityWrapper<SysRole>());
        for (SysRole role : list.getRecords()) {
            List<SysPermission> permissions = sysPermissionService.findByRoleId(role.getId());
            role.setPermissions(permissions);
        }
        return ApiRes.Custom().success().addData(list);
    }

    @RequestMapping(value = "/delAllByIds", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过ids删除")
    public ApiRes delByIds(@RequestParam Long[] ids) {
        for (Long id : ids) {
            List<SysUserRole> list = sysUserRoleService.findByRoleId(id);
            if (list != null && list.size() > 0) {
                return ApiRes.Custom().failure("删除失败，包含正被使用中的角色");
            }
        }
        sysRoleService.deleteBatchIds(Arrays.stream(ids).collect(Collectors.toList()));
        return ApiRes.Custom().success("批量通过id删除数据成功");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "更新数据")
    @Log(value = "更新用户角色")
    public ApiRes edit(@ModelAttribute SysRoleVo roleVo) {
        if (sysRoleService.selectById(roleVo.getId()) == null) {
            return ApiRes.Custom().failure("无法找到该资源");
        }
        SysRole sysRole = beanMapper.map(roleVo, SysRole.class);
        sysRoleService.update(sysRole, new EntityWrapper<SysRole>().eq("id", roleVo.getId()));
        //手动批量删除缓存
        Set<String> keysUser = redisTemplate.keys("user:" + "*");
        redisTemplate.delete(keysUser);
        Set<String> keysUserRole = redisTemplate.keys("userRole:" + "*");
        redisTemplate.delete(keysUserRole);
        return ApiRes.Custom().success();
    }

    @RequestMapping(value = "/editRolePerm/{roleId}", method = RequestMethod.POST)
    @ApiOperation(value = "编辑角色分配权限")
    public ApiRes editRolePerm(@PathVariable Long roleId,
                               @RequestParam(required = false) Long[] permIds) {

        //删除其关联权限
        sysPrivilegeService.deleteByRoleId(roleId);
        //分配新权限
        for (Long permId : permIds) {
            SysPrivilege rolePermission = new SysPrivilege();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permId);
            sysPrivilegeService.insert(rolePermission);
        }
        //手动批量删除缓存
        Set<String> keysUser = redisTemplate.keys("user:" + "*");
        redisTemplate.delete(keysUser);
        Set<String> keysUserRole = redisTemplate.keys("userRole:" + "*");
        redisTemplate.delete(keysUserRole);
        Set<String> keysUserPerm = redisTemplate.keys("userPermission:" + "*");
        redisTemplate.delete(keysUserPerm);
        Set<String> keysUserMenu = redisTemplate.keys("permission::userMenuList:*");
        redisTemplate.delete(keysUserMenu);
        return ApiRes.Custom().success();
    }
}
