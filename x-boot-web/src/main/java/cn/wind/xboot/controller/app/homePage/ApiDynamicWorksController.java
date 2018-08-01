package cn.wind.xboot.controller.app.homePage;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.common.utils.ObjectMapperUtils;
import cn.wind.db.ar.entity.*;
import cn.wind.db.ar.service.*;
import cn.wind.xboot.controller.app.AppBaseController;
import cn.wind.xboot.dto.app.ar.arUserDyWorkDto;
import cn.wind.xboot.enums.AduitType;
import cn.wind.xboot.enums.IdentityType;
import cn.wind.xboot.service.app.CXArDyWorkManage;
import cn.wind.xboot.service.app.CXArUserManage;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.app.ar.ArUserDyEvaluatesVo;
import cn.wind.xboot.vo.app.ar.ArUserDyGreatNumVo;
import cn.wind.xboot.vo.app.ar.ArUserDyWorksVo;
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
 * @Date: 2018/7/18 18:34
 * @Description:
 */
@Api(value = "动态/作品管理",tags = "动态/作品管理")
@RestController
@RequestMapping("/api/app/dyWork")
public class ApiDynamicWorksController extends AppBaseController{

    @Autowired
    private CXArUserManage userManage;
    @Autowired
    private CXArDyWorkManage dyWorkManage;
    @Autowired
    private IArUserDyWorksService dyWorksService;
    @Autowired
    private IArUserDyPicService dyPicService;
    @Autowired
    private IArUserDyGreatNumService dyGreatNumService;
    @Autowired
    private IArUserService userService;
    @Autowired
    private IArUserDyEvaluatesService dyEvaluatesService;
    @Autowired
    private IArStoreAduitService storeAduitService;

    @ApiOperation(value = "动态或作品 发布")
    @PostMapping("/publishInDyWork")
    public ApiRes publishInDyWork(arUserDyWorkDto dto){
        try{
            if(dto.getType()!=2){
                //1.用户是否实名认证
                if(!userManage.IsNameAduit(getUserId())){
                    return ApiRes.Custom().failure(ApiStatus.NAME_NO_ADUIT);
                }
            }

            dyWorkManage.publishInDyWork(dto,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "同城纹身师 店铺 分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name="longitude",value = "经度",dataType = "Double",required = true,paramType = "query"),
            @ApiImplicitParam(name="latitude",value = "纬度",dataType = "Double",required = true,paramType = "query")
    })
    @GetMapping("/pageForStoreWork")
    public ApiRes pageForStoreWork(Double longitude, Double latitude, @ModelAttribute PageVo pageVo){
        try{
            Map<String,Object> map = Maps.newHashMap();
            map.put("longitude",longitude);
            map.put("latitude",latitude);
            Page<ArUserDyWorks> page = dyWorksService.findAllStoreByConditions(pageVo.initPage(),map);

            Page<ArUserDyWorksVo> voPage = new Page<>(page.getCurrent(),page.getSize());
            voPage.setTotal(page.getTotal());

            Map<Long,ArUserDyWorks> map1 = page.getRecords().stream().collect(Collectors.toMap(ArUserDyWorks::getId, Function.identity()));

            List<ArUserDyWorksVo> vos = ObjectMapperUtils.mapAll(page.getRecords(),ArUserDyWorksVo.class);

            for(ArUserDyWorksVo vo:vos){
                ArUser user = map1.get(vo.getId()).getUser();
                vo.setPerLevel(user.getPerLevel());
                vo.setAccount(user.getAccount());
                vo.setIcon(user.getIcon());
                //1.认证
                vo.setAduit(AduitType.STOREADUIT.getaType());
                //2.名片
                // TODO 获取店铺名
                ArStoreAduit s = storeAduitService.selectById(user.getStoreId());
                vo.setBusinessCard(s.getStoreName());
                //店铺地址
                vo.setStoreCity(user.getCity());
                vo.setCounty(user.getCounty());
                vo.setPerAddress(user.getPerAddress());

                List<ArUserDyPic> pics = dyPicService.findAllByDyWorksId(vo.getId());
                vo.setPics(pics);
            }
            voPage.setRecords(vos);
            return ApiRes.Custom().addData(voPage);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "同城纹身师 纹身师 分页")
    @ApiImplicitParams({
            @ApiImplicitParam(name="city",value = "所在城市",dataType = "Long",required = true,paramType = "query"),
            @ApiImplicitParam(name="longitude",value = "经度",dataType = "Double",required = true,paramType = "query"),
            @ApiImplicitParam(name="latitude",value = "纬度",dataType = "Double",required = true,paramType = "query")
    })
    @GetMapping("/pageForArtistWork")
    public ApiRes pageForArtistWork(Long city,Double longitude, Double latitude, @ModelAttribute PageVo pageVo){
        try{
            Map<String,Object> map = Maps.newHashMap();
            map.put("longitude",longitude);
            map.put("latitude",latitude);
            map.put("city",city);
            Page<ArUserDyWorks> page = dyWorksService.findAllArtistByConditions(pageVo.initPage(),map);

            Page<ArUserDyWorksVo> voPage = new Page<>(page.getCurrent(),page.getSize());
            voPage.setTotal(page.getTotal());

            voPage.setRecords(getVos2(page.getRecords()));
            return ApiRes.Custom().addData(voPage);

        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "我的关注 动态 分页")
    @GetMapping("/pageForFocusWorks")
    public ApiRes pageForFocusWorks(@ModelAttribute PageVo pageVo){
        try{
            Page<ArUserDyWorksVo> voPage = new Page<>();
            List<Long> followIds = userManage.getFollowIds(getUserId());
            if(followIds==null){
                return ApiRes.Custom().addData(voPage);
            }
            Map<String,Object> map = Maps.newHashMap();
            map.put("list",followIds);
            Page<ArUserDyWorks> page = dyWorksService.findAllDyFocusByConditions(pageVo.initPage(),map);

            voPage = new Page<>(page.getCurrent(),page.getSize());
            voPage.setTotal(page.getTotal());

            voPage.setRecords(getVos(page.getRecords()));
            return ApiRes.Custom().addData(voPage);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "圈子 动态 分页")
    @ApiImplicitParam(name = "type",value = "1-热门 2-最新",required = true,dataType = "Integer",paramType = "query")
    @GetMapping("/pageForCircleWorks")
    public ApiRes pageForCircleWorks(Integer type,@ModelAttribute PageVo pageVo){
        try{
            Map<String,Object> map = Maps.newHashMap();
            Page<ArUserDyWorks> page = new Page<>();
            if(type==1){
                page = dyWorksService.findAllCircleWorkOrderByTime(pageVo.initPage(),map);
            }else {
                page = dyWorksService.findAllCircleWorkOrderByGreatNum(pageVo.initPage(),map);
            }

            Page<ArUserDyWorksVo> voPage = new Page<>(page.getCurrent(),page.getSize());

            voPage.setTotal(page.getTotal());

            voPage.setRecords(getVos(page.getRecords()));
            return ApiRes.Custom().addData(voPage);

        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }


    private List<ArUserDyWorksVo> getVos(List<ArUserDyWorks> dyWorks){

        Map<Long,ArUserDyWorks> map = dyWorks.stream().collect(Collectors.toMap(ArUserDyWorks::getId,Function.identity()));

        List<ArUserDyWorksVo> vos = ObjectMapperUtils.mapAll(dyWorks,ArUserDyWorksVo.class);

        for(ArUserDyWorksVo vo:vos){
            ArUser user = map.get(vo.getId()).getUser();
            vo.setPerLevel(user.getPerLevel());
            vo.setAccount(user.getAccount());
            vo.setIcon(user.getIcon());
            //1.认证
            if(user.getStoreStatus()==3){
                vo.setAduit(AduitType.STOREADUIT.getaType());
            }else if(user.getSignStatus()==3){
                vo.setAduit(AduitType.SIGNADUIT.getaType());
            }else if(user.getNameStatus()==3){
                vo.setAduit(AduitType.NAMEADUIT.getaType());
            }
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
            //3.图片
            List<ArUserDyPic> pics = dyPicService.findAllByDyWorksId(vo.getId());
            vo.setPics(pics);
            //4.是否点赞
            if(dyWorkManage.IsGreatForDyWorks(vo.getId(),getUserId())){
                vo.setIsGreat(1);
            }else {
                vo.setIsGreat(0);
            }
            //作品店铺地址
            if(vo.getType()==3){
                vo.setStoreCity(user.getCity());
                vo.setCounty(user.getCounty());
                vo.setPerAddress(user.getPerAddress());
            }
        }
        return vos;
    }

    @ApiOperation(value = "圈子 动态/作品 详情")
    @ApiImplicitParam(name = "dyWorksId",value = "动态ID",dataType = "Long",required = true,paramType = "query")
    @GetMapping("/detailForDyWorks")
    public ApiRes detailForDyWorks(Long dyWorksId){
        try{
            Map<String,Object> map = Maps.newHashMap();
            map.put("dyWorksId",dyWorksId);
            ArUserDyWorks dyWorks = dyWorksService.findOneByConditions(map);
            ArUserDyWorksVo vo = new ArUserDyWorksVo();
            BeanUtils.copyProperties(dyWorks,vo);

            ArUser user=dyWorks.getUser();
            vo.setAccount(user.getAccount());
            vo.setIcon(user.getIcon());
            vo.setPerLevel(user.getPerLevel());
            if(user.getStoreStatus()==3){
                vo.setAduit(AduitType.STOREADUIT.getaType());
            }else if(user.getSignStatus()==3){
                vo.setAduit(AduitType.SIGNADUIT.getaType());
            }else if(user.getNameStatus()==3){
                vo.setAduit(AduitType.NAMEADUIT.getaType());
            }
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
            List<ArUserDyPic> pics = dyPicService.findAllByDyWorksId(dyWorksId);
            //3.图片
            vo.setPics(pics);

            //4.是否点过赞
            if(dyWorkManage.IsGreatForDyWorks(dyWorksId,getUserId())){
                vo.setIsGreat(1);
            }else {
                vo.setIsGreat(0);
            }
            //5.是否关注
            if(userManage.IsFollowInUserId(getUserId(),dyWorks.getUserId())){
                vo.setIsCollection(1);
            }else {
                vo.setIsCollection(0);
            }
            //作品店铺地址
            if(vo.getType()==3){
                vo.setStoreCity(user.getCity());
                vo.setCounty(user.getCounty());
                vo.setPerAddress(user.getPerAddress());
            }
            return ApiRes.Custom().addData(vo);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "动态/作品 点赞人 分页")
    @ApiImplicitParam(name = "dyWorksId",value = "动态作品ID",dataType = "Long",required = true,paramType = "query")
    @GetMapping("/pageInGreatersForDyWorks")
    public ApiRes pageInGreatersForDyWorks(Long dyWorksId,@ModelAttribute PageVo<ArUserDyGreatNum> pageVo){
        try{
            List<String> sort = Lists.newArrayList();
            sort.add("num,desc");
            pageVo.setSort(sort);
            EntityWrapper<ArUserDyGreatNum> ew=new EntityWrapper<ArUserDyGreatNum>();
            if(dyWorksId!=null){
                ew.eq("dy_works_id",dyWorksId);
            }
            Page<ArUserDyGreatNum> list =dyGreatNumService.selectPage(pageVo.initPage(),ew);
            List<ArUserDyGreatNum> nums = list.getRecords();
            List<Long> userIds = nums.stream().map(ArUserDyGreatNum::getUserId).collect(Collectors.toList());
            List<ArUser> users = userService.findAllByIdIn(userIds);
            Map<Long,ArUser> map = users.stream().collect(Collectors.toMap(ArUser::getId,Function.identity()));

            Page<ArUserDyGreatNumVo> voPage = new Page<>(list.getCurrent(),list.getSize());
            voPage.setTotal(list.getTotal());
            List<ArUserDyGreatNumVo> vos = Lists.newArrayList();
            for(ArUserDyGreatNum num:nums){
                ArUserDyGreatNumVo vo = new ArUserDyGreatNumVo();
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

    @ApiOperation(value = "动态作品 评论 分页")
    @ApiImplicitParam(name = "dyWorksId",value = "动态作品ID",dataType = "Long",required = true,paramType = "query")
    @GetMapping("/evaluateForDyWorks")
    public ApiRes evaluateForDyWorks(Long dyWorksId,@ModelAttribute PageVo pageVo){
        try{
            Map<String,Object> map= Maps.newHashMap();
            map.put("dyWorksId",dyWorksId);
            map.put("type",1);
            Page<ArUserDyEvaluates> page = dyEvaluatesService.findAllByConditions(pageVo.initPage(),map);
            Page<ArUserDyEvaluatesVo> voPage = new Page<>(page.getCurrent(),page.getSize());
            voPage.setTotal(page.getTotal());

            Map<Long,ArUserDyEvaluates> map2 = page.getRecords().stream().collect(Collectors.toMap(ArUserDyEvaluates::getId, Function.identity()));

            List<ArUserDyEvaluatesVo> ArUserDyEvaluatesVos = ObjectMapperUtils.mapAll(page.getRecords(), ArUserDyEvaluatesVo.class);
            for(ArUserDyEvaluatesVo vo:ArUserDyEvaluatesVos){
                ArUser u = map2.get(vo.getId()).getUser();
                vo.setIcon(u.getIcon());
                vo.setAccount(u.getAccount());
                //子级评论
                Map<String,Object> map3= Maps.newHashMap();
                map3.put("replyId",vo.getId());
                map3.put("type",2);
                map3.put("level",2);
                map3.put("dyWorksId",dyWorksId);
                ArUserDyEvaluates childEvaluates = dyEvaluatesService.findOneInSecondEvaluate(map3);
                if(childEvaluates != null){
                    vo.setEvaluterId(childEvaluates.getUserId());
                    vo.setEvaluterAccount(childEvaluates.getUser().getAccount());
                }

            }
            voPage.setRecords(ArUserDyEvaluatesVos);
            return ApiRes.Custom().addData(voPage);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "动态作品评论回复 分页")
    @ApiImplicitParam(name = "dyEvaluateId",value = "动态作品评论ID",dataType = "Long",required = true,paramType = "query")
    @GetMapping(value = "/pageInEvaluateForDyWorks")
    public ApiRes pageInEvaluateForDyWorks(Long dyEvaluateId,@ModelAttribute PageVo pageVo){
        try{
            Map<String,Object> map4= Maps.newHashMap();
            map4.put("dyEvaluateId",dyEvaluateId);
            ArUserDyEvaluates dyEvaluatesMain = dyEvaluatesService.findOneInSecondEvaluate(map4);
            if(dyEvaluatesMain==null){
                return ApiRes.Custom().failure(ApiStatus.SP_EVALUATE_NOT_EXIST);
            }

            Map<String,Object> map= Maps.newHashMap();
            map.put("dyWorksId",dyEvaluatesMain.getDyWorksId());
            map.put("type",2);
            map.put("level",dyEvaluatesMain.getLevel()+1);
            map.put("replyId",dyEvaluateId);
            Page<ArUserDyEvaluates> page = dyEvaluatesService.findAllByConditions(pageVo.initPage(),map);

            Page<ArUserDyEvaluatesVo> voPage = new Page<>(page.getCurrent(),page.getSize());
            voPage.setTotal(page.getTotal());

            Map<Long,ArUserDyEvaluates> map2 = page.getRecords().stream().collect(Collectors.toMap(ArUserDyEvaluates::getId, Function.identity()));

            List<ArUserDyEvaluatesVo> ArUserDyEvaluatesVos = ObjectMapperUtils.mapAll(page.getRecords(), ArUserDyEvaluatesVo.class);
            for(ArUserDyEvaluatesVo vo:ArUserDyEvaluatesVos){
                ArUser u = map2.get(vo.getId()).getUser();
                vo.setIcon(u.getIcon());
                vo.setAccount(u.getAccount());
                //子级评论
                Map<String,Object> map3= Maps.newHashMap();
                map3.put("dyWorksId",dyEvaluatesMain.getDyWorksId());
                map3.put("replyId",vo.getId());
                map3.put("type",2);
                map3.put("level",dyEvaluatesMain.getLevel()+2);
                ArUserDyEvaluates childEvaluates = dyEvaluatesService.findOneInSecondEvaluate(map3);
                if(childEvaluates != null){
                    vo.setEvaluterId(childEvaluates.getUserId());
                    vo.setEvaluterAccount(childEvaluates.getUser().getAccount());
                }
            }
            voPage.setRecords(ArUserDyEvaluatesVos);

            ArUserDyEvaluatesVo evaluatesVo = new ArUserDyEvaluatesVo();
            BeanUtils.copyProperties(dyEvaluatesMain,evaluatesVo);
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

    @ApiOperation(value = "动态作品 点赞")
    @ApiImplicitParam(name = "dyWorksId",value = "动态作品ID",dataType = "Long",required = true,paramType = "query")
    @PostMapping("/greatInDyWorks")
    public ApiRes greatInDyWorks(Long dyWorksId){
        try{
            //1.金额是否充足
            if(!userManage.IsEnoughForBalance(getUserId(),new BigDecimal("1.00"))){
                return ApiRes.Custom().failure(ApiStatus.BALANCE_NOT_ENOUGH);
            }

            dyWorkManage.greatInDyWorks(dyWorksId,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "动态作品 评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dyWorksId",value = "动态作品ID",dataType = "Long",required = true,paramType = "query"),
            @ApiImplicitParam(name = "content",value = "动态作品评论内容",dataType = "String",required = true,paramType = "query")
    })
    @PostMapping(value = "/evaluateInDyWorks")
    public ApiRes evaluateInDyWorks(Long dyWorksId,String content){
        try{
            dyWorkManage.evaluateInDyWorks(dyWorksId,content,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "评论动态作品评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "dyEvaluateId",value = "该评论ID",dataType = "Long",required = true,paramType = "query"),
            @ApiImplicitParam(name = "content",value = "评论该评论的内容",dataType = "String",required = true,paramType = "query")
    })
    @PostMapping(value = "/evaluateInEvaluateForDyWorks")
    public ApiRes evaluateInEvaluateForDyWorks(Long dyEvaluateId,String content){
        try{
            dyWorkManage.evaluateInEvaluateForDyWorks(dyEvaluateId,getUserId(),content);
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "点赞动态作品评论")
    @ApiImplicitParam(name = "dyEvaluateId",value = "该评论ID",dataType = "Long",required = true,paramType = "query")
    @PostMapping(value = "/greatInEvaluateForDyWorks")
    public ApiRes greatInEvaluateForDyWorks(Long dyEvaluateId){
        try{
            //1.金额是否充足
            if(!userManage.IsEnoughForBalance(getUserId(),new BigDecimal("1.00"))){
                return ApiRes.Custom().failure(ApiStatus.BALANCE_NOT_ENOUGH);
            }

            dyWorkManage.greatInEvaluateForDyWorks(dyEvaluateId,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "店长发布作品 分页")
    @ApiImplicitParam(name = "userId",value = "用户ID",required = true,dataType = "Long",paramType = "query")
    @GetMapping(value = "/pageForAllStoreWorks")
    public ApiRes pageForAllStoreWorks(Long userId, @ModelAttribute PageVo pageVo){
        try{
            Map<String,Object> map = Maps.newHashMap();
            map.put("userId",userId);
            Page<ArUserDyWorks> page = dyWorksService.findAllStoreWorksForManager(pageVo.initPage(),map);

            Page<ArUserDyWorksVo> voPage = new Page<>(page.getCurrent(),page.getSize());
            voPage.setTotal(page.getTotal());

            Map<Long,ArUserDyWorks> map1 = page.getRecords().stream().collect(Collectors.toMap(ArUserDyWorks::getId, Function.identity()));

            List<ArUserDyWorksVo> vos = ObjectMapperUtils.mapAll(page.getRecords(),ArUserDyWorksVo.class);

            for(ArUserDyWorksVo vo:vos){
                ArUser user = map1.get(vo.getId()).getUser();
                vo.setIcon(user.getIcon());
                //1.认证
                vo.setAduit(AduitType.STOREADUIT.getaType());
                //2.名片
                // TODO 获取店铺名
                ArStoreAduit s = storeAduitService.selectById(user.getStoreId());
                vo.setBusinessCard(s.getStoreName());
                List<ArUserDyPic> pics = dyPicService.findAllByDyWorksId(vo.getId());
                vo.setPics(pics);
            }
            voPage.setRecords(vos);
            return ApiRes.Custom().addData(voPage);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "个人发布作品 分页")
    @ApiImplicitParam(name = "userId",value = "用户ID",required = true,dataType = "Long",paramType = "query")
    @GetMapping(value = "/pageForAllPerWorks")
    public ApiRes pageForAllPerWorks(Long userId, @ModelAttribute PageVo pageVo){
        try{
            Map<String,Object> map = Maps.newHashMap();
            map.put("userId",userId);
            Page<ArUserDyWorks> page = dyWorksService.findAllStoreWorksForPerson(pageVo.initPage(),map);

            Page<ArUserDyWorksVo> voPage = new Page<>(page.getCurrent(),page.getSize());
            voPage.setTotal(page.getTotal());


            voPage.setRecords(getVos2(page.getRecords()));
            return ApiRes.Custom().addData(voPage);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    public List<ArUserDyWorksVo> getVos2(List<ArUserDyWorks> dyWorks){
        Map<Long,ArUserDyWorks> map1 = dyWorks.stream().collect(Collectors.toMap(ArUserDyWorks::getId, Function.identity()));

        List<ArUserDyWorksVo> vos = ObjectMapperUtils.mapAll(dyWorks,ArUserDyWorksVo.class);

        for(ArUserDyWorksVo vo:vos){
            ArUser user = map1.get(vo.getId()).getUser();
            vo.setPerLevel(user.getPerLevel());
            vo.setAccount(user.getAccount());
            vo.setIcon(user.getIcon());
            //1.认证
            if(user.getStoreStatus()==3){
                vo.setAduit(AduitType.STOREADUIT.getaType());
            }else if(user.getSignStatus()==3){
                vo.setAduit(AduitType.SIGNADUIT.getaType());
            }else if(user.getNameStatus()==3){
                vo.setAduit(AduitType.NAMEADUIT.getaType());
            }
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
            //3.图片
            List<ArUserDyPic> pics = dyPicService.findAllByDyWorksId(vo.getId());
            vo.setPics(pics);
            //4.是否点赞
            if(dyWorkManage.IsGreatForDyWorks(vo.getId(),getUserId())){
                vo.setIsGreat(1);
            }else {
                vo.setIsGreat(0);
            }
        }
        return vos;
    }

    @ApiOperation(value = "个人发布动态 分页")
    @ApiImplicitParam(name = "userId",value = "用户ID",required = true,dataType = "Long",paramType = "query")
    @GetMapping(value = "/pageForAllPerDynamic")
    public ApiRes pageForAllPerDynamic(Long userId, @ModelAttribute PageVo pageVo){
        try{
            Map<String,Object> map = Maps.newHashMap();
            map.put("userId",userId);
            Page<ArUserDyWorks> page = dyWorksService.findAllPerDynamic(pageVo.initPage(),map);

            Page<ArUserDyWorksVo> voPage = new Page<>(page.getCurrent(),page.getSize());
            voPage.setTotal(page.getTotal());

            voPage.setRecords(getVos2(page.getRecords()));
            return ApiRes.Custom().addData(voPage);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }
}
