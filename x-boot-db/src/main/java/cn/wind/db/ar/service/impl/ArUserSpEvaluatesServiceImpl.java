package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserSpEvaluates;
import cn.wind.db.ar.dao.ArUserSpEvaluatesMapper;
import cn.wind.db.ar.service.IArUserSpEvaluatesService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 用户特价纹身评论数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-11
 */
@Service
public class ArUserSpEvaluatesServiceImpl extends ServiceImpl<ArUserSpEvaluatesMapper, ArUserSpEvaluates> implements IArUserSpEvaluatesService {

    @Override
    public Page<ArUserSpEvaluates> findAllByConditions(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllByConditions(page,map));
    }

    @Override
    public ArUserSpEvaluates findOneInSecondEvaluate(Map<String, Object> map3) {
        return this.baseMapper.findOneInSecondEvaluate(map3);
    }
}
