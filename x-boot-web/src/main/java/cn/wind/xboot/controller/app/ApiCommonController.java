package cn.wind.xboot.controller.app;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.db.sr.entity.SrArea;
import cn.wind.db.sr.service.ISrAreaService;
import cn.wind.xboot.service.app.CXArUserManage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
