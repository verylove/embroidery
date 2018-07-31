package cn.wind.xboot.controller.app.homePage;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.common.utils.ObjectMapperUtils;
import cn.wind.db.ar.entity.*;
import cn.wind.db.ar.service.*;
import cn.wind.xboot.controller.app.AppBaseController;
import cn.wind.xboot.dto.app.ar.arUserOtTattooDto;
import cn.wind.xboot.enums.AduitType;
import cn.wind.xboot.enums.IdentityType;
import cn.wind.xboot.service.app.CXArOtTattooManage;
import cn.wind.xboot.service.app.CXArUserManage;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.app.ar.ArUserOtEvaluatesVo;
import cn.wind.xboot.vo.app.ar.ArUserOtGreatNumVo;
import cn.wind.xboot.vo.app.ar.ArUserOtTattooVo;
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
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/17 16:38
 * @Description:
 */
@Api(value = "纹纹达人",tags = "纹纹达人")
@RestController
@RequestMapping("/api/app/OtTattoo")
public class ApiOutStandTattooController extends AppBaseController{

    @Autowired
    private CXArOtTattooManage otTattooManage;
    @Autowired
    private IArUserOtTattooService otTattooService;
    @Autowired
    private CXArUserManage userManage;
    @Autowired
    private IArUserOtPicService otPicService;
    @Autowired
    private IArUserOtGreatNumService otGreatNumService;
    @Autowired
    private IArUserService userService;
    @Autowired
    private IArUserOtEvaluatesService otEvaluatesService;
    @Autowired
    private IArStoreAduitService storeAduitService;


    @ApiOperation(value = "纹纹达人 分页")
    @GetMapping("/pageForOtTattoo")
    public ApiRes pageForOtTattoo(@ModelAttribute PageVo pageVo){
        try{
            Map<String,Object> map = Maps.newHashMap();
            Page<ArUserOtTattoo> page = otTattooService.findAllByConditions(pageVo.initPage(),map);

            Page<ArUserOtTattooVo> voPage = new Page<>(page.getCurrent(),page.getSize());
            voPage.setTotal(page.getTotal());

            Map<Long,ArUserOtTattoo> map2 = page.getRecords().stream().collect(Collectors.toMap(ArUserOtTattoo::getId, Function.identity()));

            List<ArUserOtTattooVo> vos = ObjectMapperUtils.mapAll(page.getRecords(),ArUserOtTattooVo.class);

            for(ArUserOtTattooVo vo:vos){
                ArUser user = map2.get(vo.getId()).getUser();
                vo.setAccount(user.getAccount());
                vo.setIcon(user.getIcon());
            }

            voPage.setRecords(vos);
            return ApiRes.Custom().addData(voPage);

        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "纹纹达人 详情")
    @PostMapping("/DetailForOtTattoo")
    public ApiRes DetailForOtTattoo(Long otTattooId){
        try{
            Map<String,Object> map = Maps.newHashMap();
            map.put("otTattooId",otTattooId);
            ArUserOtTattoo otTattoo = otTattooService.findOneByConditions(map);
            ArUserOtTattooVo vo = new ArUserOtTattooVo();
            BeanUtils.copyProperties(otTattoo,vo);

            ArUser user=otTattoo.getUser();
            vo.setAccount(user.getAccount());
            vo.setIcon(user.getIcon());
            if(user.getStoreStatus()==3){
                vo.setAduit(AduitType.STOREADUIT.getaType());
            }else if(user.getSignStatus()==3){
                vo.setAduit(AduitType.SIGNADUIT.getaType());
            }else if(user.getNameStatus()==3){
                vo.setAduit(AduitType.NAMEADUIT.getaType());
            }
            vo.setPerLevel(user.getPerLevel());
            //2.名片
            if(user.getStoreStatus()==3){
                // TODO 获取店铺名
                ArStoreAduit s = storeAduitService.selectById(user.getStoreId());
                vo.setBusinessCard(s.getStoreName());
            }else if(user.getIdentity()==1){
                vo.setBusinessCard(IdentityType.TATTOO.getType());
            }else {
                vo.setBusinessCard(IdentityType.LOVERS.getType());
            }
            List<ArUserOtPic> pics = otPicService.findAllByOtTattooId(otTattoo.getId());
            //3.图片
            vo.setPics(pics);

            //4.是否点过赞
            if(otTattooManage.IsGreatForOtTattoo(otTattoo.getId(),getUserId())){
                vo.setIsGreat(1);
            }else {
                vo.setIsGreat(0);
            }
            //5.是否关注
            if(userManage.IsFollowInUserId(getUserId(),otTattoo.getUserId())){
                vo.setIsCollection(1);
            }else {
                vo.setIsCollection(0);
            }
            //6.观看人数加一
            otTattoo.setWatchNum(otTattoo.getWatchNum()+1);
            otTattooService.updateById(otTattoo);
            return ApiRes.Custom().addData(vo);

        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "纹纹达人点赞人 分页")
    @ApiImplicitParam(name = "otTattooId",value = "纹纹达人ID",dataType = "Long",required = true,paramType = "query")
    @GetMapping("/greatersForOtTattoo")
    public ApiRes greatersForOtTattoo(Long otTattooId,@ModelAttribute PageVo<ArUserOtGreatNum> pageVo){
        try{
            List<String> sort = Lists.newArrayList();
            sort.add("num,desc");
            pageVo.setSort(sort);
            EntityWrapper<ArUserOtGreatNum> ew=new EntityWrapper<ArUserOtGreatNum>();
            if(otTattooId!=null){
                ew.eq("ot_tattoo_id",otTattooId);
            }
            Page<ArUserOtGreatNum> list =otGreatNumService.selectPage(pageVo.initPage(),ew);
            List<ArUserOtGreatNum> nums = list.getRecords();
            List<Long> userIds = nums.stream().map(ArUserOtGreatNum::getUserId).collect(Collectors.toList());
            List<ArUser> users = userService.findAllByIdIn(userIds);
            Map<Long,ArUser> map = users.stream().collect(Collectors.toMap(ArUser::getId,Function.identity()));

            Page<ArUserOtGreatNumVo> voPage = new Page<>(list.getCurrent(),list.getSize());
            voPage.setTotal(list.getTotal());
            List<ArUserOtGreatNumVo> vos = Lists.newArrayList();
            for(ArUserOtGreatNum num:nums){
                ArUserOtGreatNumVo vo = new ArUserOtGreatNumVo();
                BeanUtils.copyProperties(num,vo);
                vo.setAccount(map.get(num.getUserId()).getAccount());
                vo.setIcon(map.get(num.getUserId()).getIcon());
                vos.add(vo);
            }
            voPage.setRecords(vos);
            return ApiRes.Custom().addData(voPage);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "纹纹达人评论 分页")
    @ApiImplicitParam(name = "otTattooId",value = "纹纹达人ID",dataType = "Long",required = true,paramType = "query")
    @GetMapping("/evaluatesForOtTattoo")
    public ApiRes evaluatesForOtTattoo(Long otTattooId,@ModelAttribute PageVo pageVo){
        try{
            Map<String,Object> map = Maps.newHashMap();
            map.put("otTattooId",otTattooId);
            map.put("type",1);
            Page<ArUserOtEvaluates> page = otEvaluatesService.findAllByConditions(pageVo.initPage(),map);
            Page<ArUserOtEvaluatesVo> voPage = new Page<>(page.getCurrent(),page.getSize());
            voPage.setTotal(page.getTotal());

            Map<Long,ArUserOtEvaluates> map2 = page.getRecords().stream().collect(Collectors.toMap(ArUserOtEvaluates::getId, Function.identity()));

            List<ArUserOtEvaluatesVo> ArUserOtEvaluatesVos = ObjectMapperUtils.mapAll(page.getRecords(), ArUserOtEvaluatesVo.class);
            for(ArUserOtEvaluatesVo vo:ArUserOtEvaluatesVos){
                ArUser u = map2.get(vo.getId()).getUser();
                vo.setIcon(u.getIcon());
                vo.setAccount(u.getAccount());
                //子级评论
                Map<String,Object> map3= Maps.newHashMap();
                map3.put("replyId",vo.getId());
                map3.put("type",2);
                map3.put("level",2);
                map3.put("otTattooId",otTattooId);
                ArUserOtEvaluates childEvaluates = otEvaluatesService.findOneInSecondEvaluate(map3);
                vo.setEvaluterId(childEvaluates.getUserId());
                vo.setEvaluterAccount(childEvaluates.getUser().getAccount());
            }
            voPage.setRecords(ArUserOtEvaluatesVos);
            return ApiRes.Custom().addData(voPage);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "纹纹达人评论回复 分页")
    @ApiImplicitParam(name = "otEvaluateId",value = "纹纹达人评论ID",dataType = "Long",required = true,paramType = "query")
    @GetMapping("/pageInEvaluateForOtTattoo")
    public ApiRes pageInEvaluateForOtTattoo(Long otEvaluateId,@ModelAttribute PageVo pageVo){
        try{
            Map<String,Object> map4= Maps.newHashMap();
            map4.put("otEvaluateId",otEvaluateId);
            ArUserOtEvaluates otEvaluatesMain = otEvaluatesService.findOneInSecondEvaluate(map4);
            if(otEvaluatesMain==null){
                return ApiRes.Custom().failure(ApiStatus.SP_EVALUATE_NOT_EXIST);
            }

            Map<String,Object> map= Maps.newHashMap();
            map.put("otTattooId",otEvaluatesMain.getOtTattooId());
            map.put("type",2);
            map.put("level",otEvaluatesMain.getLevel()+1);
            map.put("replyId",otEvaluateId);
            Page<ArUserOtEvaluates> page = otEvaluatesService.findAllByConditions(pageVo.initPage(),map);

            Page<ArUserOtEvaluatesVo> voPage = new Page<>(page.getCurrent(),page.getSize());
            voPage.setTotal(page.getTotal());

            Map<Long,ArUserOtEvaluates> map2 = page.getRecords().stream().collect(Collectors.toMap(ArUserOtEvaluates::getId, Function.identity()));

            List<ArUserOtEvaluatesVo> ArUserOtEvaluatesVos = ObjectMapperUtils.mapAll(page.getRecords(), ArUserOtEvaluatesVo.class);
            for(ArUserOtEvaluatesVo vo:ArUserOtEvaluatesVos){
                ArUser u = map2.get(vo.getId()).getUser();
                vo.setIcon(u.getIcon());
                vo.setAccount(u.getAccount());
                //子级评论
                Map<String,Object> map3= Maps.newHashMap();
                map3.put("otTattooId",otEvaluatesMain.getOtTattooId());
                map3.put("replyId",vo.getId());
                map3.put("type",2);
                map3.put("level",otEvaluatesMain.getLevel()+2);
                ArUserOtEvaluates childEvaluates = otEvaluatesService.findOneInSecondEvaluate(map3);
                vo.setEvaluterId(childEvaluates.getUserId());
                vo.setEvaluterAccount(childEvaluates.getUser().getAccount());
            }
            voPage.setRecords(ArUserOtEvaluatesVos);

            ArUserOtEvaluatesVo evaluatesVo = new ArUserOtEvaluatesVo();
            BeanUtils.copyProperties(otEvaluatesMain,evaluatesVo);
            ArUser user = userService.selectById(evaluatesVo.getUserId());
            evaluatesVo.setAccount(user.getAccount());
            evaluatesVo.setIcon(user.getIcon());

            Map<String,Object> result = Maps.newHashMap();
            result.put("main",evaluatesVo);
            result.put("secondary",voPage);

            return ApiRes.Custom().addData(result);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }


    @ApiOperation(value = "纹纹达人 发布")
    @PostMapping("/publishOtTattoo")
    public ApiRes publishOtTattoo(arUserOtTattooDto dto){
        try{
            // TODO 验证是否具有爱好者权限
            //1.用户是否未实名认证
            if(userManage.IsNameAduit(getUserId())){
                return ApiRes.Custom().failure(ApiStatus.NAME_ALREADY_ADUIT);
            }

            otTattooManage.publishOtTattoo(dto,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "纹纹达人点赞")
    @ApiImplicitParam(name = "otTattooId",value = "纹纹达人ID",required = true,paramType = "query")
    @PostMapping(value = "/greatInOtTattoo")
    public ApiRes greatInOtTattoo(Long otTattooId){
        try{
            //1.金额是否充足
            if(!userManage.IsEnoughForBalance(getUserId(),new BigDecimal("1.00"))){
                return ApiRes.Custom().failure(ApiStatus.BALANCE_NOT_ENOUGH);
            }

            otTattooManage.greatInOtTattoo(otTattooId,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "纹纹达人评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "otTattooId",value = "纹纹达人ID",dataType = "Long",required = true,paramType = "query"),
            @ApiImplicitParam(name = "content",value = "纹纹达人评论内容",dataType = "String",required = true,paramType = "query")
    })
    @PostMapping(value = "/evaluateInOtTattoo")
    public ApiRes evaluateInOtTattoo(Long otTattooId,String content){
        try{
            otTattooManage.evaluateInOtTattoo(otTattooId,content,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "点赞纹纹达人评论")
    @ApiImplicitParam(name = "otEvaluateId",value = "该评论ID",dataType = "Long",required = true,paramType = "query")
    @PostMapping(value = "/greatInEvaluateForOtTattoo")
    public ApiRes greatInEvaluateForOtTattoo(Long otEvaluateId){
        try{
            //1.金额是否充足
            if(!userManage.IsEnoughForBalance(getUserId(),new BigDecimal("1.00"))){
                return ApiRes.Custom().failure(ApiStatus.BALANCE_NOT_ENOUGH);
            }

            otTattooManage.greatInEvaluateForOtTattoo(otEvaluateId,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "评论纹纹达人评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "otEvaluateId",value = "该评论ID",dataType = "Long",required = true,paramType = "query"),
            @ApiImplicitParam(name = "content",value = "评论该评论的内容",dataType = "String",required = true,paramType = "query")
    })
    @PostMapping(value = "/evaluateInEvaluateForOtTattoo")
    public ApiRes evaluateInEvaluateForOtTattoo(Long otEvaluateId,String content){
        try{
            otTattooManage.evaluateInEvaluateForOtTattoo(otEvaluateId,getUserId(),content);
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }
}
