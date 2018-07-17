package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserTbTopic;
import cn.wind.db.ar.dao.ArUserTbTopicMapper;
import cn.wind.db.ar.service.IArUserTbTopicService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 用户贴吧话题数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-16
 */
@Service
public class ArUserTbTopicServiceImpl extends ServiceImpl<ArUserTbTopicMapper, ArUserTbTopic> implements IArUserTbTopicService {

    @Override
    public Page<ArUserTbTopic> findAllByConditions(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllByConditions(page,map));
    }

    @Override
    public ArUserTbTopic findOneByConditons(Map<String, Object> map) {
        return this.baseMapper.findOneByConditons(map);
    }
}
