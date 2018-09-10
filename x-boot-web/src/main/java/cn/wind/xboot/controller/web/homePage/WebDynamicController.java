package cn.wind.xboot.controller.web.homePage;

import cn.wind.common.res.ApiRes;
import cn.wind.db.ar.entity.ArUserDyEvaluates;
import cn.wind.db.ar.entity.ArUserDyPic;
import cn.wind.db.ar.entity.ArUserDyWorks;
import cn.wind.db.ar.service.IArUserDyEvaluatesService;
import cn.wind.db.ar.service.IArUserDyPicService;
import cn.wind.db.ar.service.IArUserDyWorksService;
import cn.wind.db.sr.entity.SrArea;
import cn.wind.db.sr.service.ISrAreaService;
import cn.wind.xboot.controller.BaseController;
import cn.wind.xboot.service.web.WebDynamicWorkManage;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.SearchVo;
import cn.wind.xboot.vo.web.ar.dyWorks.AppArDyWorkVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/8 18:32
 * @Description:
 */
@Api(value = "圈子管理",tags = "圈子管理")
@RestController
@RequestMapping("/webSys/dynamic")
public class WebDynamicController extends BaseController {

    @Autowired
    private IArUserDyWorksService dyWorksService;
    @Autowired
    private ISrAreaService areaService;
    @Autowired
    private IArUserDyPicService picService;
    @Autowired
    private IArUserDyEvaluatesService dyEvaluatesService;
    @Autowired
    private WebDynamicWorkManage dynamicWorkManage;

    @Override
    public IService getService() {
        return null;
    }

    @Override
    public String getBase() {
        return null;
    }

    @ApiOperation(value = "分页获取圈子数据")
    @GetMapping("/getAllCircleByCondition")
    public ApiRes getAllCircleByCondition(@ModelAttribute AppArDyWorkVo dyWorkVo,
                                          @ModelAttribute SearchVo searchVo,
                                          @ModelAttribute PageVo pageVo){
        Map<String,Object> map = Maps.newHashMap();
        map.putAll(beanMapper.map(dyWorkVo,Map.class));
        map.putAll(beanMapper.map(searchVo,Map.class));

        Page<ArUserDyWorks> page = dyWorksService.findAllDyByCondition(pageVo.initPage(),map);

        Page<AppArDyWorkVo> voPage = new Page<>(page.getCurrent(),page.getSize());
        voPage.setTotal(page.getTotal());

        if(page.getRecords()==null || page.getRecords().size()<1){
            return ApiRes.Custom().success().addData(voPage);
        }

        List<Long> cityIds = page.getRecords().stream().map(ArUserDyWorks::getCity).collect(Collectors.toList());

        List<SrArea> areas = areaService.findAllByIdsIn(cityIds);
        Map<Long,String> areaMap = areas.stream().collect(Collectors.toMap(SrArea::getId, SrArea::getName));

        List<AppArDyWorkVo> vos = Lists.newArrayList();
        for(ArUserDyWorks dyWorks:page.getRecords()){
            AppArDyWorkVo vo = new AppArDyWorkVo();
            BeanUtils.copyProperties(dyWorks,vo);
            vo.setCityName(areaMap.get(dyWorks.getCity()));
            vo.setAccount(dyWorks.getUser().getAccount());
            List<ArUserDyPic> pics = picService.findAllByDyWorksId(dyWorks.getId());
            vo.setPics(pics);
            vos.add(vo);
        }
        voPage.setRecords(vos);
        return ApiRes.Custom().success().addData(voPage);
    }

    @ApiOperation(value = "圈子动态详情")
    @GetMapping("/circleDetail")
    public ApiRes circleDetail(Long dyWorksId){
        Map<String,Object> map = Maps.newHashMap();
        map.put("dyWorksId",dyWorksId);
        ArUserDyWorks dyWorks = dyWorksService.findOneByConditions(map);

        if(dyWorks == null){
            return ApiRes.Custom().success().failure("该圈子数据不存在");
        }

        AppArDyWorkVo vo = new AppArDyWorkVo();
        BeanUtils.copyProperties(dyWorks,vo);
        SrArea area = areaService.selectById(dyWorks.getCity());
        vo.setCityName(area.getName());
        vo.setAccount(dyWorks.getUser().getAccount());
        List<ArUserDyPic> pics = picService.findAllByDyWorksId(dyWorks.getId());
        vo.setPics(pics);
        return ApiRes.Custom().success().addData(vo);
    }

    @ApiOperation(value = "圈子动态评论")
    @GetMapping(value = "/circleEvaluates")
    public ApiRes circleEvaluates(Long dyWorksId, @ModelAttribute PageVo pageVo){
        Map<String,Object> map= Maps.newHashMap();
        map.put("dyWorksId",dyWorksId);
        map.put("type",1);
        Page<ArUserDyEvaluates> page = dyEvaluatesService.findAllByConditions(pageVo.initPage(),map);

        return ApiRes.Custom().success().addData(page);
    }

    @ApiOperation(value = "圈子动态删除")
    @DeleteMapping("/circleDelete")
    public ApiRes circleDelete(Long dyWorksId){
        return dynamicWorkManage.circleDelete(dyWorksId);
    }

    @ApiOperation(value = "圈子动态评论删除")
    @DeleteMapping("/circleEvaluatesDelete")
    public ApiRes circleEvaluatesDelete(Long dyEvaluateId){
        return dynamicWorkManage.circleEvaluatesDelete(dyEvaluateId);
    }
}
