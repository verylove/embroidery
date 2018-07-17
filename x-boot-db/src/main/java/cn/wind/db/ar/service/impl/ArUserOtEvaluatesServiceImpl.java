package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserOtEvaluates;
import cn.wind.db.ar.dao.ArUserOtEvaluatesMapper;
import cn.wind.db.ar.service.IArUserOtEvaluatesService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 纹纹达人用户评价数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-17
 */
@Service
public class ArUserOtEvaluatesServiceImpl extends ServiceImpl<ArUserOtEvaluatesMapper, ArUserOtEvaluates> implements IArUserOtEvaluatesService {


    @Override
    public Page<ArUserOtEvaluates> findAllByConditions(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllByConditions(page,map));
    }

    @Override
    public ArUserOtEvaluates findOneInSecondEvaluate(Map<String, Object> map) {
        return this.baseMapper.findOneInSecondEvaluate(map);
    }
}
