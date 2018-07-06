package cn.wind.xboot.controller;

import cn.wind.common.res.ApiRes;
import cn.wind.db.pf.entity.PfNotice;
import cn.wind.db.sys.entity.SysPermission;
import cn.wind.db.sys.entity.SysRole;
import cn.wind.db.sys.entity.SysUserRole;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.pf.PfNoticeVo;
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
import java.util.stream.Collectors;

/**
 * <p>Title: NoticeController</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/7/2
 */
@Slf4j
@RestController
@Api(value = "notice",tags = "站点消息管理")
@RequestMapping("notice")
public class NoticeController extends BaseController<PfNotice, Long>{
    @Override
    public IService getService() {
        return pfNoticeService;
    }
    @Override
    public String getBase() {
        return PfNotice.class.getPackage().getName();
    }

    @RequestMapping(value = "/getAllByPage", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取站内消息")
    public ApiRes getNoticeByPage(@ModelAttribute PageVo<PfNotice> pageVo, PfNoticeVo pfNoticeVo) {
        EntityWrapper<PfNotice> ew=new EntityWrapper<PfNotice>();
        if(pfNoticeVo.getReaded()!=null){
            ew.eq("readed",pfNoticeVo.getReaded());
        }
        Page<PfNotice> list =pfNoticeService.selectPage(pageVo.initPage(),ew);
        return ApiRes.Custom().success().addData(list);
    }
    @RequestMapping(value = "/getCount", method = RequestMethod.GET)
    @ApiOperation(value = "统计站内消息")
    public ApiRes getCount() {
        EntityWrapper<PfNotice> ew=new EntityWrapper<>();
        ew.eq("readed",0);
        EntityWrapper<PfNotice> ew1=new EntityWrapper<>();
        ew1.eq("readed",1);
        Integer unreadCount=pfNoticeService.selectCount(ew);
        Integer hasreadCount =pfNoticeService.selectCount(ew1);
        Integer recyclebinCount=pfNoticeService.countByDelFlag(1);
        return ApiRes.Map().success().put("unreadCount",unreadCount).put("hasreadCount",hasreadCount).put("recyclebinCount",recyclebinCount);
    }
    @RequestMapping(value = "/getAllByDelFlag", method = RequestMethod.GET)
    @ApiOperation(value = "分页获取站内消息")
    public ApiRes getNoticeByPage(@ModelAttribute PageVo<PfNotice> pageVo, Integer delFlag) {
        Page<PfNotice> list =pfNoticeService.findByDelFlag(pageVo.initPage(),delFlag);
        return ApiRes.Custom().success().addData(list);
    }

    @RequestMapping(value = "/delAllByIds", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过ids删除")
    public ApiRes delByIds(@RequestParam Long[] ids) {
        pfNoticeService.deleteBatchIds(Arrays.stream(ids).collect(Collectors.toList()));
        return ApiRes.Custom().success("批量通过id删除数据成功");
    }
    @RequestMapping(value = "/read", method = RequestMethod.POST)
    @ApiOperation(value = "批量通过ids标记已读")
    public ApiRes read(@RequestParam Long[] ids) {
        pfNoticeService.updateReadedByIds(Arrays.stream(ids).collect(Collectors.toList()));
        return ApiRes.Custom().success("标记已读成功");
    }
    @RequestMapping(value = "/resume", method = RequestMethod.POST)
    @ApiOperation(value = "批量通过ids标记已读")
    public ApiRes resume(@RequestParam Long[] ids) {
        pfNoticeService.updateDelFlagByIds(Arrays.stream(ids).collect(Collectors.toList()));
        return ApiRes.Custom().success("还原成功");
    }
}
