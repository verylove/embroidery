package cn.wind.xboot.controller.web.homePage;

import cn.wind.common.res.ApiRes;
import cn.wind.db.ar.entity.ArUserSpEvaluates;
import cn.wind.db.ar.entity.ArUserSpPic;
import cn.wind.db.ar.entity.ArUserSpTattoo;
import cn.wind.db.ar.service.IArUserSpEvaluatesService;
import cn.wind.db.ar.service.IArUserSpPicService;
import cn.wind.db.ar.service.IArUserSpTattooService;
import cn.wind.db.sr.entity.SrArea;
import cn.wind.db.sr.service.ISrAreaService;
import cn.wind.xboot.controller.BaseController;
import cn.wind.xboot.service.web.WebSpTattooManage;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.SearchVo;
import cn.wind.xboot.vo.web.ar.spTattoo.AppArSpTattooVo;
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
 * @Date: 2018/9/3 15:11
 * @Description:
 */
@Api(value = "特价纹身管理",tags = "特价纹身管理")
@RestController
@RequestMapping("/webSys/spTattoo")
public class WebSpecialTattooController extends BaseController {

    @Autowired
    private IArUserSpTattooService spTattooService;
    @Autowired
    private ISrAreaService areaService;
    @Autowired
    private IArUserSpPicService picService;
    @Autowired
    private WebSpTattooManage spTattooManage;
    @Autowired
    private IArUserSpEvaluatesService spEvaluatesService;

    @Override
    public IService getService() {
        return null;
    }

    @Override
    public String getBase() {
        return null;
    }

    @ApiOperation(value = "分页获取特价纹身列表")
    @GetMapping(value = "/getAllSpTattooByCondition")
    public ApiRes getAllSpTattooByCondition(@ModelAttribute AppArSpTattooVo spTattooVo,
                                            @ModelAttribute SearchVo searchVo,
                                            @ModelAttribute PageVo pageVo){
        Map<String,Object> map= Maps.newHashMap();
        map.putAll(beanMapper.map(searchVo,Map.class));
        map.putAll(beanMapper.map(spTattooVo,Map.class));

        Page<ArUserSpTattoo> page = spTattooService.findAll(pageVo.initPage(),map);

        Page<AppArSpTattooVo> voPage = new Page<>(page.getCurrent(),page.getSize());
        voPage.setTotal(page.getTotal());

        if(page.getRecords() == null || page.getRecords().size()<1){
            return ApiRes.Custom().success().addData(voPage);
        }

        List<Long> cityIds = page.getRecords().stream().map(ArUserSpTattoo::getCity).collect(Collectors.toList());

        List<SrArea> areas = areaService.findAllByIdsIn(cityIds);
        Map<Long,String> areaMap = areas.stream().collect(Collectors.toMap(SrArea::getId, SrArea::getName));

        List<AppArSpTattooVo> vos = Lists.newArrayList();
        for(ArUserSpTattoo spTattoo:page.getRecords()){
            AppArSpTattooVo vo = new AppArSpTattooVo();
            BeanUtils.copyProperties(spTattoo,vo);
            vo.setAccount(spTattoo.getUser().getAccount());
            vo.setCityName(areaMap.get(spTattoo.getCity()));
            List<ArUserSpPic> pics = picService.findAllByTattoId(spTattoo.getId());
            vo.setPics(pics);
            vos.add(vo);
        }
        voPage.setRecords(vos);
        return ApiRes.Custom().success().addData(voPage);
    }

    @ApiOperation(value = "特价纹身详情")
    @GetMapping(value = "/spTattooDetail")
    public ApiRes spTattooDetail(Long spTattooId){
        return spTattooManage.spTattooDetail(spTattooId);
    }

    @ApiOperation(value = "特价纹身评论")
    @GetMapping(value = "/spTattooEvaluates")
    public ApiRes spTattooEvaluates(Long spTattooId, @ModelAttribute PageVo pageVo){
        Map<String,Object> map= Maps.newHashMap();
        map.put("spTattooId",spTattooId);
        map.put("type",1);
        Page<ArUserSpEvaluates> page = spEvaluatesService.findAllByConditions(pageVo.initPage(),map);

        return ApiRes.Custom().success().addData(page);
    }

    @ApiOperation(value = "特价纹身删除")
    @PostMapping(value = "/spTattooDelete")
    public ApiRes spTattooDelete(Long spTattooId){
        return spTattooManage.spTattooDelete(spTattooId);
    }

    @ApiOperation(value = "特价纹身评价删除")
    @PostMapping(value = "/spTattooEvaluateDelete")
    public ApiRes spTattooEvaluateDelete(Long spEvaluateId){
        return spTattooManage.spTattooEvaluateDelete(spEvaluateId);
    }
}
