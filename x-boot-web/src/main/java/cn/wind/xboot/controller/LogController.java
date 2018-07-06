package cn.wind.xboot.controller;

import cn.wind.common.res.ApiRes;
import cn.wind.db.sys.entity.SysLog;
import cn.wind.db.sys.service.ISysLogService;
import cn.wind.xboot.vo.PageVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;
import java.util.stream.Collectors;


/**
 * 拥有ROLE_ADMIN角色的用户可以访问
 * @author Exrickx
 */
@Slf4j
@RestController
@Api(value = "log",tags= "日志管理接口")
@RequestMapping("log")
@PreAuthorize("hasRole('LOG-MANAGE')")
public class LogController {

    @Autowired
    private ISysLogService sysLogService;

    @RequestMapping(value = "/getAllByPage",method = RequestMethod.GET)
    @ApiOperation(value = "分页获取全部")
    public ApiRes getAllByPage(Principal principal,@ModelAttribute PageVo<SysLog> pageVo){
            Page<SysLog> log = sysLogService.selectPage(pageVo.initPage());
            return ApiRes.Custom().success().addData(log);
    }

    @RequestMapping(value = "/search",method = RequestMethod.GET)
    @ApiOperation(value = "分页搜索")
    public ApiRes search(@RequestParam String key, @ModelAttribute PageVo<SysLog> pageVo){
        EntityWrapper<SysLog> ew=new EntityWrapper<>();
        ew.like("request_url",key).or().like("request_type",key)
                .or().like("request_param",key)
                .or().like("username",key)
                .or().like("ip",key)
                .or().like("ip_info",key)
                .or().like("name",key);
            Page<SysLog> log = sysLogService.selectPage(pageVo.initPage(),ew);
            return ApiRes.Custom().success().addData(log);
    }

    @RequestMapping(value = "/delByIds",method = RequestMethod.DELETE)
    @ApiOperation(value = "批量删除")
    public ApiRes delByIds( @RequestParam Long[] ids){
        boolean flag=sysLogService.deleteBatchIds(Arrays.stream(ids).collect(Collectors.toList()));
        return ApiRes.Custom(flag,"删除成功","删除失败");
    }


    /**
     * 删除全部
     * @return
     */
    @RequestMapping(value = "/delAll",method = RequestMethod.DELETE)
    @ApiOperation(value = "全部删除")
    public ApiRes delAll(){
        boolean flag=sysLogService.delete(new EntityWrapper<SysLog>());
        return ApiRes.Custom(flag,"删除成功","删除失败");
    }
}
