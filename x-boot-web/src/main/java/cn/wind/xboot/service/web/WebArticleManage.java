package cn.wind.xboot.service.web;

import cn.wind.common.res.ApiRes;
import cn.wind.db.sr.entity.SrArticle;
import cn.wind.db.sr.service.ISrArticleService;
import cn.wind.xboot.vo.web.sr.AppSrArticleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/9/3 17:34
 * @Description:
 */
@Service
public class WebArticleManage {

    @Autowired
    private ISrArticleService articleService;

    @Transactional
    public ApiRes matarialUpdate(AppSrArticleVo articleVo) {
        SrArticle article = new SrArticle();
        if(articleVo.getId() == null || articleVo.getId().compareTo(0L) <= 0){//新建
            article.setCoverImg(articleVo.getCoverImg());
            article.setDescribtion(articleVo.getDescribtion());
            article.setName(articleVo.getName());
            article.setType(articleVo.getType());
            article.setUrl(articleVo.getUrl());
            articleService.insert(article);
        }else {//更新
            article = articleService.selectById(articleVo.getId());
            if(article == null){
                return ApiRes.Custom().failure("该数据不存在");
            }
            article.setCoverImg(articleVo.getCoverImg());
            article.setDescribtion(articleVo.getDescribtion());
            article.setName(articleVo.getName());
            article.setUrl(articleVo.getUrl());
            articleService.updateById(article);
        }
        return ApiRes.Custom().success();
    }

    @Transactional
    public ApiRes matarialDelete(Long matarialId) {
        SrArticle article =articleService.selectById(matarialId);
        if(article != null){
            articleService.deleteById(matarialId);
        }
        return ApiRes.Custom().success();
    }
}
