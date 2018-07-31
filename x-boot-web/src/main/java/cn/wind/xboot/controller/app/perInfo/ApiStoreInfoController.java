package cn.wind.xboot.controller.app.perInfo;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.db.ar.entity.ArStoreAduit;
import cn.wind.db.ar.entity.ArStorePic;
import cn.wind.db.ar.entity.ArUser;
import cn.wind.db.ar.service.IArStoreAduitService;
import cn.wind.db.ar.service.IArStorePicService;
import cn.wind.db.ar.service.IArUserService;
import cn.wind.xboot.enums.AduitType;
import cn.wind.xboot.vo.app.ar.ArUserStoreVo;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/23 09:52
 * @Description:
 */
@Api(value = "店铺管理",tags = "店铺管理")
@RestController
@RequestMapping("/api/app/store")
public class ApiStoreInfoController {

    @Autowired
    private IArUserService userService;
    @Autowired
    private IArStoreAduitService storeAduitService;
    @Autowired
    private IArStorePicService storePicService;

    @ApiOperation(value = "店铺信息 店铺主页")
    @ApiImplicitParam(name = "userId",value = "用户ID",dataType = "Long",required = true,paramType = "query")
    @GetMapping("/storeInfo")
    public ApiRes storeInfo(Long userId){
        try{
            ArUser user = userService.selectById(userId);
            if(user==null){
                return ApiRes.Custom().failure(ApiStatus.USER_NOT_EXIST);
            }
            Map<String,Object> map = Maps.newHashMap();
            map.put("storeId",user.getStoreId());
            map.put("userId",userId);
            ArStoreAduit storeAduit = storeAduitService.findOneByConditions(map);
            if(storeAduit==null){
                return ApiRes.Custom().failure(ApiStatus.STROE_NOT_EXIST);
            }
            ArUserStoreVo vo = new ArUserStoreVo();
            vo.setSentimentNum(user.getSentimentNum());
            vo.setFollowNum(user.getFollowNum());
            vo.setIcon(user.getIcon());
            vo.setId(storeAduit.getId());
            vo.setUserId(user.getId());
            vo.setStoreName(storeAduit.getStoreName());
            vo.setStoreAddress(storeAduit.getStoreAddress());
            vo.setAudit(AduitType.STOREADUIT.getaType());

            return ApiRes.Custom().addData(vo);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "店铺环境")
    @ApiImplicitParam(name = "storeId",value = "店铺ID",dataType = "Long",required = true,paramType = "query")
    @GetMapping("/storePics")
    public ApiRes storePics(Long storeId){
        try{
            ArStoreAduit storeAduit = storeAduitService.selectById(storeId);
            if(storeAduit==null){
                return ApiRes.Custom().failure(ApiStatus.STROE_NOT_EXIST);
            }
            List<ArStorePic> pics = storePicService.findAllByStoreId(storeId);
            return ApiRes.Custom().addData(pics);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }
}
