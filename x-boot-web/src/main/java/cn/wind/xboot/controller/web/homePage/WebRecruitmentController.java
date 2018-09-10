package cn.wind.xboot.controller.web.homePage;

import cn.wind.common.res.ApiRes;
import cn.wind.db.rc.entity.RcTattooRecruitment;
import cn.wind.db.rc.service.IRcTattooRecruitmentService;
import cn.wind.db.sr.entity.SrArea;
import cn.wind.db.sr.service.ISrAreaService;
import cn.wind.xboot.controller.BaseController;
import cn.wind.xboot.service.web.WebRcRecruitmentManage;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.SearchVo;
import cn.wind.xboot.vo.web.rc.AppRcRecruitmentVo;
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
 * @Date: 2018/9/5 16:34
 * @Description:
 */
@Api(value = "招聘管理",tags = "招聘管理")
@RestController
@RequestMapping("/webSys/recruit")
public class WebRecruitmentController extends BaseController{

    @Autowired
    private IRcTattooRecruitmentService recruitmentService;
    @Autowired
    private ISrAreaService areaService;
    @Autowired
    private WebRcRecruitmentManage recruitmentManage;

    @Override
    public IService getService() {
        return null;
    }

    @Override
    public String getBase() {
        return null;
    }

    @ApiOperation(value = "招聘分页列表")
    @GetMapping("/getAllRecruitmentByCondition")
    public ApiRes getAllRecruitmentByCondition(@ModelAttribute AppRcRecruitmentVo recruitmentVo,
                                               @ModelAttribute SearchVo searchVo,
                                               @ModelAttribute PageVo pageVo){
        Map<String,Object> map = Maps.newHashMap();
        map.putAll(beanMapper.map(recruitmentVo,Map.class));
        map.putAll(beanMapper.map(searchVo,Map.class));

        Page<RcTattooRecruitment> page = recruitmentService.findAllRcByCondition(pageVo.initPage(),map);

        Page<AppRcRecruitmentVo> voPage = new Page<>(page.getCurrent(),page.getSize());
        voPage.setTotal(page.getTotal());

        if(page.getRecords() == null || page.getRecords().size()<1){
            return ApiRes.Custom().success().addData(voPage);
        }

        List<Long> cityIds = page.getRecords().stream().map(RcTattooRecruitment::getCity).collect(Collectors.toList());

        List<SrArea> areas = areaService.findAllByIdsIn(cityIds);
        Map<Long,String> areaMap = areas.stream().collect(Collectors.toMap(SrArea::getId, SrArea::getName));

        List<AppRcRecruitmentVo> vos = Lists.newArrayList();
        for(RcTattooRecruitment recruitment:page.getRecords()){
            AppRcRecruitmentVo vo = new AppRcRecruitmentVo();
            BeanUtils.copyProperties(recruitment,vo);
            vo.setAccount(recruitment.getUser().getAccount());
            vo.setCityName(areaMap.get(recruitment.getCity()));
            vos.add(vo);
        }
        voPage.setRecords(vos);
        return ApiRes.Custom().success().addData(voPage);
    }

    @ApiOperation("招聘删除")
    @DeleteMapping("/recruitmentDelete")
    public ApiRes recruitmentDelete(Long recruitId){
        return recruitmentManage.recruitmentDelete(recruitId);
    }
}
