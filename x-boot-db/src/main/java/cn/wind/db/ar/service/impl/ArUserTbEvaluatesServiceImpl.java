package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserTbEvaluates;
import cn.wind.db.ar.dao.ArUserTbEvaluatesMapper;
import cn.wind.db.ar.service.IArUserTbEvaluatesService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 用户贴吧话题评论数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-16
 */
@Service
public class ArUserTbEvaluatesServiceImpl extends ServiceImpl<ArUserTbEvaluatesMapper, ArUserTbEvaluates> implements IArUserTbEvaluatesService {

    @Override
    public Page<ArUserTbEvaluates> findAllByConditions(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllByConditions(page,map));
    }

    @Override
    public ArUserTbEvaluates findOneInSecondEvaluate(Map<String, Object> map) {
        return this.baseMapper.findOneInSecondEvaluate(map);
    }
}
