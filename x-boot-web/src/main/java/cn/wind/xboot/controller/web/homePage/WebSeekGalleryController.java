package cn.wind.xboot.controller.web.homePage;

import cn.wind.common.res.ApiRes;
import cn.wind.db.ar.entity.*;
import cn.wind.db.ar.service.IArUserSkEvaluatesService;
import cn.wind.db.ar.service.IArUserSkGalleryService;
import cn.wind.db.ar.service.IArUserSkLabelService;
import cn.wind.db.ar.service.IArUserSkPicService;
import cn.wind.db.sr.entity.SrArea;
import cn.wind.db.sr.service.ISrAreaService;
import cn.wind.xboot.controller.BaseController;
import cn.wind.xboot.service.web.WebSkGalleryManage;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.SearchVo;
import cn.wind.xboot.vo.web.ar.skGallery.AppArSkGalleryVo;
import cn.wind.xboot.vo.web.ar.skGallery.AppArSkLabelVo;
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
 * @Date: 2018/9/4 11:26
 * @Description:
 */
@Api(value = "图库找图管理",tags = "图库找图管理")
@RestController
@RequestMapping("/webSys/skGallery")
public class WebSeekGalleryController extends BaseController{

    @Autowired
    private IArUserSkGalleryService skGalleryService;
    @Autowired
    private ISrAreaService areaService;
    @Autowired
    private IArUserSkPicService picService;
    @Autowired
    private IArUserSkLabelService labelService;
    @Autowired
    private IArUserSkEvaluatesService skEvaluatesService;
    @Autowired
    private WebSkGalleryManage skGalleryManage;

    @Override
    public IService getService() {
        return null;
    }

    @Override
    public String getBase() {
        return null;
    }

    @ApiOperation(value = "图库找图分页列表")
    @GetMapping("/getAllSkGalleryByCondition")
    public ApiRes getAllSkGalleryByCondition(@ModelAttribute AppArSkGalleryVo skGalleryVo,
                                             @ModelAttribute SearchVo searchVo,
                                             @ModelAttribute PageVo pageVo){
        Map<String,Object> map = Maps.newHashMap();
        map.putAll(beanMapper.map(skGalleryVo,Map.class));
        map.putAll(beanMapper.map(searchVo,Map.class));

        Page<ArUserSkGallery> page = skGalleryService.findAllSkByCondition(pageVo.initPage(),map);

        Page<AppArSkGalleryVo> voPage = new Page<>(page.getCurrent(),page.getSize());
        voPage.setTotal(page.getTotal());

        if(page.getRecords() == null || page.getRecords().size()<1){
            return ApiRes.Custom().success().addData(voPage);
        }

        List<Long> cityIds = page.getRecords().stream().map(ArUserSkGallery::getCity).collect(Collectors.toList());

        List<SrArea> areas = areaService.findAllByIdsIn(cityIds);
        Map<Long,String> areaMap = areas.stream().collect(Collectors.toMap(SrArea::getId, SrArea::getName));

        List<Long> labelIds = page.getRecords().stream().map(ArUserSkGallery::getLabel).collect(Collectors.toList());

        List<ArUserSkLabel> labelList = labelService.findAllByIdsIn(labelIds);
        Map<Long,String> labels = labelList.stream().collect(Collectors.toMap(ArUserSkLabel::getId,ArUserSkLabel::getName));

        List<AppArSkGalleryVo> vos = Lists.newArrayList();
        for(ArUserSkGallery skGallery:page.getRecords()){
            AppArSkGalleryVo vo = new AppArSkGalleryVo();
            BeanUtils.copyProperties(skGallery,vo);
            vo.setAccount(skGallery.getUser().getAccount());
            vo.setCityName(areaMap.get(skGallery.getCity()));
            List<ArUserSkPic> pics = picService.findAllByGalleryId(skGallery.getId());
            vo.setPics(pics);
            vo.setLabelName(labels.get(skGallery.getLabel()));
            vos.add(vo);
        }
        voPage.setRecords(vos);
        return ApiRes.Custom().success().addData(voPage);
    }

    @ApiOperation(value = "图库找图详情")
    @GetMapping("/skGalleryDetail")
    public ApiRes skGalleryDetail(Long skGalleryId){
        Map<String,Object> map = Maps.newHashMap();
        map.put("id",skGalleryId);
        ArUserSkGallery skGallery = skGalleryService.findOneByConditions(map);
        if(skGallery == null){
            return ApiRes.Custom().failure("该图库找图数据不存在");
        }

        SrArea area = areaService.selectById(skGallery.getCity());
        ArUserSkLabel label = labelService.selectById(skGallery.getLabel());

        AppArSkGalleryVo vo = new AppArSkGalleryVo();
        BeanUtils.copyProperties(skGallery,vo);
        vo.setAccount(skGallery.getUser().getAccount());
        vo.setCityName(area.getName());
        List<ArUserSkPic> pics = picService.findAllByGalleryId(skGallery.getId());
        vo.setPics(pics);
        vo.setLabelName(label.getName());
        return ApiRes.Custom().success().addData(vo);
    }

    @ApiOperation(value = "图库找图评论")
    @GetMapping("/skGalleryEvaluates")
    public ApiRes skGalleryEvaluates(Long skGalleryId, @ModelAttribute PageVo pageVo){
        Map<String,Object> map= Maps.newHashMap();
        map.put("skGalleryId",skGalleryId);
        map.put("type",1);
        Page<ArUserSkEvaluates> page = skEvaluatesService.findAllByConditions(pageVo.initPage(),map);

        return ApiRes.Custom().addData(page);
    }

    @ApiOperation(value = "图库找图删除")
    @DeleteMapping("/skGalleryDelete")
    public ApiRes skGalleryDelete(Long skGalleryId){
        return skGalleryManage.skGalleryDelete(skGalleryId);
    }

    @ApiOperation(value = "图库找图评论删除")
    @DeleteMapping("/skGalleryEvaluateDelete")
    public ApiRes skGalleryEvaluateDelete(Long skEvaluateId){
        return skGalleryManage.skGalleryEvaluateDelete(skEvaluateId);
    }

    @ApiOperation(value = "图库找图标签列表")
    @GetMapping("/getAllSkLabelByCondition")
    public ApiRes getAllSkLabelByCondition(@ModelAttribute AppArSkLabelVo skLabelVo,
                                           @ModelAttribute SearchVo searchVo,
                                           @ModelAttribute PageVo pageVo){
        Map<String,Object> map = Maps.newHashMap();
        map.putAll(beanMapper.map(skLabelVo,Map.class));
        map.putAll(beanMapper.map(searchVo,Map.class));

        Page<ArUserSkLabel> page = labelService.findAllLabelByCondition(pageVo.initPage(),map);

        Page<AppArSkLabelVo> voPage = new Page<>(page.getCurrent(),page.getSize());
        voPage.setTotal(page.getTotal());

        if(page.getRecords() == null ||page.getRecords().size()<1){
            return ApiRes.Custom().success().addData(voPage);
        }

        List<ArUserSkLabel> parentLabels = labelService.findAllParentLabel();
        Map<Long,String> parentLabelMap = parentLabels.stream().collect(Collectors.toMap(ArUserSkLabel::getId,ArUserSkLabel::getName));

        List<AppArSkLabelVo> vos = Lists.newArrayList();
        for(ArUserSkLabel label:page.getRecords()){
            AppArSkLabelVo vo = new AppArSkLabelVo();
            BeanUtils.copyProperties(label,vo);
            vo.setParentName(parentLabelMap.get(label.getPid()));
            vos.add(vo);
        }
        voPage.setRecords(vos);
        return ApiRes.Custom().success().addData(voPage);
    }

    @ApiOperation(value = "图库找图标签编辑")
    @GetMapping("/skLabelEdit")
    public ApiRes skLabelEdit(Long skLabelId){
        ArUserSkLabel skLabel = null;
        if(skLabelId != null){
            skLabel = labelService.selectById(skLabelId);
        }
        return ApiRes.Custom().success().addData(skLabel);
    }

    @ApiOperation(value = "图库找图所有父标签")
    @GetMapping("/allParentSkLabel")
    public ApiRes allParentSkLabel(){
        List<ArUserSkLabel> parentLabels = labelService.findAllParentLabel();
        return ApiRes.Custom().success().addData(parentLabels);
    }

    @ApiOperation(value = "图库找图标签新建或更新")
    @PostMapping("/skLabelUpdate")
    public ApiRes skLabelUpdate(@ModelAttribute AppArSkLabelVo labelVo){
        return skGalleryManage.skLabelUpdate(labelVo);
    }

    @ApiOperation(value = "图库找图标签删除")
    @DeleteMapping("/skLabelDelete")
    public ApiRes skLabelDelete(Long skLabelId){
        return skGalleryManage.skLabelDelete(skLabelId);
    }
}
