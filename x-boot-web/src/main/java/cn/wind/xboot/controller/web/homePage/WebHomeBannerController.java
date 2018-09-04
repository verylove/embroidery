package cn.wind.xboot.controller.web.homePage;

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
 * @Date: 2018/9/3 14:09
 * @Description:
 */
@Api(value = "首页Banner管理",tags = "首页Banner管理")
@RestController
@RequestMapping("/webSys/homeBanner")
public class WebHomeBannerController extends BaseController {

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

    @ApiOperation(value = "分页获取APP首页轮播图列表")
    @GetMapping(value = "/getAllHomeBannersByCondition")
    public ApiRes getAllHomeBannersByCondition(
                                 @ModelAttribute PageVo pageVo){
        Map<String,Object> map= Maps.newHashMap();
        map.put("category",1);
        Page<SrBannerImg> page = bannerImgService.findAllByConditions(pageVo.initPage(),map);
        return ApiRes.Custom().success().addData(page);
    }

    @ApiOperation(value = "首页轮播图编辑")
    @GetMapping(value = "/homeBannerEdit")
    public ApiRes homeBannerEdit(Long bannerImgId){
        SrBannerImg bannerImg  = new SrBannerImg();
        if(bannerImgId != null){//修改
            bannerImg = bannerImgService.selectById(bannerImgId);
        }
        return ApiRes.Custom().success().addData(bannerImg);
    }

    @ApiOperation(value = "首页轮播图更新或新增")
    @PostMapping(value = "/homeBannerUpdate")
    public ApiRes homeBannerUpdate( @ModelAttribute AppSrBannerImgVo bannerImgVo){
        return bannerImgManage.homeBannerUpdate(bannerImgVo);
    }

    @ApiOperation(value = "首页轮播图删除")
    @PostMapping(value = "/homeBannerDelete")
    public ApiRes homeBannerDelete(Long bannerImgId){
        return bannerImgManage.homeBannerDelete(bannerImgId);
    }

}
