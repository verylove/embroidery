package cn.wind.xboot.controller.web.homePage;

import cn.wind.common.res.ApiRes;
import cn.wind.db.ar.entity.ArUserTbEvaluates;
import cn.wind.db.ar.entity.ArUserTbPic;
import cn.wind.db.ar.entity.ArUserTbTopic;
import cn.wind.db.ar.service.IArUserTbEvaluatesService;
import cn.wind.db.ar.service.IArUserTbPicService;
import cn.wind.db.ar.service.IArUserTbTopicService;
import cn.wind.db.sr.entity.SrArea;
import cn.wind.db.sr.service.ISrAreaService;
import cn.wind.xboot.controller.BaseController;
import cn.wind.xboot.service.web.WebTbTopicManage;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.SearchVo;
import cn.wind.xboot.vo.web.ar.tbTopic.AppArTbTopicVo;
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
 * @Date: 2018/9/4 16:32
 * @Description:
 */
@Api(value = "贴吧话题管理",tags = "贴吧话题管理")
@RestController
@RequestMapping("/webSys/tbTopic")
public class WebTiebaTopicController extends BaseController{

    @Autowired
    private IArUserTbTopicService tbTopicService;
    @Autowired
    private ISrAreaService areaService;
    @Autowired
    private IArUserTbPicService picService;
    @Autowired
    private IArUserTbEvaluatesService tbEvaluatesService;
    @Autowired
    private WebTbTopicManage tbTopicManage;

    @Override
    public IService getService() {
        return null;
    }

    @Override
    public String getBase() {
        return null;
    }

    @ApiOperation(value = "贴吧话题分页列表")
    @GetMapping("/getAllTbTopicByCondition")
    public ApiRes getAllTbTopicByCondition(@ModelAttribute AppArTbTopicVo tbTopicVo,
                                           @ModelAttribute SearchVo searchVo,
                                           @ModelAttribute PageVo pageVo){
        Map<String,Object> map = Maps.newHashMap();
        map.putAll(beanMapper.map(tbTopicVo,Map.class));
        map.putAll(beanMapper.map(searchVo,Map.class));

        Page<ArUserTbTopic> page = tbTopicService.findAllTbByCondition(pageVo.initPage(),map);

        Page<AppArTbTopicVo> voPage = new Page<>(page.getCurrent(),page.getSize());
        voPage.setTotal(page.getTotal());

        if(page.getRecords() == null || page.getRecords().size()<1){
            return ApiRes.Custom().success().addData(voPage);
        }

        List<Long> cityIds = page.getRecords().stream().map(ArUserTbTopic::getCity).collect(Collectors.toList());

        List<SrArea> areas = areaService.findAllByIdsIn(cityIds);
        Map<Long,String> areaMap = areas.stream().collect(Collectors.toMap(SrArea::getId, SrArea::getName));

        List<AppArTbTopicVo> vos = Lists.newArrayList();
        for(ArUserTbTopic tbTopic:page.getRecords()){
            AppArTbTopicVo vo = new AppArTbTopicVo();
            BeanUtils.copyProperties(tbTopic,vo);
            vo.setAccount(tbTopic.getUser().getAccount());
            vo.setCityName(areaMap.get(tbTopic.getCity()));
            List<ArUserTbPic> pics = picService.findAllByTopicId(tbTopic.getId());
            vo.setPics(pics);
            vos.add(vo);
        }
        voPage.setRecords(vos);
        return ApiRes.Custom().success().addData(voPage);
    }

    @ApiOperation(value = "贴吧话题详情")
    @GetMapping("/tbTopicDetail")
    public ApiRes tbTopicDetail(Long tbTopicId){
        Map<String,Object> map = Maps.newHashMap();
        map.put("tbTopicId",tbTopicId);
        ArUserTbTopic tbTopic = tbTopicService.findOneByConditons(map);

        if(tbTopic == null){
            return ApiRes.Custom().failure("该贴吧话题数据不存在");
        }

        SrArea area = areaService.selectById(tbTopic.getCity());

        AppArTbTopicVo vo = new AppArTbTopicVo();
        vo.setAccount(tbTopic.getUser().getAccount());
        vo.setCityName(area.getName());
        List<ArUserTbPic> pics = picService.findAllByTopicId(tbTopic.getId());
        vo.setPics(pics);

        return ApiRes.Custom().success().addData(vo);
    }

    @ApiOperation(value = "贴吧话题评论分页列表")
    @GetMapping("/tbTopicEvaluate")
    public ApiRes tbTopicEvaluate(Long tbTopicId,@ModelAttribute PageVo pageVo){
        Map<String,Object> map = Maps.newHashMap();
        map.put("tbTopicId",tbTopicId);
        map.put("type",1);
        Page<ArUserTbEvaluates> page = tbEvaluatesService.findAllByConditions(pageVo.initPage(),map);

        return ApiRes.Custom().success().addData(page);
    }

    @ApiOperation(value = "贴吧话题删除")
    @DeleteMapping("/tbTopicDelete")
    public ApiRes tbTopicDelete(Long tbTopicId){
        return tbTopicManage.tbTopicDelete(tbTopicId);
    }

    @ApiOperation(value = "贴吧话题评论删除")
    @DeleteMapping("/tbTopicEvaluateDelete")
    public ApiRes tbTopicEvaluateDelete(Long tbTopicEvaluateId){
        return tbTopicManage.tbTopicEvaluateDelete(tbTopicEvaluateId);
    }
}
