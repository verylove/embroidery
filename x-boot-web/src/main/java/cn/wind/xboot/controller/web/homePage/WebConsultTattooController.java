package cn.wind.xboot.controller.web.homePage;

import cn.wind.common.res.ApiRes;
import cn.wind.db.ar.entity.ArUserZxPic;
import cn.wind.db.ar.entity.ArUserZxTattoo;
import cn.wind.db.ar.service.IArUserZxPicService;
import cn.wind.db.ar.service.IArUserZxTattooService;
import cn.wind.db.sr.entity.SrArea;
import cn.wind.db.sr.service.ISrAreaService;
import cn.wind.xboot.controller.BaseController;
import cn.wind.xboot.service.web.WebZxTattooManage;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.SearchVo;
import cn.wind.xboot.vo.web.ar.zxTattoo.AppArZxTattooVo;
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
 * @Date: 2018/9/4 09:46
 * @Description:
 */
@Api(value = "纹身咨询管理",tags = "纹身咨询管理")
@RestController
@RequestMapping("/webSys/zxTattoo")
public class WebConsultTattooController extends BaseController {

    @Autowired
    private IArUserZxTattooService zxTattooService;
    @Autowired
    private ISrAreaService areaService;
    @Autowired
    private IArUserZxPicService picService;
    @Autowired
    private WebZxTattooManage zxTattooManage;

    @Override
    public IService getService() {
        return null;
    }

    @Override
    public String getBase() {
        return null;
    }

    @ApiOperation(value = "分页获取App纹身咨询列表")
    @GetMapping("/getAllZxTattooByCondition")
    public ApiRes getAllZxTattooByCondition(@ModelAttribute AppArZxTattooVo zxTattooVo,
                                            @ModelAttribute SearchVo searchVo,
                                            @ModelAttribute PageVo pageVo){
        Map<String,Object> map = Maps.newHashMap();
        map.putAll(beanMapper.map(zxTattooVo,Map.class));
        map.putAll(beanMapper.map(searchVo,Map.class));

        Page<ArUserZxTattoo> page = zxTattooService.findAllZxByCondition(pageVo.initPage(),map);

        Page<AppArZxTattooVo> voPage = new Page<>(page.getCurrent(),page.getSize());
        voPage.setTotal(page.getTotal());

        if(page.getRecords()==null || page.getRecords().size()<1){
            return ApiRes.Custom().success().addData(voPage);
        }

        List<Long> cityIds = page.getRecords().stream().map(ArUserZxTattoo::getCity).collect(Collectors.toList());

        List<SrArea> areas = areaService.findAllByIdsIn(cityIds);
        Map<Long,String> areaMap = areas.stream().collect(Collectors.toMap(SrArea::getId, SrArea::getName));

        List<AppArZxTattooVo> vos = Lists.newArrayList();
        for(ArUserZxTattoo zxTattoo:page.getRecords()){
            AppArZxTattooVo vo = new AppArZxTattooVo();
            BeanUtils.copyProperties(zxTattoo,vo);
            vo.setAccount(zxTattoo.getUser().getAccount());
            vo.setCityName(areaMap.get(zxTattoo.getCity()));
            List<ArUserZxPic> pics = picService.findAllByTattooId(zxTattoo.getId());
            vo.setPics(pics);
            vos.add(vo);
        }
        voPage.setRecords(vos);
        return ApiRes.Custom().success().addData(voPage);
    }

    @ApiOperation(value = "App纹身咨询删除")
    @DeleteMapping("/zxTattooDelete")
    public ApiRes zxTattooDelete(Long zxTattooId){
        return zxTattooManage.zxTattooDelete(zxTattooId);
    }
}
