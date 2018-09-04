package cn.wind.xboot.controller.app.perInfo;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.db.ar.entity.ArUser;
import cn.wind.db.ar.entity.ArUserFollows;
import cn.wind.db.ar.entity.ArUserMessage;
import cn.wind.db.ar.service.IArUserFollowsService;
import cn.wind.db.ar.service.IArUserMessageService;
import cn.wind.db.ar.service.IArUserService;
import cn.wind.xboot.controller.app.AppBaseController;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.app.ar.ArUserFollowMessageVo;
import cn.wind.xboot.vo.app.ar.ArUserMessageVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/8/31 15:06
 * @Description:
 */
@Api(value = "消息管理",tags = "消息管理")
@RestController
@RequestMapping("/api/app/message")
public class ApiMessageController extends AppBaseController {

    @Autowired
    private IArUserMessageService messageService;
    @Autowired
    private IArUserFollowsService followsService;
    @Autowired
    private IArUserService userService;


    @ApiOperation(value = "系统消息")
    @GetMapping("/systemMessage")
    public ApiRes systemMessage(PageVo<ArUserMessage> pageVo){
        try{
            List<String> sort = Lists.newArrayList();
            sort.add("createTime,desc");
            pageVo.setSort(sort);
            EntityWrapper<ArUserMessage> ew=new EntityWrapper<ArUserMessage>();
            ew.eq("type",1).and().eq("to_user_id",getUserId());
            Page<ArUserMessage> list =messageService.selectPage(pageVo.initPage(),ew);
            return ApiRes.Custom().addData(list);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "我的评论")
    @GetMapping("/myEvaluateMessage")
    public ApiRes myEvaluateMessage(PageVo<ArUserMessage> pageVo){
        try{
            List<String> sort = Lists.newArrayList();
            sort.add("createTime,desc");
            pageVo.setSort(sort);
            EntityWrapper<ArUserMessage> ew=new EntityWrapper<ArUserMessage>();
            ew.eq("type",2).and().eq("to_user_id",getUserId());
            Page<ArUserMessage> list =messageService.selectPage(pageVo.initPage(),ew);

            Page<ArUserMessageVo> voPage = new Page<>(list.getCurrent(),list.getSize());
            voPage.setTotal(list.getTotal());

            List<ArUserMessageVo> vos = Lists.newArrayList();
            for(ArUserMessage message:list.getRecords()){
                ArUserMessageVo vo = new ArUserMessageVo();
                vo.setId(message.getId());
                if(message.getCategory()>6){
                    vo.setCategory(message.getCategory()-6);
                }else {
                    vo.setCategory(message.getCategory());
                }
                vo.setTitle(message.getTitle());
                vo.setContent(message.getContent());
                vo.setCreateTime(message.getCreateTime());
                vo.setToUserId(message.getToUserId());
                vo.setTattooContent(message.getExtraContent());
                vo.setTattooId(message.getExtraId());
            }

            voPage.setRecords(vos);
            return ApiRes.Custom().addData(voPage);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "我的赞")
    @GetMapping("/myGreatMessage")
    public ApiRes myGreatMessage(PageVo<ArUserMessage> pageVo){
        try{
            List<String> sort = Lists.newArrayList();
            sort.add("createTime,desc");
            pageVo.setSort(sort);
            EntityWrapper<ArUserMessage> ew=new EntityWrapper<ArUserMessage>();
            ew.eq("type",3).and().eq("to_user_id",getUserId());
            Page<ArUserMessage> list =messageService.selectPage(pageVo.initPage(),ew);

            Page<ArUserMessageVo> voPage = new Page<>(list.getCurrent(),list.getSize());
            voPage.setTotal(list.getTotal());

            List<ArUserMessageVo> vos = Lists.newArrayList();
            for(ArUserMessage message:list.getRecords()){
                ArUserMessageVo vo = new ArUserMessageVo();
                vo.setId(message.getId());
                if(message.getCategory()>6){
                    vo.setCategory(message.getCategory()-6);
                }else {
                    vo.setCategory(message.getCategory());
                }
                vo.setContent(message.getContent());
                vo.setTitle(message.getTitle());
                vo.setCreateTime(message.getCreateTime());
                vo.setToUserId(message.getToUserId());
                vo.setTattooContent(message.getExtraContent());
                vo.setTattooId(message.getExtraId());
                vos.add(vo);
            }

            voPage.setRecords(vos);
            return ApiRes.Custom().addData(voPage);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "关注消息")
    @GetMapping("/myFollowMessage")
    public ApiRes myFollowMessage(PageVo<ArUserFollows> pageVo){
        try{
            List<String> sort = Lists.newArrayList();
            sort.add("modifyTime,desc");
            pageVo.setSort(sort);
            EntityWrapper<ArUserFollows> ew=new EntityWrapper<ArUserFollows>();
            ew.eq("follow_id",getUserId()).and().eq("status",1);
            Page<ArUserFollows> list =followsService.selectPage(pageVo.initPage(),ew);

            Page<ArUserFollowMessageVo> voPage = new Page<>(list.getCurrent(),list.getSize());
            voPage.setTotal(list.getTotal());

            if(list.getRecords() == null || list.getRecords().size()<1){
                return ApiRes.Custom().addData(voPage);
            }
            List<Long> userIds = list.getRecords().stream().map(ArUserFollows::getUserId).collect(Collectors.toList());
            List<ArUser> users = userService.findAllByIdIn(userIds);
            Map<Long,ArUser> userMap = users.stream().collect(Collectors.toMap(ArUser::getId, Function.identity()));

            List<Long> followIds = followsService.findAllFollowIdsByUserId(getUserId());

            List<ArUserFollowMessageVo> vos = Lists.newArrayList();
            for(ArUserFollows follows:list.getRecords()){
                ArUserFollowMessageVo vo = new ArUserFollowMessageVo();
                vo.setUserId(follows.getUserId());
                vo.setCreateTime(follows.getModifyTime());
                vo.setIcon(userMap.get(follows.getUserId()).getIcon());
                vo.setAccount(userMap.get(follows.getUserId()).getAccount());
                if(followIds.contains(follows.getUserId())){
                    vo.setIsFollowed(1);
                }else {
                    vo.setIsFollowed(0);
                }
            }
            return ApiRes.Custom().addData(voPage);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }
}
