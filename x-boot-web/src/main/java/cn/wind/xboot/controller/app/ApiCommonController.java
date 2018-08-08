package cn.wind.xboot.controller.app;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.db.sr.entity.SrArea;
import cn.wind.db.sr.service.ISrAreaService;
import cn.wind.xboot.service.AreaManage;
import cn.wind.xboot.service.app.CXArUserManage;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/12 09:23
 * @Description:
 */
@Api(value = "app通用接口",tags = "app通用接口")
@RestController
@RequestMapping(value = "/api/app/common")
public class ApiCommonController extends AppBaseController{

    @Autowired
    private CXArUserManage userManage;
    @Autowired
    private ISrAreaService areaService;
    @Autowired
    private AreaManage areaManage;

    @ApiOperation(value = "用户是否第一次点赞")
    @GetMapping(value = "/greatStatus")
    public ApiRes greatStautsInApp(){
        try{
            //1.第一次在该App点赞
            if(!userManage.IsGreatInApp(getUserId())){
                return ApiRes.Custom().failure(ApiStatus.GREAT_FIRST);
            }
            return ApiRes.Custom().failure(ApiStatus.GREAT_NOT_FIRST);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "获取指定地方名")
    @ApiImplicitParam(name = "areaId",value = "地方ID",required = true,paramType = "query")
    @GetMapping(value = "/getArea")
    public ApiRes getCity(Long areaId){
        try {
            SrArea area = areaService.selectById(areaId);
            return ApiRes.Custom().addData(area);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "获取所有地方名")
    @GetMapping(value = "/getAllArea")
    public ApiRes getAllArea(){
        try{
            List<SrArea> srAreas = areaService.selectList(new EntityWrapper<SrArea>());
            return ApiRes.Custom().add(srAreas);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "获取所有省的信息")
    @GetMapping(value = "getProvinceList")
    public ApiRes getProvinceList(){
        try {
            Map<String,Object> map = Maps.newHashMap();
            map.put("level",1);
            List<SrArea> areaList = areaService.findAllByConditions(map);
            return ApiRes.Custom().add(areaList);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "获取某省下的所有城市")
    @GetMapping(value = "getCityList/{provinceId}")
    public ApiRes getCityList(@PathVariable Long provinceId){
        try{
            SrArea province = areaService.selectById(provinceId);
            if(province==null){
                return ApiRes.Custom().failure(ApiStatus.AREA_NOT_EXIST);
            }
            Map<String,Object> map = Maps.newHashMap();
            map.put("level",2);
            map.put("code",province.getCode().substring(0,2));
            List<SrArea> areaList = areaService.findAllByConditions(map);
            return ApiRes.Custom().add(areaList);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "获取某城市下的所有县区")
    @GetMapping(value = "getCountyList/{cityId}")
    public ApiRes getCountyList(@PathVariable Long cityId){
        try{
            SrArea city = areaService.selectById(cityId);
            if(city==null){
                return ApiRes.Custom().failure(ApiStatus.AREA_NOT_EXIST);
            }
            Map<String,Object> map = Maps.newHashMap();
            map.put("level",3);
            map.put("code",city.getCode().substring(0,4));
            List<SrArea> areaList = areaService.findAllByConditions(map);
            if (areaList.size()<1){
                //如果是有县区升级为县级市，或者少数名族自治区州，第三级仍显示市区的名称
                SrArea area = areaService.selectById(cityId);
                areaList.add(area);
            }
            return ApiRes.Custom().add(areaList);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "用户关注")
    @ApiImplicitParam(name = "followId",value = "关注对象",required = true,paramType = "query")
    @PostMapping("/followOneUser")
    public ApiRes followOneUser(Long followId){
        try{
            userManage.followOneUser(followId,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "获取当前城市")
    @GetMapping("/getNowWhere")
    public ApiRes getNowWhere(Double longitude, Double latitude){
        try{
            SrArea area = areaManage.getNowWhere(longitude, latitude);
            return ApiRes.Custom().addData(area);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }


}
