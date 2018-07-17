package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserTbGreatNum;
import cn.wind.db.ar.dao.ArUserTbGreatNumMapper;
import cn.wind.db.ar.service.IArUserTbGreatNumService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户贴吧话题点赞次数数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-16
 */
@Service
public class ArUserTbGreatNumServiceImpl extends ServiceImpl<ArUserTbGreatNumMapper, ArUserTbGreatNum> implements IArUserTbGreatNumService {


    @Override
    public ArUserTbGreatNum findOneByTopicIdAndUserId(Long tbTopicId, Long userId) {
        return this.baseMapper.findOneByTopicIdAndUserId(tbTopicId,userId);
    }
}
