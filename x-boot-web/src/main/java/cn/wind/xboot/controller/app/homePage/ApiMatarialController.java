package cn.wind.xboot.controller.app.homePage;

import cn.wind.common.res.ApiRes;
import cn.wind.common.res.ApiStatus;
import cn.wind.db.sr.entity.SrArticle;
import cn.wind.db.sr.entity.SrBannerImg;
import cn.wind.db.sr.service.ISrArticleService;
import cn.wind.db.sr.service.ISrBannerImgService;
import cn.wind.xboot.controller.app.AppBaseController;
import cn.wind.xboot.vo.PageVo;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/10 09:49
 * @Description:
 */
@Api(value = "素材话题管理",tags = "素材话题管理")
@RestController
@RequestMapping("/api/app/matarial")
public class ApiMatarialController extends AppBaseController{

    @Autowired
    private ISrBannerImgService bannerImgService;
    @Autowired
    private ISrArticleService articleService;

    @ApiOperation(value = "轮播图显示")
    @ApiImplicitParam(name = "type",value = "类型 1-首页 2-商城",required = true,dataType = "Integer",paramType = "query")
    @GetMapping("/banners")
    public ApiRes getBanners(Integer type){
        try{
            List<SrBannerImg> imgs = bannerImgService.findAllByType(type);
            return ApiRes.Custom().addData(imgs);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }

    @ApiOperation(value = "获取文章资讯（纹身素材、纹身培训、公众号、签约纹身店）")
    @ApiImplicitParam(name = "type",value = "类型 1-纹身素材 2-纹身培训 3-公众号 4-签约纹身店",required = true,dataType = "Integer",paramType = "query")
    @GetMapping("/articles")
    public ApiRes getArticle(Integer type,@ModelAttribute PageVo<SrArticle> pageVo){
        try{
            List<String> sort = Lists.newArrayList();
            sort.add("createTime,desc");
            pageVo.setSort(sort);
            EntityWrapper<SrArticle> ew=new EntityWrapper<SrArticle>();
            if(type!=null){
                ew.eq("type",type);
            }
            Page<SrArticle> list =articleService.selectPage(pageVo.initPage(),ew);
            return ApiRes.Custom().addData(list);
        }catch (Exception e){
            e.printStackTrace();
            return ApiRes.Custom().failure(ApiStatus.DATA_GET_FAIL);
        }
    }
}
