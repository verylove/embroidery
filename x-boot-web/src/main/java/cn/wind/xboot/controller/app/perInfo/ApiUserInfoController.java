package cn.wind.xboot.controller.app.perInfo;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.common.utils.DateUtil;
import cn.wind.common.utils.ObjectMapperUtils;
import cn.wind.db.ar.entity.*;
import cn.wind.db.ar.service.*;
import cn.wind.db.sr.entity.SrLevels;
import cn.wind.db.sr.service.ISrLevelsService;
import cn.wind.xboot.controller.app.AppBaseController;
import cn.wind.xboot.enums.AduitType;
import cn.wind.xboot.enums.IdentityType;
import cn.wind.xboot.service.app.CXArUserManage;
import cn.wind.xboot.service.app.CXArUserSignManage;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.app.ar.ArUserDailySentimentVo;
import cn.wind.xboot.vo.app.ar.ArUserLevelVo;
import cn.wind.xboot.vo.app.ar.ArUserVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    private CXArUserManage userManage;
    @Autowired
    private IArUserDailyRecordService dailyRecordService;
    @Autowired
    private IArUserFollowsService followsService;
    @Autowired
    private ISrLevelsService levelsService;
    @Autowired
    private IArStoreAduitService storeAduitService;
    @Autowired
    private IArUserLevelRecordService levelRecordService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @ApiOperation(value = "身份选择 注册完试用")
    @ApiImplicitParam(name = "type", value = "1-纹身师 0-爱好者", required = true, dataType = "Integer", paramType = "query")
    @PostMapping("/identitySelect")
    public ApiRes identitySelect(Integer type){
        try{
            ArUser user = userService.selectById(getUserId());
            if(user==null){
                return ApiRes.Custom().failure(ApiStatus.USER_NOT_EXIST);
            }
            userManage.identitySelect(user,type);
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "我的页面 我的信息")
    @ApiImplicitParam(name = "userId",value = "用户ID",dataType = "Long",required = true,paramType = "query")
    @GetMapping("/userInfo")
    public ApiRes userInfo (Long userId){
        ArUser user = userService.selectById(userId);
        if(user==null){
            return ApiRes.Custom().failure(ApiStatus.USER_NOT_EXIST);
        }
        ArUserVo userVo = new ArUserVo();
        BeanUtils.copyProperties(user,userVo);
        //1.名片
        if(user.getStoreStatus()==3){
            // TODO 获取店铺名
            ArStoreAduit s = storeAduitService.selectById(user.getStoreId());
            userVo.setBusinessCard(s.getStoreName());
        }else if(user.getIdentity()==1){
            userVo.setBusinessCard(IdentityType.TATTOO.getType());
        }else {
            userVo.setBusinessCard(IdentityType.LOVERS.getType());
        }
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
            return userSignManage.signIn(getUserId());
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure("签到异常");
        }
    }

    @ApiOperation(value = "用户个人主页")
    @ApiImplicitParam(name = "userId",value = "用户名",required = true,paramType = "query")
    @GetMapping("/homePageInUser")
    public ApiRes homePageInUser(Long userId){
        try{
            ArUser user = userService.selectById(userId);
            if(user==null){
                return ApiRes.Custom().failure(ApiStatus.USER_NOT_EXIST);
            }
            ArUserVo userVo = new ArUserVo();
            BeanUtils.copyProperties(user,userVo);

            //1.认证
            if(user.getStoreStatus()==3){
                userVo.setAduit(AduitType.STOREADUIT.getaType());
            }else if(user.getSignStatus()==3){
                userVo.setAduit(AduitType.SIGNADUIT.getaType());
            }else if(user.getNameStatus()==3){
                userVo.setAduit(AduitType.NAMEADUIT.getaType());
            }
            //2.名片
            if(user.getStoreStatus()==3){
                // TODO 获取店铺名
                ArStoreAduit s = storeAduitService.selectById(user.getStoreId());
                userVo.setBusinessCard(s.getStoreName());
            }else if(user.getIdentity()==1){
                userVo.setBusinessCard(IdentityType.TATTOO.getType());
            }else {
                userVo.setBusinessCard(IdentityType.LOVERS.getType());
            }

            //3.关注
            if(userId!=getUserId()){
                if(userManage.IsFollowInUserId(getUserId(),userId)){
                    userVo.setIsCollection(1);
                }else {
                    userVo.setIsCollection(0);
                }
            }

            return ApiRes.Custom().addData(userVo);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "用户人气详情")
    @GetMapping("/InfoInSentiment")
    public ApiRes InfoInSentiment(){
        try{
            ArUser user = userService.selectById(getUserId());
            if(user==null){
                return ApiRes.Custom().failure(ApiStatus.USER_NOT_EXIST);
            }
            ArUserDailySentimentVo dailySentimentVo = new ArUserDailySentimentVo();
            LocalDate date = LocalDate.now();
            ArUserDailyRecord dailyRecord = dailyRecordService.findOneByUserIdAndDate(getUserId(),date);
            if(dailyRecord == null){
                return ApiRes.Custom().addData(dailySentimentVo);
            }
            BeanUtils.copyProperties(dailyRecord,dailySentimentVo);
            dailySentimentVo.setIcon(user.getIcon());
            dailySentimentVo.setSentimentNum(user.getSentimentNum());
            return ApiRes.Custom().addData(dailySentimentVo);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "用户关注页面")
    @GetMapping("/pageForMyFocus")
    public ApiRes pageForMyFocus(@ModelAttribute PageVo<ArUserFollows> pageVo){
        try{
            List<String> sort = Lists.newArrayList();
            sort.add("createTime,desc");
            pageVo.setSort(sort);
            EntityWrapper<ArUserFollows> ew=new EntityWrapper<ArUserFollows>();
            ew.eq("user_id",getUserId()).and().eq("status",1);
            Page<ArUserFollows> list =followsService.selectPage(pageVo.initPage(),ew);

            List<Long> userIds = list.getRecords().stream().map(ArUserFollows::getFollowId).collect(Collectors.toList());

            Page<ArUserVo> voPage = new Page<>(list.getCurrent(),list.getSize());
            voPage.setTotal(list.getTotal());

            List<ArUser> users = userService.findAllByIdIn(userIds);

            List<ArUserVo> vos = ObjectMapperUtils.mapAll(users,ArUserVo.class);

            voPage.setRecords(vos);
            return ApiRes.Custom().addData(voPage);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "用户粉丝页面")
    @GetMapping("/pageForMyFollow")
    public ApiRes pageForMyFollow(@ModelAttribute PageVo<ArUserFollows> pageVo){
        try{
            List<String> sort = Lists.newArrayList();
            sort.add("createTime,desc");
            pageVo.setSort(sort);
            EntityWrapper<ArUserFollows> ew=new EntityWrapper<ArUserFollows>();
            ew.eq("follow_id",getUserId()).and().eq("status",1);
            Page<ArUserFollows> list =followsService.selectPage(pageVo.initPage(),ew);

            List<Long> userIds = list.getRecords().stream().map(ArUserFollows::getUserId).collect(Collectors.toList());

            Page<ArUserVo> voPage = new Page<>(list.getCurrent(),list.getSize());
            voPage.setTotal(list.getTotal());

            List<ArUser> users = userService.findAllByIdIn(userIds);

            List<ArUserVo> vos = ObjectMapperUtils.mapAll(users,ArUserVo.class);
            for(ArUserVo vo:vos){
                if(userManage.IsFollowInUserId(getUserId(),vo.getId())){
                    vo.setIsCollection(1);
                }else {
                    vo.setIsCollection(0);
                }
            }

            voPage.setRecords(vos);
            return ApiRes.Custom().addData(voPage);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "用户等级")
    @GetMapping("/levelInfo")
    public ApiRes levelInfo(){
        try{
            ArUser user = userService.selectById(getUserId());
            if(user==null){
                return ApiRes.Custom().failure(ApiStatus.USER_NOT_EXIST);
            }

            ArUserLevelVo levelVo = new ArUserLevelVo();
            levelVo.setPerLevel(user.getPerLevel());
            levelVo.setActiveNum(user.getActiveNum());
            levelVo.setCharmNum(user.getCharmNum());
            levelVo.setWealthNum(user.getWealthNum());

            SrLevels levels = levelsService.findOneByLevel(user.getPerLevel());
            levelVo.setNowLevelNum(levels.getScore());

            SrLevels nextLevels = levelsService.findOneByLevel(user.getPerLevel()+1);
            if(nextLevels==null){
                levelVo.setNextLevelNum(levels.getScore());
            }else {
                levelVo.setNextLevelNum(nextLevels.getScore());
            }

            LocalDate yeDate = LocalDate.now().minusDays(1);
            ArUserDailyRecord dailyRecord = dailyRecordService.findOneByUserIdAndDate(getUserId(),yeDate);
            if(dailyRecord==null){
                levelVo.setYdPlusNum(0L);
            }else {
                Long allNum = dailyRecord.getDailyActiveNum()+dailyRecord.getDailyCharmNum()+dailyRecord.getDailyWealthNum();
                levelVo.setYdPlusNum(allNum);
            }

            return ApiRes.Custom().addData(levelVo);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "用户昨日加分")
    @GetMapping("/ydPlusNum")
    public ApiRes ydPlusNum(){
        try{
            Map<String,Object> map = Maps.newHashMap();
            map.put("userId",getUserId());
            List<ArUserLevelRecord> levelRecords = levelRecordService.findAllByConditionsYesterDay(map);
            return ApiRes.Custom().addData(levelRecords);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "修改登录密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPass",value = "旧密码",dataType = "String",required = true,paramType = "query"),
            @ApiImplicitParam(name = "code",value = "验证码",dataType = "String",required = true,paramType = "query"),
            @ApiImplicitParam(name = "newPassOne",value = "新密码",dataType = "String",required = true,paramType = "query"),
            @ApiImplicitParam(name = "newPassTwo",value = "确认新密码",dataType = "String",required = true,paramType = "query")
    })
    @PostMapping("/resetPassword")
    public ApiRes resetPassword(String oldPass,String code,String newPassOne,String newPassTwo){
        try{
            //1.判断密码是否一致
            ArUser user = userService.selectById(getUserId());
            if(!user.getPassword().equals(oldPass)){
                return ApiRes.Custom().failure(ApiStatus.PASSWORD_ERROR);
            }
            //2.判断验证码是否正确
            String oldCode = redisTemplate.opsForValue().get("CIXIU_PHONE_MODIFY:"+user.getPhone());
            if(oldCode==null){
                return ApiRes.Custom().failure(ApiStatus.VERIFICATION_CODE_TIMEOUT);
            }
            if(!code.equals(oldCode)){
                return ApiRes.Custom().failure(ApiStatus.VERIFICATION_CODE_FAIL);
            }
            //3.判断密码格式
            String passExp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,12}$";
            if(!newPassOne.matches(passExp)){
                return ApiRes.Custom().failure(ApiStatus.PASSWORD_FORMAT_ERROR);
            }
            //4.判断新密码是否一致
            if(!newPassOne.equals(newPassTwo)){
                return ApiRes.Custom().failure(ApiStatus.PASSWORD_NOT_CONSISTENT);
            }
            userManage.updateUserWithPassword(user.getPhone(),newPassOne);
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "新建支付密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "payPass",value = "支付密码",dataType = "String",required = true,paramType = "query"),
            @ApiImplicitParam(name = "code",value = "验证码",dataType = "String",required = true,paramType = "query")
    })
    @PostMapping("/setPayPass")
    public ApiRes setPayPass(String payPass,String code){
        try{
            ArUser user = userService.selectById(getUserId());
            //1.判断验证码是否正确
            String oldCode = redisTemplate.opsForValue().get("CIXIU_PHONE_PAYPASS:"+user.getPhone());
            if(oldCode==null){
                return ApiRes.Custom().failure(ApiStatus.VERIFICATION_CODE_TIMEOUT);
            }
            if(!code.equals(oldCode)){
                return ApiRes.Custom().failure(ApiStatus.VERIFICATION_CODE_FAIL);
            }

            userManage.setPayPss(user,payPass);
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.PAY_PASS_SET_ERROR);
        }
    }

    @ApiOperation(value = "修改支付密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPayPass",value = "旧支付密码",dataType = "String",required = true,paramType = "query"),
            @ApiImplicitParam(name = "code",value = "验证码",dataType = "String",required = true,paramType = "query"),
            @ApiImplicitParam(name = "newPassOne",value = "新支付密码",dataType = "String",required = true,paramType = "query"),
            @ApiImplicitParam(name = "newPassTwo",value = "确认新密码",dataType = "String",required = true,paramType = "query")
    })
    @PostMapping("/reSetPayPass")
    public ApiRes reSetPayPass(String oldPayPass,String code,String newPassOne,String newPassTwo){
        try{
            //1.判断密码是否一致
            ArUser user = userService.selectById(getUserId());
            if(!user.getPayPass().equals(oldPayPass)){
                return ApiRes.Custom().failure(ApiStatus.PAY_PASS_ERROR);
            }
            //2.判断验证码是否正确
            String oldCode = redisTemplate.opsForValue().get("CIXIU_PHONE_PAYPASS:"+user.getPhone());
            if(oldCode==null){
                return ApiRes.Custom().failure(ApiStatus.VERIFICATION_CODE_TIMEOUT);
            }
            if(!code.equals(oldCode)){
                return ApiRes.Custom().failure(ApiStatus.VERIFICATION_CODE_FAIL);
            }
            //4.判断新密码是否一致
            if(!newPassOne.equals(newPassTwo)){
                return ApiRes.Custom().failure(ApiStatus.PAY_PASS_NOT_CONSISTENT);
            }
            userManage.setPayPss(user,newPassOne);
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.PAY_PASS_SET_ERROR);
        }
    }

    @ApiOperation(value = "忘记支付密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code",value = "验证码",dataType = "String",required = true,paramType = "query"),
            @ApiImplicitParam(name = "newPassOne",value = "新支付密码",dataType = "String",required = true,paramType = "query"),
            @ApiImplicitParam(name = "newPassTwo",value = "确认新密码",dataType = "String",required = true,paramType = "query")
    })
    @PostMapping("/forgetPayPass")
    public ApiRes forgetPayPass(String code,String newPassOne,String newPassTwo){
        try{
            //1.判断密码是否一致
            ArUser user = userService.selectById(getUserId());
            //2.判断验证码是否正确
            String oldCode = redisTemplate.opsForValue().get("CIXIU_PHONE_PAYPASS:"+user.getPhone());
            if(oldCode==null){
                return ApiRes.Custom().failure(ApiStatus.VERIFICATION_CODE_TIMEOUT);
            }
            if(!code.equals(oldCode)){
                return ApiRes.Custom().failure(ApiStatus.VERIFICATION_CODE_FAIL);
            }
            //4.判断新密码是否一致
            if(!newPassOne.equals(newPassTwo)){
                return ApiRes.Custom().failure(ApiStatus.PAY_PASS_NOT_CONSISTENT);
            }
            userManage.setPayPss(user,newPassOne);
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.PAY_PASS_SET_ERROR);
        }
    }
}
