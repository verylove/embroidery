package cn.wind.xboot.controller.app.login;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.common.token.AesUtility;
import cn.wind.db.ar.entity.ArUser;
import cn.wind.db.ar.service.IArUserService;
import cn.wind.xboot.controller.app.AppBaseController;
import cn.wind.xboot.service.app.CXArUserManage;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
@RequestMapping("/api")
public class ApiLoginController extends AppBaseController{

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private IArUserService userService;
    @Autowired
    private CXArUserManage userManage;

    @ApiOperation(value="用户注册",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "注册的用户手机号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "1-纹身师 0-爱好者", required = true, dataType = "Integer", paramType = "query")
    })
    @PostMapping("/open/register")
    public ApiRes register(String phone,String code,String password,Integer type){
        Map<String,Object> map = new HashMap<>();
        try{
            //1.判断手机格式
            String pattern = "^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\\d{8}$";
            if(!phone.matches(pattern)){
                return ApiRes.Custom().failure(ApiStatus.PHONE_FORMAT_ERROR);
            }
            //2.判断验证码是否正确
            String oldCode = redisTemplate.opsForValue().get("CIXIU_PHONE_REGISTER:"+phone);
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
            user = userManage.addUserByIdentity(phone,password,type);
            if(user==null){
                return ApiRes.Custom().failure(ApiStatus.REGISTER_FAIL);
            }
            //token
            String token = AesUtility.TokenAesEncrypt(Long.toString(user.getId()));
            redisTemplate.opsForValue().set("embroidery:"+user.getId(), token,7, TimeUnit.DAYS);
            map.put("token",token);
            map.put("user",user);
            return ApiRes.Custom().addData(map);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure("注册异常");
        }
    }

    @ApiOperation(value = "第三方登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "openId", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "类型 1-微信 2-QQ", required = true, dataType = "Integer", paramType = "query")
    })
    @PostMapping(value = "/open/thirdLogin")
    public ApiRes thirdLogin(String openId, Integer type){
        HashMap<String,Object> map =new HashMap<String,Object>();
        try {
            ArUser user;
            if(type == 1){
                user = userService.findOneByWxOpenId(openId);
            }else {
                user = userService.findOneByQqOpenId(openId);
            }
            //如果未授权注册，需要绑定手机号
            if(user==null){
                return ApiRes.Custom().failure(ApiStatus.BIND_NOT_EXIST);
            }

            String token = AesUtility.TokenAesEncrypt(Long.toString(user.getId()));
            //将token，user对象保存在缓存中
            redisTemplate.opsForValue().set("embroidery:"+user.getId(), token,7, TimeUnit.DAYS);
            map.put("user",user);
            map.put("token",token);
            return ApiRes.Custom().addData(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiRes.Custom().failure("登录异常");
        }
    }

    @ApiOperation("绑定手机号（第三方登录用）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId",value = "openId",required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "phone",value = "手机号",required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "code",value = "验证码",required = true, dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "type",value = "类型 1-微信 2-QQ",required = true, dataType = "Integer",paramType = "query")
    })
    @PostMapping("/open/binding")
    public ApiRes bindPhone(String openId, String phone,String code, Integer type){
        Map<String,Object> map = new HashMap<>();
        try{
            //1.判断手机格式
            String pattern = "^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\\d{8}$";
            if(!phone.matches(pattern)){
                return ApiRes.Custom().failure(ApiStatus.PHONE_FORMAT_ERROR);
            }
            //2.判断验证码是否正确
            String oldCode = redisTemplate.opsForValue().get("CIXIU_PHONE_BIND:"+phone);
            if(oldCode==null){
                return ApiRes.Custom().failure(ApiStatus.VERIFICATION_CODE_TIMEOUT);
            }
            if(!code.equals(oldCode)){
                return ApiRes.Custom().failure(ApiStatus.VERIFICATION_CODE_FAIL);
            }
            //3.判断该号码是否存在
            ArUser user = userService.findOneByPhone(phone);
            if(user==null){
                user = userManage.addUser(openId,phone,type);
            }else {
                user = userManage.updateUserWithOpenId(openId,user,type);
            }
            if(user==null){
                return ApiRes.Custom().failure(ApiStatus.THIRD_BIND_FAIL);
            }

            String token = AesUtility.TokenAesEncrypt(Long.toString(user.getId()));
            //将token，user对象保存在缓存中
            redisTemplate.opsForValue().set("embroidery:"+user.getId(), token,7, TimeUnit.DAYS);
            map.put("user",user);
            map.put("token",token);
            return ApiRes.Custom().addData(map);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure("绑定手机号异常");
        }
    }

    @ApiOperation(value = "忘记密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone",value = "手机号",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "code",value = "验证码",required = true,dataType = "String",paramType = "query"),
            @ApiImplicitParam(name = "password",value = "密码",required = true,dataType = "String",paramType = "query")
    })
    @PostMapping("/open/forgetPassword")
    public ApiRes forgetPw(String phone, String code, String password){
        try{
            //1.判断手机格式
            String pattern = "^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\\d{8}$";
            if(!phone.matches(pattern)){
                return ApiRes.Custom().failure(ApiStatus.PHONE_FORMAT_ERROR);
            }
            //2.判断验证码是否正确
            String oldCode = redisTemplate.opsForValue().get("CIXIU_PHONE_FORGET:"+phone);
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
            //4.修改密码
            userManage.updateUserWithPassword(phone,password);
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure("修改密码异常");
        }
    }

    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone",value = "手机号",dataType = "String",required = true,paramType = "query"),
            @ApiImplicitParam(name = "password",value = "密码",dataType = "String",required = true,paramType = "query")
    })
    @PostMapping("/open/login")
    public ApiRes login(String phone, String password){
        Map<String,Object> map = new HashMap<>();
        try {
            //1.账号是否存在
            ArUser user = userService.findOneByPhone(phone);
            if(user==null){
                return ApiRes.Custom().failure(ApiStatus.USER_NOT_EXIST);
            }
            //2.密码是否相等
            if(!password.equals(user.getPassword())){
                return ApiRes.Custom().failure(ApiStatus.PASSWORD_ERROR);
            }
            String token = AesUtility.TokenAesEncrypt(Long.toString(user.getId()));
            //将token，user对象保存在缓存中
            redisTemplate.opsForValue().set("embroidery:"+user.getId(), token,7, TimeUnit.DAYS);
            map.put("user",user);
            map.put("token",token);
            return ApiRes.Custom().addData(map);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure("登录异常");
        }
    }

    @ApiOperation(value = "快速登录")
    @PostMapping("/app/fastLogin")
    public ApiRes fastLogin(){
        HashMap<String,Object> map =new HashMap<String,Object>();
        try {
            ArUser user=userService.selectById(getUserId());
            String token= (String) redisTemplate.opsForValue().get("embroidery:"+user.getId());
            map.put("user",user);
            map.put("token",token);
            return ApiRes.Custom().addData(map);
        } catch (Exception e) {
            e.printStackTrace();
            return ApiRes.Custom().failure("登录异常");
        }
    }

}
