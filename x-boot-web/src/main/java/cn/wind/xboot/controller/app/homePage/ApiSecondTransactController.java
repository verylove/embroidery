package cn.wind.xboot.controller.app.homePage;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.common.utils.ObjectMapperUtils;
import cn.wind.db.ar.entity.ArStoreAduit;
import cn.wind.db.ar.entity.ArUser;
import cn.wind.db.ar.service.IArStoreAduitService;
import cn.wind.db.rc.entity.RcSecondPic;
import cn.wind.db.rc.entity.RcSecondTransactions;
import cn.wind.db.rc.service.IRcSecondPicService;
import cn.wind.db.rc.service.IRcSecondTransactionsService;
import cn.wind.xboot.controller.app.AppBaseController;
import cn.wind.xboot.dto.app.rc.rcSecondTransactDto;
import cn.wind.xboot.enums.AduitType;
import cn.wind.xboot.enums.IdentityType;
import cn.wind.xboot.service.app.CXArUserManage;
import cn.wind.xboot.service.app.CXRcSecondTransactManage;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.app.rc.RcSecondTransactVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/18 15:48
 * @Description:
 */
@Api(value = "二手市场",tags = "二手市场")
@RestController
@RequestMapping("/api/app/secondTransact")
public class ApiSecondTransactController extends AppBaseController{

    @Autowired
    private CXArUserManage userManage;
    @Autowired
    private CXRcSecondTransactManage secondTransactManage;
    @Autowired
    private IRcSecondTransactionsService secondTransactionsService;
    @Autowired
    private IRcSecondPicService secondPicService;
    @Autowired
    private IArStoreAduitService storeAduitService;

    @ApiOperation(value = "二手市场 发布")
    @PostMapping("/publishInSecondTransact")
    public ApiRes publishInSecondTransact(rcSecondTransactDto dto){
        try{
            //1.用户是否实名认证
            if(!userManage.IsNameAduit(getUserId())){
                return ApiRes.Custom().failure(ApiStatus.NAME_NO_ADUIT);
            }

            secondTransactManage.publishInSecondTransact(dto,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "二手市场 分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name="longitude",value = "经度",dataType = "Double",required = false,paramType = "query"),
            @ApiImplicitParam(name="latitude",value = "纬度",dataType = "Double",required = false,paramType = "query")
    })
    @GetMapping("/pageInSecondTransact")
    public ApiRes pageInSecondTransact(@RequestParam(value = "longitude",required = false)Double longitude,
                                       @RequestParam(value = "latitude",required = false)Double latitude, @ModelAttribute PageVo pageVo){
        try{
            Page<RcSecondTransactions> page;
            Map<String,Object> map= Maps.newHashMap();
            if(longitude==null||latitude==null){
                //全国
                page = secondTransactionsService.findAllByConditons(pageVo.initPage(),map);
            }else {
                //附近
                map.put("longitude",longitude);
                map.put("latitude",latitude);
                page = secondTransactionsService.findByCoordinates(pageVo.initPage(),map);
            }

            Page<RcSecondTransactVo> pageResult = new Page<>(page.getCurrent(), page.getSize());

            pageResult.setRecords(getVo(page.getRecords()));
            return ApiRes.Custom().addData(pageResult);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation("查看电话")
    @ApiImplicitParam(name = "SecondTransactId",value = "二手市场ID",required = true,paramType = "query")
    @GetMapping("/infoForSecondTransact")
    public ApiRes infoForSecondTransact(Long SecondTransactId){
        try{
            //1.用户是否实名认证
            if(!userManage.IsNameAduit(getUserId())){
                return ApiRes.Custom().failure(ApiStatus.NAME_NO_ADUIT);
            }

            Map<String,Object> map = Maps.newHashMap();
            map.put("SecondTransactId",SecondTransactId);
            RcSecondTransactions transactions = secondTransactionsService.findOneByConditions(map);
            return ApiRes.Custom().addData(transactions.getPhone());
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "二手市场 分页")
    @ApiImplicitParam(name="userId",value = "用户ID",dataType = "Long",required = true,paramType = "query")
    @GetMapping("/pageInPerSecondTransact")
    public ApiRes pageInPerSecondTransact(Long userId,@ModelAttribute PageVo pageVo){
        try{
            Map<String,Object> map= Maps.newHashMap();
            map.put("userId",userId);
            Page<RcSecondTransactions> page = secondTransactionsService.findAllByConditons(pageVo.initPage(),map);

            Page<RcSecondTransactVo> pageResult = new Page<>(page.getCurrent(), page.getSize());


            pageResult.setRecords(getVo(page.getRecords()));
            return ApiRes.Custom().addData(pageResult);

        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    public List<RcSecondTransactVo> getVo(List<RcSecondTransactions> secondTransactions){
        Map<Long,RcSecondTransactions> map2 = secondTransactions.stream().collect(Collectors.toMap(RcSecondTransactions::getId, Function.identity()));

        List<RcSecondTransactVo> rcSecondTransactVos = ObjectMapperUtils.mapAll(secondTransactions, RcSecondTransactVo.class);
        for(RcSecondTransactVo vo:rcSecondTransactVos){
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
            //3.图片
            List<RcSecondPic> pics = secondPicService.findAllBySecondTransactId(vo.getId());
            vo.setPics(pics);
        }
        return rcSecondTransactVos;
    }
}
