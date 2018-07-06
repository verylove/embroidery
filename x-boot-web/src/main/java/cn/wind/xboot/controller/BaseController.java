package cn.wind.xboot.controller;

import cn.wind.common.res.ApiRes;
import cn.wind.db.pf.service.IPfNoticeService;
import cn.wind.db.sys.service.*;
import com.baomidou.mybatisplus.service.IService;
import io.swagger.annotations.ApiOperation;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;

/**
 * <p>Title: BaseController</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/22
 */
public abstract class BaseController<E, ID extends Serializable> {
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
    @Autowired
    protected ISysPrivilegeService sysPrivilegeService;
    @Autowired
    protected IPfNoticeService pfNoticeService;
    public abstract IService getService();
    public abstract String getBase();
    @RequestMapping(value = "save",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存数据")
    @SuppressWarnings("unchecked")
    public ApiRes<E> save(@ModelAttribute E entity) throws Exception{
         Class c=Class.forName(getBase()+"."+entity.getClass().getSimpleName().substring(0,entity.getClass().getSimpleName().length()-2));
         getService().insert(beanMapper.map(entity,c));
        return ApiRes.Custom().success().addData(entity);
    }
}
