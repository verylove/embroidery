package cn.wind.db.sr.service;

import cn.wind.db.sr.entity.SrArticle;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 文章公众号数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-10
 */
public interface ISrArticleService extends IService<SrArticle> {

    Page<SrArticle> findAllByConditions(Page page, Map<String, Object> map);
}
