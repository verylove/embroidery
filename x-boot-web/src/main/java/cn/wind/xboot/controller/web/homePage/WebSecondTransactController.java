package cn.wind.xboot.controller.web.homePage;

import cn.wind.common.res.ApiRes;
import cn.wind.db.rc.entity.RcSecondPic;
import cn.wind.db.rc.entity.RcSecondTransactions;
import cn.wind.db.rc.service.IRcSecondPicService;
import cn.wind.db.rc.service.IRcSecondTransactionsService;
import cn.wind.db.sr.entity.SrArea;
import cn.wind.db.sr.service.ISrAreaService;
import cn.wind.xboot.controller.BaseController;
import cn.wind.xboot.service.web.WebSecondTransactManage;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.SearchVo;
import cn.wind.xboot.vo.web.rc.AppRcSecondTransactVo;
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
 * @Date: 2018/9/5 17:11
 * @Description:
 */
@Api(value = "二手市场管理",tags = "二手市场管理")
@RestController
@RequestMapping("/webSys/secondTransact")
public class WebSecondTransactController extends BaseController{

    @Autowired
    private IRcSecondTransactionsService secondTransactionsService;
    @Autowired
    private ISrAreaService areaService;
    @Autowired
    private IRcSecondPicService picService;
    @Autowired
    private WebSecondTransactManage secondTransactManage;


    @Override
    public IService getService() {
        return null;
    }

    @Override
    public String getBase() {
        return null;
    }

    @ApiOperation(value = "二手市场分页列表")
    @GetMapping("/getAllSecondTransactByCondition")
    public ApiRes getAllSecondTransactByCondition(@ModelAttribute AppRcSecondTransactVo secondTransactVo,
                                                  @ModelAttribute SearchVo searchVo,
                                                  @ModelAttribute PageVo pageVo){
        Map<String,Object> map = Maps.newHashMap();
        map.putAll(beanMapper.map(secondTransactVo,Map.class));
        map.putAll(beanMapper.map(searchVo,Map.class));

        Page<RcSecondTransactions> page = secondTransactionsService.findAllRcByCondition(pageVo.initPage(),map);

        Page<AppRcSecondTransactVo> voPage = new Page<>(page.getCurrent(),page.getSize());
        voPage.setTotal(page.getTotal());

        if(page.getRecords() == null || page.getRecords().size()<1){
            return ApiRes.Custom().success().addData(voPage);
        }

        List<Long> cityIds = page.getRecords().stream().map(RcSecondTransactions::getCity).collect(Collectors.toList());

        List<SrArea> areas = areaService.findAllByIdsIn(cityIds);
        Map<Long,String> areaMap = areas.stream().collect(Collectors.toMap(SrArea::getId, SrArea::getName));

        List<AppRcSecondTransactVo> vos = Lists.newArrayList();
        for(RcSecondTransactions secondTransactions:page.getRecords()){
            AppRcSecondTransactVo vo = new AppRcSecondTransactVo();
            BeanUtils.copyProperties(secondTransactions,vo);
            vo.setAccount(secondTransactions.getUser().getAccount());
            vo.setCityName(areaMap.get(secondTransactions.getCity()));
            List<RcSecondPic> pics = picService.findAllBySecondTransactId(secondTransactions.getId());
            vo.setPics(pics);
            vos.add(vo);
        }
        voPage.setRecords(vos);
        return ApiRes.Custom().success().addData(voPage);
    }

    @ApiOperation(value = "二手市场信息删除")
    @DeleteMapping("/secondTransactDelete")
    public ApiRes secondTransactDelete(Long SecondTransactId){
        return secondTransactManage.secondTransactDelete(SecondTransactId);
    }
}
