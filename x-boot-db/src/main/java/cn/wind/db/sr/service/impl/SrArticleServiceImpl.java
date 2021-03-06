package cn.wind.db.sr.service.impl;

import cn.wind.db.sr.entity.SrArticle;
import cn.wind.db.sr.dao.SrArticleMapper;
import cn.wind.db.sr.service.ISrArticleService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 文章公众号数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-10
 */
@Service
public class SrArticleServiceImpl extends ServiceImpl<SrArticleMapper, SrArticle> implements ISrArticleService {

    @Override
    public Page<SrArticle> findAllByConditions(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllByConditions(page,map));
    }
}
