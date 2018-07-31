package cn.wind.xboot.controller.app.homePage;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.common.utils.ObjectMapperUtils;
import cn.wind.db.ar.entity.*;
import cn.wind.db.ar.service.*;
import cn.wind.xboot.controller.app.AppBaseController;
import cn.wind.xboot.dto.app.ar.arUserSkGalleryDto;
import cn.wind.xboot.enums.AduitType;
import cn.wind.xboot.enums.IdentityType;
import cn.wind.xboot.service.app.CXArSkGalleryManage;
import cn.wind.xboot.service.app.CXArUserManage;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.app.ar.ArUserSkGalleryVo;
import cn.wind.xboot.vo.app.ar.ArUserSkGreatNumVo;
import cn.wind.xboot.vo.app.ar.ArUserSkEvaluatesVo;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/13 15:02
 * @Description:
 */
@Api(value = "图库找图管理",tags = "图库找图管理")
@RestController
@RequestMapping(value = "/api/app/skGallery")
public class ApiSeekGalleryController extends AppBaseController{

    @Autowired
    private CXArSkGalleryManage skGalleryManage;
    @Autowired
    private CXArUserManage userManage;
    @Autowired
    private IArUserSkLabelService skLabelService;
    @Autowired
    private IArUserSkGalleryService skGalleryService;
    @Autowired
    private IArUserSkPicService skPicService;
    @Autowired
    private IArUserSkGreatNumService skGreatNumService;
    @Autowired
    private IArUserService userService;
    @Autowired
    private IArUserSkEvaluatesService skEvaluatesService;
    @Autowired
    private IArStoreAduitService storeAduitService;

    @ApiOperation(value = "图库找图发布")
    @PostMapping(value = "/publishSkGallery")
    public ApiRes publishSkGallery(arUserSkGalleryDto dto){
        try{
            //1.用户是否实名认证
            if(!userManage.IsNameAduit(getUserId())){
                return ApiRes.Custom().failure(ApiStatus.NAME_NO_ADUIT);
            }

            skGalleryManage.publishSkGallery(dto,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "图库找图 标签")
    @GetMapping(value = "/getAllLabel")
    public ApiRes getAllLabel(){
        try{
            EntityWrapper<ArUserSkLabel> ew=new EntityWrapper<ArUserSkLabel>();
            ew.eq("level",1);
            List<ArUserSkLabel> labels = skLabelService.selectList(ew);
            Map<String,Object> map = Maps.newHashMap();
            List<Map<String,Object>> result = new ArrayList<>();
            for(ArUserSkLabel label:labels){
                map.put("main",label);
                ew = new EntityWrapper<ArUserSkLabel>();
                ew.eq("level",2).and().eq("pid",label.getId());
                List<ArUserSkLabel> labels2 = skLabelService.selectList(ew);
                map.put("secondary",labels2);
                result.add(map);
            }
            return ApiRes.Custom().addData(result);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "图库找图 分页")
    @GetMapping(value = "/pageForSkGallery")
    public ApiRes pageForSkGallery(@ModelAttribute PageVo pageVo){
        try{
            Page<ArUserSkGallery> page = skGalleryService.findAllByConditions(pageVo.initPage(),Maps.newHashMap());

            return ApiRes.Custom().addData(skGalleryToSkGalleryVo(page));
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "图库找图 检索")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "likeContent",value = "搜索内容",dataType = "String",required = true,paramType = "query"),
            @ApiImplicitParam(name = "labelId",value = "标签ID",dataType = "Integer",required = true,paramType = "query")
    })
    @GetMapping(value = "/searchInSkGallery")
    public ApiRes searchInSkGallery(String likeContent,Integer labelId,@ModelAttribute PageVo pageVo){
        try{
            Map<Object,Object> map = Maps.newHashMap();
            map.put("content",likeContent);
            map.put("labelId",labelId);
            Page<ArUserSkGallery> page = skGalleryService.findAllByConditions(pageVo.initPage(),map);

            return ApiRes.Custom().addData(skGalleryToSkGalleryVo(page));
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    /**
     * 图库找图格式转换Vo
     * @param page
     * @return
     */
    private Page<ArUserSkGalleryVo> skGalleryToSkGalleryVo(Page<ArUserSkGallery> page){
        Page<ArUserSkGalleryVo> voPage = new Page<>(page.getCurrent(),page.getSize());
        voPage.setTotal(page.getTotal());

        Map<Long,ArUserSkGallery> map = page.getRecords().stream().collect(Collectors.toMap(ArUserSkGallery::getId, Function.identity()));

        List<ArUserSkGalleryVo> vos = ObjectMapperUtils.mapAll(page.getRecords(),ArUserSkGalleryVo.class);
        for(ArUserSkGalleryVo vo:vos){
            ArUser user = map.get(vo.getId()).getUser();
            vo.setAccount(user.getAccount());
            vo.setIcon(user.getIcon());
            if(user.getStoreStatus()==3){
                vo.setAduit("店");
            }else if(user.getSignStatus()==3){
                vo.setAduit("签");
            }else if(user.getNameStatus()==3){
                vo.setAduit("实");
            }

            List<ArUserSkPic> pics = skPicService.findAllByGalleryId(vo.getId());
            vo.setPics(pics);
        }
        voPage.setRecords(vos);
        return voPage;
    }

    @ApiOperation(value = "图库找图详情")
    @ApiImplicitParam(name = "skGalleryId",value = "图库找图ID",required = true,paramType = "query")
    @PostMapping(value = "/DetailInSkGallery")
    public ApiRes DetailInSkGallery(Long skGalleryId){
        try{
            Map<String,Object> map = Maps.newHashMap();
            map.put("id",skGalleryId);
            ArUserSkGallery skGallery = skGalleryService.findOneByConditions(map);
            ArUserSkGalleryVo vo = new ArUserSkGalleryVo();
            BeanUtils.copyProperties(skGallery,vo);

            ArUser user=skGallery.getUser();
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
            List<ArUserSkPic> pics = skPicService.findAllByGalleryId(skGalleryId);
            //3.图片
            vo.setPics(pics);

            //4.是否点过赞
            if(skGalleryManage.IsGreatForSkGallery(skGalleryId,getUserId())){
                vo.setIsGreat(1);
            }else {
                vo.setIsGreat(0);
            }
            //5.是否关注
            if(userManage.IsFollowInUserId(getUserId(),skGallery.getUserId())){
                vo.setIsCollection(1);
            }else {
                vo.setIsCollection(0);
            }
            //6.观看人数加一
            skGallery.setWatchNum(skGallery.getWatchNum()+1);
            skGalleryService.updateById(skGallery);
            return ApiRes.Custom().addData(vo);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "图库找图点赞人 分页")
    @ApiImplicitParam(name = "skGalleryId",value = "图库找图ID",required =true,paramType = "query")
    @GetMapping(value = "/greatersForSkGallery")
    public ApiRes greatersForSkGallery(Long skGalleryId,@ModelAttribute PageVo<ArUserSkGreatNum> pageVo){
        try{
            List<String> sort = Lists.newArrayList();
            sort.add("num,desc");
            pageVo.setSort(sort);
            EntityWrapper<ArUserSkGreatNum> ew=new EntityWrapper<ArUserSkGreatNum>();
            if(skGalleryId!=null){
                ew.eq("seek_gallery_id",skGalleryId);
            }
            Page<ArUserSkGreatNum> list =skGreatNumService.selectPage(pageVo.initPage(),ew);
            List<ArUserSkGreatNum> nums = list.getRecords();
            List<Long> userIds = nums.stream().map(ArUserSkGreatNum::getUserId).collect(Collectors.toList());
            List<ArUser> users = userService.findAllByIdIn(userIds);
            Map<Long,ArUser> map = users.stream().collect(Collectors.toMap(ArUser::getId,Function.identity()));

            Page<ArUserSkGreatNumVo> voPage = new Page<>(list.getCurrent(),list.getSize());
            voPage.setTotal(list.getTotal());
            List<ArUserSkGreatNumVo> vos = Lists.newArrayList();
            for(ArUserSkGreatNum num:nums){
                ArUserSkGreatNumVo vo = new ArUserSkGreatNumVo();
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

    @ApiOperation(value = "图库选图评论 分页")
    @ApiImplicitParam(name = "skGalleryId",value = "图库找图ID",required = true,paramType = "query")
    @GetMapping(value = "/evaluatesForGallery")
    public ApiRes evaluatesForGallery(Long skGalleryId,@ModelAttribute PageVo pageVo){
        try{
            Map<String,Object> map= Maps.newHashMap();
            map.put("skGalleryId",skGalleryId);
            map.put("type",1);
            Page<ArUserSkEvaluates> page = skEvaluatesService.findAllByConditions(pageVo.initPage(),map);
            Page<ArUserSkEvaluatesVo> voPage = new Page<>(page.getCurrent(),page.getSize());
            voPage.setTotal(page.getTotal());

            Map<Long,ArUserSkEvaluates> map2 = page.getRecords().stream().collect(Collectors.toMap(ArUserSkEvaluates::getId, Function.identity()));

            List<ArUserSkEvaluatesVo> ArUserSkEvaluatesVos = ObjectMapperUtils.mapAll(page.getRecords(), ArUserSkEvaluatesVo.class);
            for(ArUserSkEvaluatesVo vo:ArUserSkEvaluatesVos){
                ArUser u = map2.get(vo.getId()).getUser();
                vo.setIcon(u.getIcon());
                vo.setAccount(u.getAccount());
                //子级评论
                Map<String,Object> map3= Maps.newHashMap();
                map3.put("replyId",vo.getId());
                map3.put("type",2);
                map3.put("level",2);
                map3.put("skGalleryId",skGalleryId);
                ArUserSkEvaluates childEvaluates = skEvaluatesService.findOneInSecondEvaluate(map3);
                vo.setEvaluterId(childEvaluates.getUserId());
                vo.setEvaluterAccount(childEvaluates.getUser().getAccount());
            }
            voPage.setRecords(ArUserSkEvaluatesVos);
            return ApiRes.Custom().addData(voPage);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "图库找图评论回复 分页")
    @ApiImplicitParam(name = "skEvaluateId",value = "图库找图评论ID",dataType = "Long",required = true,paramType = "query")
    @GetMapping(value = "/pageInEvaluateForSkGallery")
    public ApiRes pageInEvaluateForSkGallery(Long skEvaluateId,@ModelAttribute PageVo pageVo){
        try{
            Map<String,Object> map4= Maps.newHashMap();
            map4.put("skEvaluateId",skEvaluateId);
            ArUserSkEvaluates skEvaluatesMain = skEvaluatesService.findOneInSecondEvaluate(map4);
            if(skEvaluatesMain==null){
                return ApiRes.Custom().failure(ApiStatus.SP_EVALUATE_NOT_EXIST);
            }

            Map<String,Object> map= Maps.newHashMap();
            map.put("skGalleryId",skEvaluatesMain.getSeekGalleryId());
            map.put("type",2);
            map.put("level",skEvaluatesMain.getLevel()+1);
            map.put("replyId",skEvaluateId);
            Page<ArUserSkEvaluates> page = skEvaluatesService.findAllByConditions(pageVo.initPage(),map);

            Page<ArUserSkEvaluatesVo> voPage = new Page<>(page.getCurrent(),page.getSize());
            voPage.setTotal(page.getTotal());

            Map<Long,ArUserSkEvaluates> map2 = page.getRecords().stream().collect(Collectors.toMap(ArUserSkEvaluates::getId, Function.identity()));

            List<ArUserSkEvaluatesVo> ArUserSkEvaluatesVos = ObjectMapperUtils.mapAll(page.getRecords(), ArUserSkEvaluatesVo.class);
            for(ArUserSkEvaluatesVo vo:ArUserSkEvaluatesVos){
                ArUser u = map2.get(vo.getId()).getUser();
                vo.setIcon(u.getIcon());
                vo.setAccount(u.getAccount());
                //子级评论
                Map<String,Object> map3= Maps.newHashMap();
                map3.put("skGalleryId",skEvaluatesMain.getSeekGalleryId());
                map3.put("replyId",vo.getId());
                map3.put("type",2);
                map3.put("level",skEvaluatesMain.getLevel()+2);
                ArUserSkEvaluates childEvaluates = skEvaluatesService.findOneInSecondEvaluate(map3);
                vo.setEvaluterId(childEvaluates.getUserId());
                vo.setEvaluterAccount(childEvaluates.getUser().getAccount());
            }
            voPage.setRecords(ArUserSkEvaluatesVos);

            ArUserSkEvaluatesVo evaluatesVo = new ArUserSkEvaluatesVo();
            BeanUtils.copyProperties(skEvaluatesMain,evaluatesVo);
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

    @ApiOperation(value = "图库找图点赞")
    @ApiImplicitParam(name = "skGalleryId",value = "图库找图ID",required = true,paramType = "query")
    @PostMapping(value = "/greatInSkGallery")
    public ApiRes greatInSkGallery(Long skGalleryId){
        try{
            //1.金额是否充足
            if(!userManage.IsEnoughForBalance(getUserId(),new BigDecimal("1.00"))){
                return ApiRes.Custom().failure(ApiStatus.BALANCE_NOT_ENOUGH);
            }

            skGalleryManage.greatInSkGallery(skGalleryId,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "图库找图评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "skGalleryId",value = "图库找图ID",dataType = "Long",required = true,paramType = "query"),
            @ApiImplicitParam(name = "content",value = "图库找图评论内容",dataType = "String",required = true,paramType = "query")
    })
    @PostMapping(value = "/evaluateInSkGallery")
    public ApiRes evaluateInSkGallery(Long skGalleryId,String content){
        try{
            skGalleryManage.evaluateInSkGallery(skGalleryId,content,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "评论图库找图评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "skEvaluateId",value = "该评论ID",dataType = "Long",required = true,paramType = "query"),
            @ApiImplicitParam(name = "content",value = "评论该评论的内容",dataType = "String",required = true,paramType = "query")
    })
    @PostMapping(value = "/evaluateInEvaluateForSkGallery")
    public ApiRes evaluateInEvaluateForSkGallery(Long skEvaluateId,String content){
        try{
            skGalleryManage.evaluateInEvaluateForSkGallery(skEvaluateId,getUserId(),content);
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "点赞图库找图评论")
    @ApiImplicitParam(name = "skEvaluateId",value = "该评论ID",dataType = "Long",required = true,paramType = "query")
    @PostMapping(value = "/greatInEvaluateForSkGallery")
    public ApiRes greatInEvaluateForSkGallery(Long skEvaluateId){
        try{
            //1.金额是否充足
            if(!userManage.IsEnoughForBalance(getUserId(),new BigDecimal("1.00"))){
                return ApiRes.Custom().failure(ApiStatus.BALANCE_NOT_ENOUGH);
            }

            skGalleryManage.greatInEvaluateForSkGallery(skEvaluateId,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

}
