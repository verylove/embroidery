package cn.wind.xboot.controller.app.homePage;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.common.utils.ObjectMapperUtils;
import cn.wind.db.ar.entity.ArStoreAduit;
import cn.wind.db.ar.entity.ArUser;
import cn.wind.db.ar.entity.ArUserSpTattoo;
import cn.wind.db.ar.service.IArStoreAduitService;
import cn.wind.db.rc.entity.RcTattooRecruitment;
import cn.wind.db.rc.service.IRcTattooRecruitmentService;
import cn.wind.xboot.controller.app.AppBaseController;
import cn.wind.xboot.dto.app.rc.rcRecruitMentDto;
import cn.wind.xboot.enums.AduitType;
import cn.wind.xboot.enums.IdentityType;
import cn.wind.xboot.service.app.CXArUserManage;
import cn.wind.xboot.service.app.CXRcRecruitManage;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.app.ar.ArUserSpTattooVo;
import cn.wind.xboot.vo.app.rc.RcTattooRecruitVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/18 14:34
 * @Description:
 */
@Api(value = "招聘管理",tags = "招聘管理")
@RestController
@RequestMapping("/api/app/recruit")
public class ApiRecruitmentController extends AppBaseController{

    @Autowired
    private CXArUserManage userManage;
    @Autowired
    private CXRcRecruitManage rcRecruitManage;
    @Autowired
    private IRcTattooRecruitmentService recruitmentService;
    @Autowired
    private IArStoreAduitService storeAduitService;

    @ApiOperation(value = "招聘信息 发布")
    @PostMapping("/publishInRecruit")
    public ApiRes publishInRecruit(rcRecruitMentDto dto){
        try{
            //1.用户是否实名认证
            if(!userManage.IsNameAduit(getUserId())){
                return ApiRes.Custom().failure(ApiStatus.NAME_NO_ADUIT);
            }

            rcRecruitManage.publishInRecruit(dto,getUserId());
            return ApiRes.Custom().success();

        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "招聘信息 分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name="longitude",value = "经度",dataType = "Double",required = false,paramType = "query"),
            @ApiImplicitParam(name="latitude",value = "纬度",dataType = "Double",required = false,paramType = "query")
    })
    @GetMapping("/pageInRecruit")
    public ApiRes pageInRecruit(@RequestParam(value = "longitude",required = false)Double longitude,
                                @RequestParam(value = "latitude",required = false)Double latitude, @ModelAttribute PageVo pageVo){
        try{
            Page<RcTattooRecruitment> page;
            Map<String,Object> map= Maps.newHashMap();
            if(longitude==null||latitude==null){
                //全国
                page = recruitmentService.findAllByConditons(pageVo.initPage(),map);
            }else {
                //附近
                map.put("longitude",longitude);
                map.put("latitude",latitude);
                page = recruitmentService.findByCoordinates(pageVo.initPage(),map);
            }

            Page<RcTattooRecruitVo> pageResult = new Page<>(page.getCurrent(), page.getSize());

            pageResult.setRecords(getVo(page.getRecords()));
            return ApiRes.Custom().addData(pageResult);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "查看电话")
    @ApiImplicitParam(name = "recruitId",value = "招聘信息ID",required = true,paramType = "query")
    @GetMapping("/infoForRecruit")
    public ApiRes infoForRecruit(Long recruitId){
        try{
            //1.用户是否实名认证
            if(!userManage.IsNameAduit(getUserId())){
                return ApiRes.Custom().failure(ApiStatus.NAME_NO_ADUIT);
            }

            Map<String,Object> map = Maps.newHashMap();
            map.put("recruitId",recruitId);
            RcTattooRecruitment recruitment = recruitmentService.findOneByConditions(map);
            return ApiRes.Custom().addData(recruitment.getPhone());

        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "招聘信息个人 分页")
    @ApiImplicitParam(name="userId",value = "用户ID",dataType = "Long",required = true,paramType = "query")
    @GetMapping("/pageInPerRecruit")
    public ApiRes pageInPerRecruit(Long userId, @ModelAttribute PageVo pageVo){
        try{
            Map<String,Object> map= Maps.newHashMap();
            map.put("userId",userId);
            Page<RcTattooRecruitment> page = recruitmentService.findAllByConditons(pageVo.initPage(),map);

            Page<RcTattooRecruitVo> pageResult = new Page<>(page.getCurrent(), page.getSize());

            pageResult.setRecords(getVo(page.getRecords()));
            return ApiRes.Custom().addData(pageResult);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    public List<RcTattooRecruitVo> getVo(List<RcTattooRecruitment> rcTattooRecruitments){
        Map<Long,RcTattooRecruitment> map2 = rcTattooRecruitments.stream().collect(Collectors.toMap(RcTattooRecruitment::getId, Function.identity()));

        List<RcTattooRecruitVo> rcTattooRecruitVos = ObjectMapperUtils.mapAll(rcTattooRecruitments, RcTattooRecruitVo.class);
        for(RcTattooRecruitVo vo:rcTattooRecruitVos){
            ArUser t = map2.get(vo.getId()).getUser();
            vo.setPerLevel(t.getPerLevel());
            vo.setAccount(t.getAccount());
            vo.setIcon(t.getIcon());
            //1.认证
            if(t.getStoreStatus()==3){
                vo.setAduit(AduitType.STOREADUIT.getaType());
            }else if(t.getSignStatus()==3){
                vo.setAduit(AduitType.SIGNADUIT.getaType());
            }else if(t.getNameStatus()==3){
                vo.setAduit(AduitType.NAMEADUIT.getaType());
            }
            //2.名片
            if(t.getStoreStatus()==3){
                // TODO 获取店铺名
                ArStoreAduit s = storeAduitService.selectById(t.getStoreId());
                vo.setBusinessCard(s.getStoreName());
            }else if(t.getIdentity()==1){
                vo.setBusinessCard(IdentityType.TATTOO.getType());
            }else {
                vo.setBusinessCard(IdentityType.LOVERS.getType());
            }

        }
        return rcTattooRecruitVos;
    }
}
