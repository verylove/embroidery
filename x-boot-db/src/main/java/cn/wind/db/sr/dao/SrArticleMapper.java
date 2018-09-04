package cn.wind.db.sr.dao;

import cn.wind.db.sr.entity.SrArticle;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文章公众号数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-10
 */
public interface SrArticleMapper extends BaseMapper<SrArticle> {

    List<SrArticle> findAllByConditions(Pagination page, Map<String, Object> map);
}
