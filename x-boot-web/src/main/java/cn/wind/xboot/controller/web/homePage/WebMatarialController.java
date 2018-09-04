package cn.wind.xboot.controller.web.homePage;

import cn.wind.common.res.ApiRes;
import cn.wind.db.sr.entity.SrArticle;
import cn.wind.db.sr.service.ISrArticleService;
import cn.wind.xboot.controller.BaseController;
import cn.wind.xboot.service.web.WebArticleManage;
import cn.wind.xboot.vo.PageVo;
import cn.wind.xboot.vo.web.sr.AppSrArticleVo;
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
 * @Date: 2018/9/3 17:05
 * @Description:
 */
@Api(value = "纹身素材/培训等管理",tags = "纹身素材/培训等管理")
@RestController
@RequestMapping("/webSys/matarial")
public class WebMatarialController extends BaseController {

    @Autowired
    private ISrArticleService articleService;
    @Autowired
    private WebArticleManage articleManage;

    @Override
    public IService getService() {
        return null;
    }

    @Override
    public String getBase() {
        return null;
    }

    @ApiOperation(value = "分页获取APP素材列表")
    @GetMapping(value = "/getAllMatarialByConditions")
    public ApiRes getAllMatarialByConditions(@ModelAttribute AppSrArticleVo articleVo,
                                             @ModelAttribute PageVo pageVo){
        Map<String,Object> map = Maps.newHashMap();
        map.putAll(beanMapper.map(articleVo,Map.class));

        Page<SrArticle> page = articleService.findAllByConditions(pageVo.initPage(),map);
        return ApiRes.Custom().success().addData(page);
    }

    @ApiOperation(value = "APP素材编辑")
    @GetMapping(value = "/matarialEdit")
    public ApiRes matarialEdit(Long matarialId){
        SrArticle article = new SrArticle();
        if(matarialId != null){
            article = articleService.selectById(matarialId);
        }
        return ApiRes.Custom().success().addData(article);
    }

    @ApiOperation(value = "APP素材更新或修改")
    @PostMapping(value = "/matarialUpdate")
    public ApiRes matarialUpdate(@ModelAttribute AppSrArticleVo articleVo){
        return articleManage.matarialUpdate(articleVo);
    }

    @ApiOperation(value = "APP素材删除")
    @DeleteMapping(value = "/matarialDelete")
    public ApiRes matarialDelete(Long matarialId){
        return articleManage.matarialDelete(matarialId);
    }
}
