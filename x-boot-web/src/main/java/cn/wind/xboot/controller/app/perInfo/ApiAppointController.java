package cn.wind.xboot.controller.app.perInfo;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.common.utils.ObjectMapperUtils;
import cn.wind.db.ar.entity.ArAppointOrder;
import cn.wind.db.ar.entity.ArUser;
import cn.wind.db.ar.service.IArAppointOrderService;
import cn.wind.xboot.controller.app.AppBaseController;
import cn.wind.xboot.dto.app.ar.arAppointOrderDto;
import cn.wind.xboot.enums.IdentityType;
import cn.wind.xboot.service.app.CXArAppointManage;
import cn.wind.xboot.service.app.CXArUserManage;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.app.ar.ArAppointOrderVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/23 17:14
 * @Description:
 */
@Api(value = "预约订单管理",tags = "预约订单管理")
@RestController
@RequestMapping("/api/app/appoint")
public class ApiAppointController extends AppBaseController{

    @Autowired
    private CXArUserManage userManage;
    @Autowired
    private CXArAppointManage appointManage;
    @Autowired
    private IArAppointOrderService appointOrderService;

    @ApiOperation(value = "预约订单")
    @PostMapping("/appointOrder")
    public ApiRes appointOrder(arAppointOrderDto dto){
        try{
            //1.用户是否未实名认证
            if(userManage.IsNameAduit(getUserId())){
                return ApiRes.Custom().failure(ApiStatus.NAME_ALREADY_ADUIT);
            }

            appointManage.newOrder(dto,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "预约订单 分页 爱好者端")
    @GetMapping("/pageInAppointOrderForLovers")
    public ApiRes pageInAppointOrderForLovers(@ModelAttribute PageVo pageVo){
        try{
            Map<String,Object> map = Maps.newHashMap();
            map.put("userId",getUserId());
            Page<ArAppointOrder> page = appointOrderService.findAllByConditions(pageVo.initPage(),map);

            Page<ArAppointOrderVo> voPage = new Page<>(page.getCurrent(),page.getSize());
            voPage.setTotal(voPage.getTotal());

            voPage.setRecords(getVo(page.getRecords()));
            return ApiRes.Custom().addData(voPage);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    private List<ArAppointOrderVo> getVo(List<ArAppointOrder> records) {
        Map<Long,ArAppointOrder> map = records.stream().collect(Collectors.toMap(ArAppointOrder::getId, Function.identity()));

        List<ArAppointOrderVo> vos = ObjectMapperUtils.mapAll(records,ArAppointOrderVo.class);

        for(ArAppointOrderVo vo:vos){
            ArUser user = map.get(vo.getId()).getUser();
            vo.setAccount(user.getAccount());
            vo.setBusinessCard(IdentityType.LOVERS.getType());
            vo.setIcon(user.getIcon());
            vo.setPerLevel(user.getPerLevel());
        }
        return vos;
    }

    @ApiOperation(value = "预约订单 当日 分页 纹身师端")
    @GetMapping("/pageInAppointForTattooNow")
    public ApiRes pageInAppointForTattooNow(@ModelAttribute PageVo pageVo){
        try{
            Map<String,Object> map = Maps.newHashMap();
            map.put("artistId",getUserId());
            Page<ArAppointOrder> page = appointOrderService.findAllByConditions2(pageVo.initPage(),map);

            Page<ArAppointOrderVo> voPage = new Page<>(page.getCurrent(),page.getSize());
            voPage.setTotal(voPage.getTotal());

            voPage.setRecords(getVo(page.getRecords()));
            return ApiRes.Custom().addData(voPage);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "预约订单 所有 分页 纹身师端")
    @GetMapping("/pageInAppointForTattooAll")
    public ApiRes pageInAppointForTattooAll(@ModelAttribute PageVo pageVo){
        try{
            Map<String,Object> map = Maps.newHashMap();
            map.put("artistId",getUserId());
            Page<ArAppointOrder> page = appointOrderService.findAllByConditions(pageVo.initPage(),map);

            Page<ArAppointOrderVo> voPage = new Page<>(page.getCurrent(),page.getSize());
            voPage.setTotal(voPage.getTotal());

            voPage.setRecords(getVo(page.getRecords()));
            return ApiRes.Custom().addData(voPage);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }
}
