package cn.wind.xboot.controller.app.homePage;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.common.utils.ObjectMapperUtils;
import cn.wind.db.ar.entity.*;
import cn.wind.db.ar.service.*;
import cn.wind.xboot.controller.app.AppBaseController;
import cn.wind.xboot.dto.app.ar.arUserSpTattooDto;
import cn.wind.xboot.enums.AduitType;
import cn.wind.xboot.enums.IdentityType;
import cn.wind.xboot.service.app.CXArSpTattooManage;
import cn.wind.xboot.service.app.CXArUserManage;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.app.ar.ArUserSpEvaluatesVo;
import cn.wind.xboot.vo.app.ar.ArUserSpGreatNumVo;
import cn.wind.xboot.vo.app.ar.ArUserSpTattooVo;
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
 * @Date: 2018/7/10 15:27
 * @Description:特价纹身
 */
@Api(value = "特价纹身",tags = "特价纹身")
@RestController
@RequestMapping("/api/app/spTattoo")
public class ApiSpecialTattooController extends AppBaseController{

    @Autowired
    private IArUserSpTattooService spTattooService;
    @Autowired
    private IArUserSpPicService spPicService;
    @Autowired
    private CXArSpTattooManage spTattooManage;
    @Autowired
    private CXArUserManage userManage;
    @Autowired
    private IArUserSpGreatNumService spGreatNumService;
    @Autowired
    private IArUserService userService;
    @Autowired
    private IArUserSpEvaluatesService spEvaluatesService;
    @Autowired
    private IArStoreAduitService storeAduitService;

    @ApiOperation(value = "特价纹身分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name="longitude",value = "经度",dataType = "Double",required = false,paramType = "query"),
            @ApiImplicitParam(name="latitude",value = "纬度",dataType = "Double",required = false,paramType = "query")
    })
    @GetMapping("/pageForTattoo")
    public ApiRes pageForTattoo(@RequestParam(value = "longitude",required = false)Double longitude,
                                @RequestParam(value = "latitude",required = false)Double latitude, @ModelAttribute PageVo pageVo){
        try{
            Page<ArUserSpTattoo> page;
            if(longitude==null||latitude==null){//全国
                page = spTattooService.findAll(pageVo.initPage(),Maps.newHashMap());
            }else {//附近
                Map<String,Object> map= Maps.newHashMap();

                map.put("longitude",longitude);
                map.put("latitude",latitude);
                page = spTattooService.findByCoordinates(pageVo.initPage(),map);
            }
            Page<ArUserSpTattooVo> pageResult = new Page<>(page.getCurrent(), page.getSize());
            pageResult.setTotal(page.getTotal());

            pageResult.setRecords(getVo(page.getRecords()));
            return ApiRes.Custom().addData(pageResult);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "特价纹身发表")
    @PostMapping(value = "/publishSpTattoo")
    public ApiRes publishSpTattoo(arUserSpTattooDto dto){
        try{
            //1.用户是否实名认证
            if(!userManage.IsNameAduit(getUserId())){
                return ApiRes.Custom().failure(ApiStatus.NAME_NO_ADUIT);
            }

            spTattooManage.publish(dto,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "特价纹身详情")
    @ApiImplicitParam(name = "spTattooId",value = "特价纹身ID",dataType = "Long",required = true,paramType = "query")
    @GetMapping(value = "/DetailForSpTattoo")
    public ApiRes DetailForSpTattoo(Long spTattooId){
        try{
            ArUserSpTattoo too = spTattooService.findOneById(spTattooId);
            ArUserSpTattooVo vo = new ArUserSpTattooVo();
            BeanUtils.copyProperties(too,vo);

            ArUser t = too.getUser();
            vo.setPerLevel(t.getPerLevel());
            vo.setAccount(t.getAccount());
            vo.setIcon(t.getIcon());
            //1.认证
            if(t.getStoreStatus()==3){
                vo.setAduit(AduitType.STOREADUIT.getaType());
            }else if(t.getSignStatus()==3){
                vo.setAduit(AduitType.SIGNADUIT.getaType());
            }else if(t.getNameStatus()==3){
                vo.setAduit(AduitType.NAMEADUIT.getaType());
            }
            //2.名片
            if(t.getStoreStatus()==3){
                // TODO 获取店铺名
                ArStoreAduit s = storeAduitService.selectById(t.getStoreId());
                vo.setBusinessCard(s.getStoreName());
            }else if(t.getIdentity()==1){
                vo.setBusinessCard(IdentityType.TATTOO.getType());
            }else {
                vo.setBusinessCard(IdentityType.LOVERS.getType());
            }
            List<ArUserSpPic> pics = spPicService.findAllByTattoId(vo.getId());
            //3.图片
            vo.setPics(pics);

            //4.是否点过赞
            if(spTattooManage.IsGreatForSpTattoo(spTattooId,getUserId())){
                vo.setIsGreat(1);
            }else {
                vo.setIsGreat(0);
            }
            //5.是否关注
            if(userManage.IsFollowInUserId(getUserId(),too.getUserId())){
                vo.setIsCollection(1);
            }else {
                vo.setIsCollection(0);
            }


            return ApiRes.Custom().addData(vo);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "特价纹身点赞人 分页")
    @ApiImplicitParam(name = "spTattooId",value = "特价纹身ID",required = true,paramType = "query")
    @GetMapping(value = "/greatersForSpTattoo")
    public ApiRes greatersForSpTattoo(Long spTattooId,@ModelAttribute PageVo<ArUserSpGreatNum> pageVo){
        try{
            List<String> sort = Lists.newArrayList();
            sort.add("num,desc");
            pageVo.setSort(sort);
            EntityWrapper<ArUserSpGreatNum> ew=new EntityWrapper<ArUserSpGreatNum>();
            if(spTattooId!=null){
                ew.eq("special_tattoo_id",spTattooId);
            }
            Page<ArUserSpGreatNum> list =spGreatNumService.selectPage(pageVo.initPage(),ew);
            List<ArUserSpGreatNum> nums = list.getRecords();
            Page<ArUserSpGreatNumVo> voPage = new Page<>(list.getCurrent(),list.getSize());
            if(nums==null || nums.size()<1){
                return ApiRes.Custom().addData(voPage);
            }
            List<Long> userIds = nums.stream().map(ArUserSpGreatNum::getUserId).collect(Collectors.toList());
            List<ArUser> users = userService.findAllByIdIn(userIds);
            Map<Long,ArUser> map = users.stream().collect(Collectors.toMap(ArUser::getId,Function.identity()));


            voPage.setTotal(list.getTotal());
            List<ArUserSpGreatNumVo> vos = Lists.newArrayList();
            for(ArUserSpGreatNum num:nums){
                ArUserSpGreatNumVo vo = new ArUserSpGreatNumVo();
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

    @ApiOperation(value = "特价纹身评论 分页")
    @ApiImplicitParam(name = "spTattooId",value = "特价纹身ID",required = true,paramType = "query")
    @GetMapping(value = "/evaluateInSpTattoo")
    public ApiRes evaluateInSpTattoo(Long spTattooId,@ModelAttribute PageVo pageVo){
        try{
            Map<String,Object> map= Maps.newHashMap();
            map.put("spTattooId",spTattooId);
            map.put("type",1);
            Page<ArUserSpEvaluates> page = spEvaluatesService.findAllByConditions(pageVo.initPage(),map);
            Page<ArUserSpEvaluatesVo> voPage = new Page<>(page.getCurrent(),page.getSize());
            voPage.setTotal(page.getTotal());

            Map<Long,ArUserSpEvaluates> map2 = page.getRecords().stream().collect(Collectors.toMap(ArUserSpEvaluates::getId, Function.identity()));

            List<ArUserSpEvaluatesVo> ArUserSpEvaluatesVos = ObjectMapperUtils.mapAll(page.getRecords(), ArUserSpEvaluatesVo.class);
            for(ArUserSpEvaluatesVo vo:ArUserSpEvaluatesVos){
                ArUser u = map2.get(vo.getId()).getUser();
                vo.setIcon(u.getIcon());
                vo.setAccount(u.getAccount());
                //子级评论
                Map<String,Object> map3= Maps.newHashMap();
                map3.put("replyId",vo.getId());
                map3.put("type",2);
                map3.put("level",2);
                map3.put("spTattooId",spTattooId);
                ArUserSpEvaluates childEvaluates = spEvaluatesService.findOneInSecondEvaluate(map3);
                if(childEvaluates != null){
                    vo.setEvaluterId(childEvaluates.getUserId());
                    vo.setEvaluterAccount(childEvaluates.getUser().getAccount());
                }
            }
            voPage.setRecords(ArUserSpEvaluatesVos);
            return ApiRes.Custom().addData(voPage);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "特价纹身评论回复 分页")
    @ApiImplicitParam(name = "spEvaluateId",value = "评论ID",required = true,paramType = "query")
    @GetMapping(value = "/pageInEvaluateForSpTattoo")
    public ApiRes pageInEvaluateForSpTattoo(Long spEvaluateId,@ModelAttribute PageVo pageVo){
        try{
            Map<String,Object> map4= Maps.newHashMap();
            map4.put("spEvaluateId",spEvaluateId);
            ArUserSpEvaluates spEvaluatesMain = spEvaluatesService.findOneInSecondEvaluate(map4);
            if(spEvaluatesMain==null){
                return ApiRes.Custom().failure(ApiStatus.SP_EVALUATE_NOT_EXIST);
            }

            Map<String,Object> map= Maps.newHashMap();
            map.put("spTattooId",spEvaluatesMain.getSpecialTattooId());
            map.put("type",2);
            map.put("level",spEvaluatesMain.getLevel()+1);
            map.put("replyId",spEvaluateId);
            Page<ArUserSpEvaluates> page = spEvaluatesService.findAllByConditions(pageVo.initPage(),map);

            Page<ArUserSpEvaluatesVo> voPage = new Page<>(page.getCurrent(),page.getSize());
            voPage.setTotal(page.getTotal());

            Map<Long,ArUserSpEvaluates> map2 = page.getRecords().stream().collect(Collectors.toMap(ArUserSpEvaluates::getId, Function.identity()));

            List<ArUserSpEvaluatesVo> ArUserSpEvaluatesVos = ObjectMapperUtils.mapAll(page.getRecords(), ArUserSpEvaluatesVo.class);
            for(ArUserSpEvaluatesVo vo:ArUserSpEvaluatesVos){
                ArUser u = map2.get(vo.getId()).getUser();
                vo.setIcon(u.getIcon());
                vo.setAccount(u.getAccount());
                //子级评论
                Map<String,Object> map3= Maps.newHashMap();
                map3.put("spTattooId",spEvaluatesMain.getSpecialTattooId());
                map3.put("replyId",vo.getId());
                map3.put("type",2);
                map3.put("level",spEvaluatesMain.getLevel()+2);
                ArUserSpEvaluates childEvaluates = spEvaluatesService.findOneInSecondEvaluate(map3);
                if(childEvaluates != null){
                    vo.setEvaluterId(childEvaluates.getUserId());
                    vo.setEvaluterAccount(childEvaluates.getUser().getAccount());
                }
            }
            voPage.setRecords(ArUserSpEvaluatesVos);

            ArUserSpEvaluatesVo evaluatesVo = new ArUserSpEvaluatesVo();
            BeanUtils.copyProperties(spEvaluatesMain,evaluatesVo);
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

    @ApiOperation(value = "特价纹身点赞")
    @ApiImplicitParam(name = "spTattooId",value = "特价纹身ID",required = true,paramType = "query")
    @PostMapping(value = "/greatInSpTattoo")
    public ApiRes greatInSpTattoo(Long spTattooId){
        try{
            //1.金额是否充足
            if(!userManage.IsEnoughForBalance(getUserId(),new BigDecimal("1.00"))){
                return ApiRes.Custom().failure(ApiStatus.BALANCE_NOT_ENOUGH);
            }
            spTattooManage.greatInSpTattoo(spTattooId,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "特价纹身评价")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "spTattooId",value = "特价纹身ID",dataType = "Long",required = true,paramType = "query"),
            @ApiImplicitParam(name = "content",value = "特价纹身评论内容",dataType = "String",required = true,paramType = "query")
    })
    @PostMapping(value = "/evaluateInSpTattoo")
    public ApiRes evaluateInSpTattoo(Long spTattooId,String content){
        try{
            spTattooManage.evaluateInSpTattoo(spTattooId,getUserId(),content);
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "特价纹身评论点赞")
    @ApiImplicitParam(name = "spEvaluateId",value = "特价纹身评论ID",required = true,paramType = "query")
    @PostMapping(value = "/greatInEvaluateForSpTattoo")
    public ApiRes greatInEvaluateForSpTattoo(Long spEvaluateId){
        try{
            //1.金额是否充足
            if(!userManage.IsEnoughForBalance(getUserId(),new BigDecimal("1.00"))){
                return ApiRes.Custom().failure(ApiStatus.BALANCE_NOT_ENOUGH);
            }
            spTattooManage.greatInEvaluateForSpTattoo(spEvaluateId,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "特价纹身评论回复")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "spEvaluateId",value = "点评的评论",required = true,dataType = "Long",paramType = "query"),
        @ApiImplicitParam(name = "content",value = "点评的内容",required = true,dataType = "String",paramType = "query")
    })
    @PostMapping(value = "/evaluateInEvaluateForSpTattoo")
    public ApiRes evaluateInEvaluateForSpTattoo(Long spEvaluateId,String content){
        try{
            spTattooManage.evaluateInEvaluateForSpTattoo(spEvaluateId,getUserId(),content);
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "特价纹身个人 分页 ")
    @ApiImplicitParam(name="userId",value = "用户ID",dataType = "Long",required = true,paramType = "query")
    @GetMapping("/pageForPerTattoo")
    public ApiRes pageForPerTattoo(Long userId,@ModelAttribute PageVo pageVo){
        try{
            Map<String,Object> map1 = Maps.newHashMap();
            map1.put("userId",userId);
            Page<ArUserSpTattoo> page = spTattooService.findAll(pageVo.initPage(),map1);
            Page<ArUserSpTattooVo> pageResult = new Page<>(page.getCurrent(), page.getSize());


            pageResult.setRecords(getVo(page.getRecords()));
            return ApiRes.Custom().addData(pageResult);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    public List<ArUserSpTattooVo> getVo(List<ArUserSpTattoo> spTattoos){
        Map<Long,ArUserSpTattoo> map = spTattoos.stream().collect(Collectors.toMap(ArUserSpTattoo::getId, Function.identity()));

        List<ArUserSpTattooVo> ArUserSpTattooVos = ObjectMapperUtils.mapAll(spTattoos, ArUserSpTattooVo.class);
        for(ArUserSpTattooVo vo:ArUserSpTattooVos){
            List<ArUserSpPic> pics = spPicService.findAllByTattoId(vo.getId());
            ArUser t = map.get(vo.getId()).getUser();
            vo.setPerLevel(t.getPerLevel());
            vo.setAccount(t.getAccount());
            vo.setIcon(t.getIcon());
            //1.认证
            if(t.getStoreStatus()==3){
                vo.setAduit(AduitType.STOREADUIT.getaType());
            }else if(t.getSignStatus()==3){
                vo.setAduit(AduitType.SIGNADUIT.getaType());
            }else if(t.getNameStatus()==3){
                vo.setAduit(AduitType.NAMEADUIT.getaType());
            }
            //2.名片
            if(t.getStoreStatus()==3){
                // TODO 获取店铺名
                ArStoreAduit s = storeAduitService.selectById(t.getStoreId());
                vo.setBusinessCard(s.getStoreName());
            }else if(t.getIdentity()==1){
                vo.setBusinessCard(IdentityType.TATTOO.getType());
            }else {
                vo.setBusinessCard(IdentityType.LOVERS.getType());
            }
            //3.图片
            vo.setPics(pics);

            //4.是否点过赞
            if(spTattooManage.IsGreatForSpTattoo(vo.getId(),getUserId())){
                vo.setIsGreat(1);
            }else {
                vo.setIsGreat(0);
            }
        }
        return ArUserSpTattooVos;
    }


}
