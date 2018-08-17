package cn.wind.xboot.controller.web.userAudit;

import cn.wind.common.res.ApiRes;
import cn.wind.db.ar.entity.ArUser;
import cn.wind.db.ar.service.IArUserService;
import cn.wind.xboot.controller.BaseController;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.SearchVo;
import cn.wind.xboot.vo.web.ar.AppArUserVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/8/16 20:11
 * @Description:
 */
@Api(value = "后台用户管理",tags = "后台用户管理")
@RestController
@RequestMapping("/userAudit")
public class WebUserAuditController extends BaseController {

    @Autowired
    private IArUserService userService;

    @Override
    public IService getService() {
        return null;
    }

    @Override
    public String getBase() {
        return null;
    }

    @ApiOperation(value = "多条件分页获取APP用户列表")
    @GetMapping(value = "/getByCondition")
    public ApiRes getByCondition(@ModelAttribute AppArUserVo userVo,
                                 @ModelAttribute SearchVo searchVo,
                                 @ModelAttribute PageVo pageVo){
        Map<String,Object> map= Maps.newHashMap();
        map.putAll(beanMapper.map(searchVo,Map.class));
        map.putAll(beanMapper.map(userVo,Map.class));
        Page<ArUser> page = userService.findAllByConditions(pageVo.initPage(),map);
        return ApiRes.Custom().success().addData(page);
    }
}
