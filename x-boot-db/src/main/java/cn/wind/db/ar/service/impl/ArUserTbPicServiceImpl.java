package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserTbPic;
import cn.wind.db.ar.dao.ArUserTbPicMapper;
import cn.wind.db.ar.service.IArUserTbPicService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户贴吧话题图片数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-16
 */
@Service
public class ArUserTbPicServiceImpl extends ServiceImpl<ArUserTbPicMapper, ArUserTbPic> implements IArUserTbPicService {

    @Override
    public List<ArUserTbPic> findAllByTopicId(Long id) {
        return this.baseMapper.findAllByTopicId(id);
    }
}
