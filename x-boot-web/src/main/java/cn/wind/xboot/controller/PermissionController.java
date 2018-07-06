package cn.wind.xboot.controller;

import cn.wind.common.constant.ApiConstant;
import cn.wind.common.res.ApiRes;
import cn.wind.db.sys.entity.SysPermission;
import cn.wind.db.sys.entity.SysPrivilege;
import cn.wind.xboot.enums.PermType;
import cn.wind.xboot.vo.sys.SysPermissionVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * <p>Title: PermissionController</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/22
 */
@Slf4j
@RestController
@Api(value = "permission",tags = "菜单/权限管理接口")
@RequestMapping("/permission")
@CacheConfig(cacheNames = "permission")
public class PermissionController extends BaseController<SysPermission,Long> {
    @Override
    public IService getService() {
        return sysPermissionService;
    }

    @Override
    public String getBase() {
        return null;
    }

    @RequestMapping(value = "/getMenuList/{userId}",method = RequestMethod.GET)
    @ApiOperation(value = "获取用户页面菜单数据")
    @Cacheable(key = "'userMenuList:'+#userId")
    public ApiRes getAllMenuList(@PathVariable Long userId){
        //用户所有权限 已排序去重
        List<SysPermission> list = sysPermissionService.findByUserId(userId);

        List<SysPermission> menuList = new ArrayList<>();
        //筛选一级页面
        for(SysPermission p : list){
            if(PermType.MENU.getValue().equals(p.getType())&& ApiConstant.LEVEL_ONE.equals(p.getLevel())){
                menuList.add(p);
            }
        }
        //筛选二级页面
        List<SysPermission> secondMenuList = new ArrayList<>();
        for(SysPermission p : list){
            if(PermType.MENU.getValue().equals(p.getType())&&ApiConstant.LEVEL_TWO.equals(p.getLevel())){
                secondMenuList.add(p);
            }
        }
        //筛选二级页面拥有的按钮权限
        List<SysPermission> buttonPermissions = new ArrayList<>();
        for(SysPermission p : list){
            if(PermType.BUTTON.getValue().equals(p.getType())&&ApiConstant.LEVEL_THREE.equals(p.getLevel())){
                buttonPermissions.add(p);
            }
        }

        //匹配二级页面拥有权限
        for(SysPermission p : secondMenuList){
            List<String> buttonTypes = new ArrayList<>();
            for(SysPermission pe : buttonPermissions){
                if(p.getId().equals(pe.getPId())){
                    buttonTypes.add(pe.getButtonType());
                }
            }
            p.setButtonTypes(buttonTypes);
        }
        //匹配一级页面拥有二级页面
        for(SysPermission p : menuList){
            List<SysPermission> secondMenu = new ArrayList<>();
            for(SysPermission pe : secondMenuList){
                if(p.getId().equals(pe.getPId())){
                    secondMenu.add(pe);
                }
            }
            p.setChildren(secondMenu);
        }
        return ApiRes.Custom().success().addData(menuList);
    }

    @RequestMapping(value = "/getAllList",method = RequestMethod.GET)
    @ApiOperation(value = "获取权限菜单树")
    @Cacheable(key = "'allList'")
    public ApiRes getAllList(){
        //一级
        List<SysPermission> list = sysPermissionService.findByLevelOrderBySort(ApiConstant.LEVEL_ONE);
        //二级
        for(SysPermission p1 : list){
            List<SysPermission> children1 =sysPermissionService.findByParentIdOrderBySort(p1.getId());
            p1.setChildren(children1);
            //三级
            for(SysPermission p2 : children1){
                List<SysPermission> children2 =sysPermissionService.findByParentIdOrderBySort(p2.getId());
                p2.setChildren(children2);
            }
        }
        return ApiRes.Custom().success().addData(list);
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ApiOperation(value = "添加")
    @CacheEvict(key = "'menuList'")
    public ApiRes add(@ModelAttribute SysPermissionVo permissionVo){
        SysPermission sysPermission= beanMapper.map(permissionVo,SysPermission.class);
        boolean flag=sysPermissionService.insert(sysPermission);
        //手动删除缓存
        redisTemplate.delete("permission::allList");
        return ApiRes.Custom(flag).addData(sysPermission);
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ApiOperation(value = "编辑")
    public ApiRes edit(@ModelAttribute SysPermissionVo permissionVo){
        SysPermission sysPermission=sysPermissionService.selectById(permissionVo.getId());
        if(sysPermission==null)
        {
            return ApiRes.Custom().failure("无法找到该资源");
        }
        SysPermission sysPermission1=beanMapper.map(permissionVo,SysPermission.class);
        boolean flag=sysPermissionService.update(sysPermission1,new EntityWrapper<SysPermission>().eq("id",sysPermission1.getId()));
        if(flag){
            //手动批量删除缓存
            Set<String> keys = redisTemplate.keys("userPermission:" + "*");
            redisTemplate.delete(keys);
            Set<String> keysUser = redisTemplate.keys("user:" + "*");
            redisTemplate.delete(keysUser);
            Set<String> keysUserMenu = redisTemplate.keys("permission::userMenuList:*");
            redisTemplate.delete(keysUserMenu);
            redisTemplate.delete("permission::allList");
        }
        return ApiRes.Custom(flag);
    }
    @RequestMapping(value = "/delByIds",method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过id删除")
    @CacheEvict(key = "'menuList'")
    public ApiRes delByIds(@RequestParam Long[] ids){
        for(Long id:ids){
            List<SysPrivilege> list = sysPrivilegeService.findByPermissionId(id);
            if(list!=null&&list.size()>0){
                return ApiRes.Custom().failure("删除失败，包含正被使用中的菜单或权限");
            }
        }
        for(Long id:ids){
            sysPermissionService.deleteById(id);
        }
        //手动删除缓存
        redisTemplate.delete("permission::allList");
        return ApiRes.Custom().success("批量通过id删除数据成功");
    }
}
