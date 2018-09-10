package cn.wind.xboot.controller.web.mall;

import cn.wind.common.res.ApiRes;
import cn.wind.db.sr.entity.SrBannerImg;
import cn.wind.db.sr.service.ISrBannerImgService;
import cn.wind.xboot.controller.BaseController;
import cn.wind.xboot.service.web.WebBannerImgManage;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.web.sr.AppSrBannerImgVo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/8 19:34
 * @Description:
 */
@Api(value = "商城Banner管理",tags = "商城Banner管理")
@RestController
@RequestMapping("/webSys/mallBanner")
public class WebMallBannerController extends BaseController{
    @Autowired
    private ISrBannerImgService bannerImgService;
    @Autowired
    private WebBannerImgManage bannerImgManage;

    @Override
    public IService getService() {
        return null;
    }

    @Override
    public String getBase() {
        return null;
    }

    @ApiOperation(value = "分页获取APP商城轮播图列表")
    @GetMapping(value = "/getAllMallBannersByCondition")
    public ApiRes getAllMallBannersByCondition(
            @ModelAttribute PageVo pageVo){
        Map<String,Object> map= Maps.newHashMap();
        map.put("category",2);
        Page<SrBannerImg> page = bannerImgService.findAllByConditions(pageVo.initPage(),map);
        return ApiRes.Custom().success().addData(page);
    }

    @ApiOperation(value = "商城轮播图编辑")
    @GetMapping(value = "/mallBannerEdit")
    public ApiRes mallBannerEdit(Long bannerImgId){
        SrBannerImg bannerImg  = new SrBannerImg();
        if(bannerImgId != null){//修改
            bannerImg = bannerImgService.selectById(bannerImgId);
        }
        return ApiRes.Custom().success().addData(bannerImg);
    }

    @ApiOperation(value = "商城轮播图更新或新增")
    @PostMapping(value = "/mallBannerUpdate")
    public ApiRes mallBannerUpdate( @ModelAttribute AppSrBannerImgVo bannerImgVo){
        return bannerImgManage.mallBannerUpdate(bannerImgVo);
    }

    @ApiOperation(value = "商城轮播图删除")
    @PostMapping(value = "/mallBannerDelete")
    public ApiRes mallBannerDelete(Long bannerImgId){
        return bannerImgManage.homeBannerDelete(bannerImgId);
    }
}
