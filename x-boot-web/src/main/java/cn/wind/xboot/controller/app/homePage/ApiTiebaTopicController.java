package cn.wind.xboot.controller.app.homePage;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.common.utils.ObjectMapperUtils;
import cn.wind.db.ar.entity.ArUser;
import cn.wind.db.ar.entity.ArUserTbEvaluates;
import cn.wind.db.ar.entity.ArUserTbPic;
import cn.wind.db.ar.entity.ArUserTbTopic;
import cn.wind.db.ar.service.IArUserService;
import cn.wind.db.ar.service.IArUserTbEvaluatesService;
import cn.wind.db.ar.service.IArUserTbPicService;
import cn.wind.db.ar.service.IArUserTbTopicService;
import cn.wind.xboot.controller.app.AppBaseController;
import cn.wind.xboot.dto.app.ar.arUserTbTopicDto;
import cn.wind.xboot.enums.AduitType;
import cn.wind.xboot.enums.IdentityType;
import cn.wind.xboot.service.app.CXArTbTopicManage;
import cn.wind.xboot.service.app.CXArUserManage;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.app.ar.ArUserTbEvaluatesVo;
import cn.wind.xboot.vo.app.ar.ArUserTbTopicVo;
import com.baomidou.mybatisplus.plugins.Page;
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
 * @Date: 2018/7/16 19:17
 * @Description: 贴吧话题(权限两者都有)
 */
@Api(value = "贴吧话题",tags = "贴吧话题")
@RestController
@RequestMapping(value = "/api/app/tbTopic")
public class ApiTiebaTopicController extends AppBaseController{

    @Autowired
    private CXArUserManage userManage;
    @Autowired
    private CXArTbTopicManage tbTopicManage;
    @Autowired
    private IArUserTbTopicService tbTopicService;
    @Autowired
    private IArUserTbPicService tbPicService;
    @Autowired
    private IArUserTbEvaluatesService tbEvaluatesService;
    @Autowired
    private IArUserService userService;

    @ApiOperation(value = "贴吧话题 分页")
    @ApiImplicitParam(name = "type",value = "1-贴吧2-话题",dataType = "Integer",required = true,paramType = "query")
    @GetMapping("/pageForTbTopic")
    public ApiRes pageForTbTopic(Integer type,@ModelAttribute PageVo pageVo){
        try{
            Map<String,Object> map = Maps.newHashMap();
            map.put("type",type);
            Page<ArUserTbTopic> page = tbTopicService.findAllByConditions(pageVo.initPage(),map);

            List<ArUserTbTopicVo> vos = ObjectMapperUtils.mapAll(page.getRecords(),ArUserTbTopicVo.class);

            Page<ArUserTbTopicVo> voPage = new Page<>(page.getCurrent(),page.getSize());
            voPage.setTotal(page.getTotal());

            voPage.setRecords(vos);
            return ApiRes.Custom().addData(voPage);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "贴吧话题详情")
    @ApiImplicitParam(name="tbTopicId",value = "贴吧话题ID",dataType = "Long",required = true,paramType = "query")
    @GetMapping("/DetailInTbTopic")
    public ApiRes DetailInTbTopic(Long tbTopicId){
        try{
            Map<String,Object> map = Maps.newHashMap();
            map.put("tbTopicId",tbTopicId);
            ArUserTbTopic tbTopic = tbTopicService.findOneByConditons(map);

            ArUserTbTopicVo vo = new ArUserTbTopicVo();
            BeanUtils.copyProperties(tbTopic,vo);

            ArUser user = tbTopic.getUser();
            vo.setAccount(user.getAccount());
            vo.setIcon(user.getIcon());
            vo.setPerLevel(user.getPerLevel());
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
//                   vo.setBusinessCard();
            }else if(user.getIdentity()==1){
                vo.setBusinessCard(IdentityType.TATTOO.getType());
            }else {
                vo.setBusinessCard(IdentityType.LOVERS.getType());
            }
            //3.关注
            if(userManage.IsFollowInUserId(getUserId(),vo.getUserId())){
                vo.setIsCollection(1);
            }else {
                vo.setIsCollection(0);
            }
            //4.点赞
            if(tbTopicManage.IsGreatForTbTopic(vo.getId(),getUserId())){
                vo.setIsGreat(1);
            }else {
                vo.setIsGreat(0);
            }
            //5.图片
            List<ArUserTbPic> pics = tbPicService.findAllByTopicId(vo.getId());
            vo.setPics(pics);

            return ApiRes.Custom().addData(vo);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "贴吧话题评论 分页")
    @ApiImplicitParam(name="tbTopicId",value = "贴吧话题ID",dataType = "Long",required = true,paramType = "query")
    @GetMapping(value = "/evaluatesForTbTopic")
    public ApiRes evaluatesForTbTopic(Long tbTopicId,@ModelAttribute PageVo pageVo){
        try{
            Map<String,Object> map = Maps.newHashMap();
            map.put("tbTopicId",tbTopicId);
            map.put("type",1);
            Page<ArUserTbEvaluates> page = tbEvaluatesService.findAllByConditions(pageVo.initPage(),map);

            Page<ArUserTbEvaluatesVo> voPage = new Page<>(page.getCurrent(),page.getSize());
            voPage.setTotal(page.getTotal());

            Map<Long,ArUserTbEvaluates> map2 = page.getRecords().stream().collect(Collectors.toMap(ArUserTbEvaluates::getId, Function.identity()));


            List<ArUserTbEvaluatesVo> vos = ObjectMapperUtils.mapAll(page.getRecords(),ArUserTbEvaluatesVo.class);
            for(ArUserTbEvaluatesVo vo:vos){
                ArUser u = map2.get(vo.getId()).getUser();
                vo.setIcon(u.getIcon());
                vo.setAccount(u.getAccount());
                //子级评论
                Map<String,Object> map3= Maps.newHashMap();
                map3.put("replyId",vo.getId());
                map3.put("type",2);
                map3.put("level",2);
                map3.put("tbTopicId",tbTopicId);
                ArUserTbEvaluates childEvaluates = tbEvaluatesService.findOneInSecondEvaluate(map3);
                vo.setEvaluterId(childEvaluates.getUserId());
                vo.setEvaluterAccount(childEvaluates.getUser().getAccount());
            }
            voPage.setRecords(vos);
            return ApiRes.Custom().addData(voPage);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "贴吧话题评论回复 分页")
    @ApiImplicitParam(name = "tbEvaluateId",value = "该评论的ID",dataType = "Long",required = true,paramType = "query")
    @GetMapping("/pageInEvaluatesForTbTopic")
    public ApiRes pageInEvaluatesForTbTopic(Long tbEvaluateId,@ModelAttribute PageVo pageVo){
        try{
            Map<String,Object> map4= Maps.newHashMap();
            map4.put("skEvaluateId",tbEvaluateId);
            ArUserTbEvaluates skEvaluatesMain = tbEvaluatesService.findOneInSecondEvaluate(map4);
            if(skEvaluatesMain==null){
                return ApiRes.Custom().failure(ApiStatus.SP_EVALUATE_NOT_EXIST);
            }

            Map<String,Object> map= Maps.newHashMap();
            map.put("tbTopicId",skEvaluatesMain.getTbTopicId());
            map.put("type",2);
            map.put("level",skEvaluatesMain.getLevel()+1);
            map.put("replyId",tbEvaluateId);
            Page<ArUserTbEvaluates> page = tbEvaluatesService.findAllByConditions(pageVo.initPage(),map);

            Page<ArUserTbEvaluatesVo> voPage = new Page<>(page.getCurrent(),page.getSize());
            voPage.setTotal(page.getTotal());

            Map<Long,ArUserTbEvaluates> map2 = page.getRecords().stream().collect(Collectors.toMap(ArUserTbEvaluates::getId, Function.identity()));

            List<ArUserTbEvaluatesVo> ArUserSkEvaluatesVos = ObjectMapperUtils.mapAll(page.getRecords(), ArUserTbEvaluatesVo.class);
            for(ArUserTbEvaluatesVo vo:ArUserSkEvaluatesVos){
                ArUser u = map2.get(vo.getId()).getUser();
                vo.setIcon(u.getIcon());
                vo.setAccount(u.getAccount());
                //子级评论
                Map<String,Object> map3= Maps.newHashMap();
                map3.put("tbTopicId",skEvaluatesMain.getTbTopicId());
                map3.put("replyId",vo.getId());
                map3.put("type",2);
                map3.put("level",skEvaluatesMain.getLevel()+2);
                ArUserTbEvaluates childEvaluates = tbEvaluatesService.findOneInSecondEvaluate(map3);
                vo.setEvaluterId(childEvaluates.getUserId());
                vo.setEvaluterAccount(childEvaluates.getUser().getAccount());
            }
            voPage.setRecords(ArUserSkEvaluatesVos);

            ArUserTbEvaluatesVo evaluatesVo = new ArUserTbEvaluatesVo();
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

    @ApiOperation(value = "贴吧话题发布")
    @PostMapping("/publishTbTopic")
    public ApiRes publishTbTopic(arUserTbTopicDto dto){
        try{
            tbTopicManage.publishTbTopic(dto,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "贴吧话题点赞")
    @ApiImplicitParam(name = "tbTopicId",value = "贴吧话题Id",dataType = "Long",required = true,paramType = "query")
    @PostMapping("/greatInTbTopic")
    public ApiRes greatInTbTopic(Long tbTopicId){
        try{
            //1.金额是否充足
            if(!userManage.IsEnoughForBalance(getUserId(),new BigDecimal("1.00"))){
                return ApiRes.Custom().failure(ApiStatus.BALANCE_NOT_ENOUGH);
            }

            tbTopicManage.greatInTbTopic(tbTopicId,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "贴吧话题评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tbTopicId",value = "贴吧话题Id",dataType = "Long",required = true,paramType = "query"),
            @ApiImplicitParam(name = "content",value = "贴吧话题评论",dataType = "String",required = true,paramType = "query")
    })
    @PostMapping("/evaluateInTbTopic")
    public ApiRes evaluateInTbTopic(Long tbTopicId,String content){
        try{

            tbTopicManage.evaluateInTbTopic(tbTopicId,content,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "贴吧话题评论回复")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tbEvaluateId",value = "该评论的ID",dataType = "Long",required = true,paramType = "query"),
            @ApiImplicitParam(name = "content",value = "回复内容",dataType = "String",required = true,paramType = "query")
    })
    @PostMapping("/evaluateInEvaluateForTbTopic")
    public ApiRes evaluateInEvaluateForTbTopic(Long tbEvaluateId,String content){
        try{

            tbTopicManage.evaluateInEvaluateForTbTopic(tbEvaluateId,content,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

    @ApiOperation(value = "贴吧话题评论点赞")
    @ApiImplicitParam(name = "tbEvaluateId",value = "该评论的ID",dataType = "Long",required = true,paramType = "query")
    @PostMapping("/greatInEvaluateForTbTopic")
    public ApiRes greatInEvaluateForTbTopic(Long tbEvaluateId){
        try{
            //1.金额是否充足
            if(!userManage.IsEnoughForBalance(getUserId(),new BigDecimal("1.00"))){
                return ApiRes.Custom().failure(ApiStatus.BALANCE_NOT_ENOUGH);
            }

            tbTopicManage.greatInEvaluateForTbTopic(tbEvaluateId,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }
    }

}
