package cn.wind.xboot.controller;

import cn.wind.common.res.ApiRes;
import cn.wind.common.utils.PasswordUtil;
import cn.wind.common.utils.UserUtil;
import cn.wind.db.sys.entity.SysRole;
import cn.wind.db.sys.entity.SysUser;
import cn.wind.db.sys.entity.SysUserInfo;
import cn.wind.xboot.service.UserManage;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.SearchVo;
import cn.wind.xboot.vo.sys.SysUserVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * <p>用户管理接口</p>
 * Time:10:43
 * @author xukk
 */
@Slf4j
@Api(value = "user", tags = "用户管理模块")
@RestController
@Validated
@RequestMapping("user")
@CacheConfig(cacheNames = "user")
public class UserController extends BaseController{
    @Autowired
    private UserManage userManage;

    @Override
    public IService getService() {
        return null;
    }

    @Override
    public String getBase() {
        return null;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder){
        webDataBinder.setDisallowedFields("userInfo");
    }


    @RequestMapping()
    public Principal user(@RequestHeader("authorization") String authorization, Principal user) {
        return user;
    }


    @RequestMapping(value = "/info",method = RequestMethod.GET)
    @ApiOperation(value = "获取当前登录用户接口")
    public ApiRes getUserInfo(){
        SysUser sysUser= sysUserService.findUserInfoById(UserUtil.getCurrentUserId());
        SysUserVo sysUserVo=beanMapper.map(sysUser, SysUserVo.class);
        return ApiRes.Custom().success().addData(sysUserVo);
    }
    @RequestMapping(value = "/getByCondition",method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取用户列表")
    public ApiRes getByCondition(@ModelAttribute SysUserVo userVo,
                                             @ModelAttribute SearchVo searchVo,
                                             @ModelAttribute PageVo pageVo){
        Map<String,Object> map= Maps.newHashMap();
        map.putAll(beanMapper.map(searchVo,Map.class));
        map.putAll(beanMapper.map(userVo,Map.class));
        Page<SysUser> page = sysUserService.findByCondition(pageVo.initPage(),map);
        for(SysUser u: page.getRecords()){
            List<SysRole> list = sysRoleService.findByUserId(u.getId());
            u.setRoles(list);
            u.setPassword(null);
        }
        return ApiRes.Custom().success().addData(page);
    }
    @RequestMapping(value = "/admin/disable/{userId}",method = RequestMethod.POST)
    @ApiOperation(value = "后台禁用用户")
    public ApiRes disable(@ApiParam("用户唯一id标识") @PathVariable Long userId){

        SysUser user= sysUserService.selectById(userId);
        if(user==null){
            return ApiRes.Custom().failure("通过userId获取用户失败");
        }
        user.setEnabled(false);
        sysUserService.updateById(user);
        //手动更新缓存
        redisTemplate.delete("user::"+user.getUsername());
        return ApiRes.Custom().success();
    }
    @RequestMapping(value = "/admin/enable/{userId}",method = RequestMethod.POST)
    @ApiOperation(value = "后台启用用户")
    public ApiRes enable(@ApiParam("用户唯一id标识") @PathVariable Long userId){
        SysUser user= sysUserService.selectById(userId);
        if(user==null){
            return ApiRes.Custom().failure("通过userId获取用户失败");
        }
        user.setEnabled(true);
        sysUserService.updateById(user);
        //手动更新缓存
        redisTemplate.delete("user::"+user.getUsername());
        return ApiRes.Custom().success();
    }
    /**
     * @param sysUserVo
     * @param roles
     * @return
     */
    @RequestMapping(value = "/admin/edit",method = RequestMethod.POST)
    @ApiOperation(value = "修改资料",notes = "需要通过id获取原用户信息 需要username更新缓存")
    @CacheEvict(key = "#sysUserVo.username")
    public ApiRes edit(@ModelAttribute SysUserVo sysUserVo,
                               @RequestParam(required = false) Long[] roles){
        return userManage.edit(sysUserVo,roles);
    }

    @RequestMapping(value = "/admin/add",method = RequestMethod.POST)
    @ApiOperation(value = "添加用户")
    public ApiRes regist(@ModelAttribute SysUserVo sysUserVo,
                                 @RequestParam(required = false) Long[] roles){

        return userManage.regist(sysUserVo,roles);
    }
    @RequestMapping(value = "/delByIds",method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过ids删除")
    public ApiRes delAllByIds(@RequestParam Long[] ids){
        return userManage.delAllByIds(ids);
    }

    @RequestMapping(value = "/unlock",method = RequestMethod.POST)
    @ApiOperation(value = "解锁验证密码")
    public ApiRes unLock(@RequestParam String password){

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser u = sysUserService.findByUsername(user.getUsername());
        if(!new BCryptPasswordEncoder().matches(password, u.getPassword())){
            return ApiRes.Custom().failure("密码不正确");
        }
        return ApiRes.Custom().success();
    }


    /**
     * 线上demo仅允许ADMIN权限改密码
     * @param id
     * @param password
     * @param newPass
     * @return
     */
    @RequestMapping(value = "/modifyPass",method = RequestMethod.POST)
    @ApiOperation(value = "修改密码")
    public ApiRes modifyPass(@ApiParam("需用户id获取原用户数据") @RequestParam Long id,
                                     @ApiParam("password") @RequestParam String password,
                                     @ApiParam("新密码") @RequestParam String newPass){
        SysUser old = sysUserService.selectById(id);
        if(!PasswordUtil.matches(password,old.getPassword())){
            return ApiRes.Custom().failure("旧密码不正确");
        }
        String newEncryptPass= new BCryptPasswordEncoder().encode(newPass);
        if(!sysUserService.updatePasswordById(newEncryptPass,old.getId())){
            return ApiRes.Custom().failure("修改失败");
        }

        //手动更新缓存
        redisTemplate.delete("user::"+old.getUsername());

        return ApiRes.Custom().success();
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ApiOperation(value = "修改用户自己资料",notes = "用户名密码不会修改 需要通过id获取原用户信息 需要username更新缓存")
    @CacheEvict(key = "#u.username")
    public ApiRes editOwn(@ModelAttribute SysUserVo u){
        u.init();
        SysUser sysUser=beanMapper.map(u,SysUser.class);
        if(!sysUserInfoService.update(sysUser.getUserInfo(),new EntityWrapper<SysUserInfo>().eq("id",sysUser.getUserInfo().getId()))){
            return ApiRes.Custom().failure("修改失败");
        }
        return ApiRes.Custom().success("修改成功");
    }
}
