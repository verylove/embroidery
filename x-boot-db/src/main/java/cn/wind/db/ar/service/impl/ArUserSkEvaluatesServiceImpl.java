package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserSkEvaluates;
import cn.wind.db.ar.dao.ArUserSkEvaluatesMapper;
import cn.wind.db.ar.service.IArUserSkEvaluatesService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 图库找图用户评价数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-13
 */
@Service
public class ArUserSkEvaluatesServiceImpl extends ServiceImpl<ArUserSkEvaluatesMapper, ArUserSkEvaluates> implements IArUserSkEvaluatesService {

    @Override
    public Page<ArUserSkEvaluates> findAllByConditions(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllByConditions(page,map));
    }

    @Override
    public ArUserSkEvaluates findOneInSecondEvaluate(Map<String, Object> map) {
        return this.baseMapper.findOneInSecondEvaluate(map);
    }
}
