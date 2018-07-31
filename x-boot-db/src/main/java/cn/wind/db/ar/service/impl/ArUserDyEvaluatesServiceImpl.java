package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserDyEvaluates;
import cn.wind.db.ar.dao.ArUserDyEvaluatesMapper;
import cn.wind.db.ar.service.IArUserDyEvaluatesService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 动态作品用户评价数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
@Service
public class ArUserDyEvaluatesServiceImpl extends ServiceImpl<ArUserDyEvaluatesMapper, ArUserDyEvaluates> implements IArUserDyEvaluatesService {

    @Override
    public Page<ArUserDyEvaluates> findAllByConditions(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllByConditions(page,map));
    }

    @Override
    public ArUserDyEvaluates findOneInSecondEvaluate(Map<String, Object> map) {
        return this.baseMapper.findOneInSecondEvaluate(map);
    }
}
