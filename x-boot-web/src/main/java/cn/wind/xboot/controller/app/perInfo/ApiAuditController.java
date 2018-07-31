package cn.wind.xboot.controller.app.perInfo;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.db.ar.entity.ArSignAudit;
import cn.wind.db.ar.entity.ArStoreAduit;
import cn.wind.db.ar.entity.ArTattooAduit;
import cn.wind.db.ar.service.IArSignAuditService;
import cn.wind.db.ar.service.IArStoreAduitService;
import cn.wind.db.ar.service.IArTattooAduitService;
import cn.wind.xboot.controller.app.AppBaseController;
import cn.wind.xboot.dto.app.ar.arNameAduitDto;
import cn.wind.xboot.dto.app.ar.arStoreAduitDto;
import cn.wind.xboot.service.app.CXArAuditManage;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/23 11:51
 * @Description:
 */
@Api(value = "用户认证管理",tags = "用户认证管理")
@RestController
@RequestMapping("/api/app/audit")
public class ApiAuditController extends AppBaseController{

    @Autowired
    private IArSignAuditService signAuditService;
    @Autowired
    private IArStoreAduitService storeAduitService;
    @Autowired
    private CXArAuditManage auditManage;
    @Autowired
    private IArTattooAduitService tattooAduitService;

    @ApiOperation(value = "签约认证")
    @PostMapping("/signAudit")
    public ApiRes signAudit(){
        try{
            Map<String,Object> map = Maps.newHashMap();
            map.put("userId",getUserId());
            map.put("status",2);
            ArSignAudit signAudits = signAuditService.findOneByConditions(map);

            if(signAudits != null){
                return ApiRes.Custom().failure(ApiStatus.SIGN_ALREADY_ADUIT);
            }
            map = Maps.newHashMap();
            map.put("userId",getUserId());
            map.put("status",1);
            ArSignAudit signAudits2 = signAuditService.findOneByConditions(map);

            if(signAudits2 != null){
                return ApiRes.Custom().failure(ApiStatus.SIGN_ING_ADUIT);
            }

            auditManage.newSignAudit(getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "店铺认证")
    @PostMapping("/storeAudit")
    public ApiRes storeAudit(arStoreAduitDto dto){
        try{
            Map<String,Object> map = Maps.newHashMap();
            map.put("userId",getUserId());
            map.put("status",2);
            ArStoreAduit storeAduit = storeAduitService.findOneByConditions(map);

            if(storeAduit != null){
                return ApiRes.Custom().failure(ApiStatus.STORE_ALREADY_ADUIT);
            }

            map = Maps.newHashMap();
            map.put("userId",getUserId());
            map.put("status",1);
            ArStoreAduit storeAduit2 = storeAduitService.findOneByConditions(map);
            if(storeAduit2 != null){
                return ApiRes.Custom().failure(ApiStatus.STORE_ING_ADUIT);
            }

            auditManage.newStoreAudit(dto,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "实名认证")
    @PostMapping("/nameAudit")
    public ApiRes nameAudit(arNameAduitDto dto){
        try{
            Map<String,Object> map = Maps.newHashMap();
            map.put("userId",getUserId());
            map.put("status",2);
            ArTattooAduit tattooAduit = tattooAduitService.findOneByConditions(map);

            if(tattooAduit!= null){
                return ApiRes.Custom().failure(ApiStatus.NAME_ALREADY_ADUIT);
            }

            map = Maps.newHashMap();
            map.put("userId",getUserId());
            map.put("status",1);
            ArTattooAduit tattooAduit2 = tattooAduitService.findOneByConditions(map);

            if(tattooAduit2 != null){
                return ApiRes.Custom().failure(ApiStatus.NAME_ING_ADUIT);
            }

            auditManage.newNameAudit(dto,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }
}
