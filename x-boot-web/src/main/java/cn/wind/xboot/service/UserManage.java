package cn.wind.xboot.service;

import cn.hutool.core.util.StrUtil;
import cn.wind.common.res.ApiRes;
import cn.wind.common.utils.BlankUtil;
import cn.wind.db.sys.entity.SysUser;
import cn.wind.db.sys.entity.SysUserInfo;
import cn.wind.db.sys.entity.SysUserRole;
import cn.wind.db.sys.service.*;
import cn.wind.xboot.vo.sys.SysUserVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>Title: UserManage</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/26
 */
@Service
public class UserManage {
    @Autowired
    protected DozerBeanMapper beanMapper;
    @Autowired
    protected  StringRedisTemplate redisTemplate;
    @Autowired
    protected ISysRoleService sysRoleService;
    @Autowired
    protected ISysPermissionService sysPermissionService;
    @Autowired
    protected ISysUserService sysUserService;
    @Autowired
    protected ISysUserRoleService sysUserRoleService;
    @Autowired
    protected ISysUserInfoService sysUserInfoService;

    @Transactional
    public ApiRes edit(SysUserVo sysUserVo, Long[] roles){
        sysUserVo.init();
        SysUser sysUser=beanMapper.map(sysUserVo,SysUser.class);
        SysUser old = sysUserService.findUserInfoById(sysUserVo.getId());
        //所修改了用户名
        if(!old.getUsername().equals(sysUserVo.getUsername())){
            //若修改用户名删除原用户名缓存
            redisTemplate.delete("user::"+old.getUsername());
            //判断新用户名是否存在
            if(sysUserService.findByUsername(sysUserVo.getUsername())!=null){
                return ApiRes.Custom().failure("该用户名已被存在");
            }
            //删除缓存
            redisTemplate.delete("user::"+sysUserVo.getUsername());
        }
        if(!StringUtils.equalsIgnoreCase(old.getUserInfo().getUserNo(),sysUserVo.getUserNo())){
            if(BlankUtil.isNotBlank(sysUserInfoService.selectList(new EntityWrapper<SysUserInfo>().eq("user_no",sysUserVo.getUserNo())))){
                return ApiRes.Custom().failure("该用户编号已存在");
            }
        }
        if(!StringUtils.equalsIgnoreCase(old.getUserInfo().getMobile(),sysUserVo.getMobile())){
            if(BlankUtil.isNotBlank(sysUserInfoService.selectList(new EntityWrapper<SysUserInfo>().eq("mobile",sysUserVo.getMobile())))){
                return ApiRes.Custom().failure("该手机号已存在");
            }
        }

        if(!sysUserService.updateById(sysUser)){
            return ApiRes.Custom().failure("修改失败");
        }
        if(!sysUserInfoService.updateById(sysUser.getUserInfo())){
            return ApiRes.Custom().failure("修改失败");
        }

        //删除该用户角色

        sysUserRoleService.delete(new EntityWrapper<SysUserRole>().eq("user_id",sysUser.getId()));

        if(roles!=null&&roles.length>0){
            //新角色
            for(Long roleId : roles){
                SysUserRole ur = new SysUserRole();
                ur.setRoleId(roleId);
                ur.setUserId(sysUser.getId());
                sysUserRoleService.insert(ur);
            }
        }
        //手动删除缓存
        redisTemplate.delete("userRole::"+sysUser.getId());
        return ApiRes.Custom().success("修改成功");
    }
    @Transactional
    public ApiRes regist(@ModelAttribute SysUserVo sysUserVo,
                         @RequestParam(required = false) Long[] roles){

        if(StrUtil.isBlank(sysUserVo.getUsername()) || StrUtil.isBlank(sysUserVo.getPassword())){
            return ApiRes.Custom().failure("缺少必需表单字段");
        }

        if(sysUserService.findByUsername(sysUserVo.getUsername())!=null){
            return ApiRes.Custom().failure("该用户名已被注册");
        }
        if(BlankUtil.isNotBlank(sysUserInfoService.selectList(new EntityWrapper<SysUserInfo>().eq("user_no",sysUserVo.getUserNo())))){
            return ApiRes.Custom().failure("该用户编号已存在");
        }
        if(BlankUtil.isNotBlank(sysUserInfoService.selectList(new EntityWrapper<SysUserInfo>().eq("mobile",sysUserVo.getMobile())))){
            return ApiRes.Custom().failure("该手机号已存在");
        }
        //删除缓存
        redisTemplate.delete("user::"+sysUserVo.getUsername());

        String encryptPass = new BCryptPasswordEncoder().encode(sysUserVo.getPassword());
        sysUserVo.setPassword(encryptPass);

        sysUserVo.init();
        SysUser sysUser=beanMapper.map(sysUserVo,SysUser.class);
        if(!sysUserService.insert(sysUser)){
            return ApiRes.Custom().failure("添加失败");
        }
        sysUser.getUserInfo().setUserId(sysUser.getId());
        if(StringUtils.isBlank(sysUser.getUserInfo().getUserNo())){
            sysUser.getUserInfo().setUserNo(System.currentTimeMillis()+"");
        }
        if(!sysUserInfoService.insert(sysUser.getUserInfo())){
            return ApiRes.Custom().failure("添加失败");
        }
        if(roles!=null&&roles.length>0){
            //添加角色
            for(Long roleId : roles){
                SysUserRole ur = new SysUserRole();
                ur.setUserId(sysUser.getId());
                ur.setRoleId(roleId);
                sysUserRoleService.insert(ur);
            }
        }

        return ApiRes.Custom().success();
    }
    @Transactional
    public ApiRes delAllByIds(Long[] ids){
        for(Long id:ids){
            sysUserService.deleteById(id);
        }
        return ApiRes.Custom().success("批量通过id删除数据成功");
    }
}
