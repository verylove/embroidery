package cn.wind.xboot.controller.app.perInfo;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.common.utils.DateUtil;
import cn.wind.db.ar.entity.ArUser;
import cn.wind.db.ar.entity.ArUserSignRecord;
import cn.wind.db.ar.service.IArUserService;
import cn.wind.db.ar.service.IArUserSignRecordService;
import cn.wind.xboot.controller.app.AppBaseController;
import cn.wind.xboot.service.appUser.CXArUserSignManage;
import cn.wind.xboot.vo.app.ar.ArUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/9 16:45
 * @Description:
 */
@Api(value = "用户个人信息管理",tags = "用户个人信息管理")
@RestController
@RequestMapping("/api/app/user")
public class ApiUserInfoController extends AppBaseController {

    @Autowired
    private IArUserService userService;
    @Autowired
    private IArUserSignRecordService signRecordService;
    @Autowired
    private CXArUserSignManage userSignManage;

    @ApiOperation(value = "我的页面")
    @GetMapping("/userInfo")
    public ApiRes userInfo (){
        ArUser user = userService.selectById(getUserId());
        if(user==null){
            return ApiRes.Custom().failure(ApiStatus.USER_NOT_EXIST);
        }
        ArUserVo userVo = new ArUserVo();
        BeanUtils.copyProperties(user,userVo);
        return ApiRes.Custom().addData(userVo);
    }

    @ApiOperation(value = "签到记录")
    @GetMapping("/signRecord")
    public ApiRes signRecord(){
        try{
            List<ArUserSignRecord> signRecords = signRecordService.findAllBetweenDaysAndUserId(getUserId(),DateUtil.getFirstDayByNow(),DateUtil.getLastDayByNow());
            return ApiRes.Custom().addData(signRecords);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "签到")
    @PostMapping("/signIn")
    public ApiRes signIn(){
        try{
            return ApiRes.Custom().addData(userSignManage.signIn(getUserId()));
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure("签到异常");
        }
    }


}
