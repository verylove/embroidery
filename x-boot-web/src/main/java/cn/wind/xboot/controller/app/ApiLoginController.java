package cn.wind.xboot.controller.app;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.db.ar.entity.ArUser;
import cn.wind.db.ar.service.IArUserService;
import cn.wind.xboot.service.appUser.CXArUserManage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Title: ApiController</p>
 * <p>Description: TODO</p>
 *
 * @author changzhaoliang
 * @version 1.0
 * @date 2018/6/19
 */
@Api(value = "登录注册api",tags = "登录注册api")
@RestController
@RequestMapping("/api/open")
public class ApiLoginController {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private IArUserService userService;
    @Autowired
    private CXArUserManage userManage;

    @ApiOperation(value="用户注册")
    @PostMapping("/register")
    public ApiRes register(@ApiParam("注册的用户手机号")String phone,@ApiParam("验证码")String code,@ApiParam("密码")String password){
        try{
            //1.判断手机格式
            String pattern = "^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\\d{8}$";
            if(!phone.matches(pattern)){
                return ApiRes.Custom().failure(ApiStatus.PHONE_FORMAT_ERROR);
            }
            //2.判断验证码是否正确
            String oldCode = redisTemplate.opsForValue().get("CIXIU_PHONE:"+phone);
            if(oldCode==null){
                return ApiRes.Custom().failure(ApiStatus.VERIFICATION_CODE_TIMEOUT);
            }
            if(!code.equals(oldCode)){
                return ApiRes.Custom().failure(ApiStatus.VERIFICATION_CODE_FAIL);
            }
            //3.判断密码格式
            String passExp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$";
            if(!password.matches(passExp)){
                return ApiRes.Custom().failure(ApiStatus.PASSWORD_FORMAT_ERROR);
            }
            //4.判断是否已被注册
            ArUser user = userService.findOneByPhone(phone);
            if(user!=null){
                return ApiRes.Custom().failure(ApiStatus.USER_ALREADY_EXIST);
            }
            //5.注册用户
            boolean result = userManage.addUser(phone,password);
            if(!result){
                return ApiRes.Custom().failure(ApiStatus.REGISTER_FAIL);
            }
            return ApiRes.Custom().addData(userService.register(admin));
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure("异常");
        }
    }

}
