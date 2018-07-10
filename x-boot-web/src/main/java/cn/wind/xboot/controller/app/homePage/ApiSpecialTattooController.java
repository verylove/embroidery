package cn.wind.xboot.controller.app.homePage;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.common.utils.ObjectMapperUtils;
import cn.wind.db.ar.entity.ArUserSpPic;
import cn.wind.db.ar.entity.ArUserSpTattoo;
import cn.wind.db.ar.service.IArUserSpPicService;
import cn.wind.db.ar.service.IArUserSpTattooService;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.app.ar.ArUserSpTattooVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
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

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/10 15:27
 * @Description:特价纹身
 */
@Api(value = "特价纹身",tags = "特价纹身")
@RestController
@RequestMapping("/api/app/spTattoo")
public class ApiSpecialTattooController {

    @Autowired
    private IArUserSpTattooService spTattooService;
    @Autowired
    private IArUserSpPicService spPicService;

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
                page = spTattooService.findAll(pageVo.initPage());
            }else {//附近
                Map<String,Object> map= Maps.newHashMap();
                map.put("longitude",longitude);
                map.put("latitude",latitude);
                page = spTattooService.findByCoordinates(pageVo.initPage(),map);
            }
            Page<ArUserSpTattooVo> pageResult = new Page<>(page.getCurrent(), page.getSize());
            List<ArUserSpTattooVo> ArUserSpTattooVos = ObjectMapperUtils.mapAll(page.getRecords(), ArUserSpTattooVo.class);
            for(ArUserSpTattooVo vo:ArUserSpTattooVos){
                List<ArUserSpPic> pics = spPicService.findAllByTattoId(vo.getId());
                vo.setPics(pics);
            }
            pageResult.setRecords(ArUserSpTattooVos);
            return ApiRes.Custom().addData(pageResult);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }
}
