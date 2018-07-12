package cn.wind.xboot.controller.app.homePage;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.common.utils.ObjectMapperUtils;
import cn.wind.db.ar.entity.ArUserZxPic;
import cn.wind.db.ar.entity.ArUserZxTattoo;
import cn.wind.db.ar.service.IArUserZxPicService;
import cn.wind.db.ar.service.IArUserZxTattooService;
import cn.wind.xboot.controller.app.AppBaseController;
import cn.wind.xboot.dto.app.ar.arUserZxTattooDto;
import cn.wind.xboot.enums.AduitType;
import cn.wind.xboot.enums.IdentityType;
import cn.wind.xboot.service.app.CXArUserManage;
import cn.wind.xboot.service.app.CXArZxTattooManage;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.app.ar.ArUserZxTattooVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/12 17:30
 * @Description:
 */
@Api(value = "纹身咨询管理",tags = "纹身咨询管理")
@RestController
@RequestMapping(value = "/api/app/zxTattoo")
public class ApiConsultTattooController extends AppBaseController{

    @Autowired
    private CXArZxTattooManage zxTattooManage;
    @Autowired
    private IArUserZxTattooService zxTattooService;
    @Autowired
    private IArUserZxPicService zxPicService;
    @Autowired
    private CXArUserManage userManage;

    @ApiOperation(value = "纹身咨询发布")
    @PostMapping(value = "/publishZxTattoo")
    public ApiRes publishZxTattoo(arUserZxTattooDto dto){
        try{
            // TODO 验证是否具有爱好者权限
            //1.用户是否未实名认证
            if(userManage.IsNameAduit(getUserId())){
                return ApiRes.Custom().failure(ApiStatus.NAME_ALREADY_ADUIT);
            }

            zxTattooManage.publish(dto,getUserId());
            return ApiRes.Custom().success();
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_POST_FAIL);
        }

    }

    @ApiOperation(value = "纹身咨询 分页")
    @GetMapping(value = "/pageForZxTattoo")
    public ApiRes pageForZxTattoo(@ModelAttribute PageVo pageVo){
        try{
            Page<ArUserZxTattoo> page = zxTattooService.findAllByConditions(pageVo.initPage(), Maps.newHashMap());

            Page<ArUserZxTattooVo> voPage = new Page<>(page.getCurrent(),page.getSize());
            page.setTotal(page.getTotal());

            Map<Long,ArUserZxTattoo> map = page.getRecords().stream().collect(Collectors.toMap(ArUserZxTattoo::getId, Function.identity()));

            List<ArUserZxTattooVo> zxTattooVoList = ObjectMapperUtils.mapAll(page.getRecords(), ArUserZxTattooVo.class);
            for(ArUserZxTattooVo vo:zxTattooVoList){
                ArUserZxTattoo tattoo = map.get(vo.getId());
                if(Strings.isNullOrEmpty(tattoo.getMicroLetter())){
                    vo.setIsMicroLetter(0);
                }else {
                    vo.setIsMicroLetter(1);
                }
                if(Strings.isNullOrEmpty(tattoo.getPhone())){
                    vo.setIsPhone(0);
                }else {
                    vo.setIsPhone(1);
                }
                vo.setPerLevel(tattoo.getUser().getPerLevel());
                vo.setAccount(tattoo.getUser().getAccount());
                vo.setIcon(tattoo.getUser().getIcon());
                //1.认证
                if(tattoo.getUser().getStoreStatus()==3){
                    vo.setAduit(AduitType.STOREADUIT.getaType());
                }else if(tattoo.getUser().getSignStatus()==3){
                    vo.setAduit(AduitType.SIGNADUIT.getaType());
                }else if(tattoo.getUser().getNameStatus()==3){
                    vo.setAduit(AduitType.NAMEADUIT.getaType());
                }
                //2.名片
                if(tattoo.getUser().getStoreStatus()==3){
                    // TODO 获取店铺名
//                    vo.setBusinessCard();
                }else if(tattoo.getUser().getIdentity()==1){
                    vo.setBusinessCard(IdentityType.TATTOO.getType());
                }else {
                    vo.setBusinessCard(IdentityType.LOVERS.getType());
                }

                List<ArUserZxPic> pics = zxPicService.findAllByTattooId(vo.getId());
                vo.setPics(pics);
            }
            voPage.setRecords(zxTattooVoList);
            return ApiRes.Custom().addData(voPage);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "查看微信或电话")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "zxTattooId",value = "纹身咨询ID",dataType = "Long",required = true,paramType = "query"),
            @ApiImplicitParam(name = "type",value = "类型 1-微信 2-电话",dataType = "Integer",required = true,paramType = "query")
    })
    @GetMapping("/infoForZxTattoo")
    public ApiRes infoForZxTattoo(Long zxTattooId,Integer type){
        try{
            //1.用户是否实名认证
            if(!userManage.IsNameAduit(getUserId())){
                return ApiRes.Custom().failure(ApiStatus.NAME_NO_ADUIT);
            }

            Map<String,Object> map = Maps.newHashMap();
            map.put("zxTattooId",zxTattooId);
            ArUserZxTattoo tattoo = zxTattooService.findOneByConditions(map);
            if(type==1){
                return ApiRes.Custom().addData(tattoo.getMicroLetter());
            }else {
                return ApiRes.Custom().addData(tattoo.getPhone());
            }
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }
}
