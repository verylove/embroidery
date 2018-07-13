package cn.wind.xboot.controller.app.homePage;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.common.utils.ObjectMapperUtils;
import cn.wind.db.ar.entity.ArUser;
import cn.wind.db.ar.entity.ArUserSkGallery;
import cn.wind.db.ar.entity.ArUserSkLabel;
import cn.wind.db.ar.service.IArUserSkGalleryService;
import cn.wind.db.ar.service.IArUserSkLabelService;
import cn.wind.xboot.controller.app.AppBaseController;
import cn.wind.xboot.dto.app.ar.arUserSkGalleryDto;
import cn.wind.xboot.enums.AduitType;
import cn.wind.xboot.service.app.CXArSkGalleryManage;
import cn.wind.xboot.service.app.CXArUserManage;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.app.ar.ArUserSkGalleryVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            }
            voPage.setRecords(vos);
            return ApiRes.Custom().addData(voPage);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }
}
