package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserZxPic;
import cn.wind.db.ar.dao.ArUserZxPicMapper;
import cn.wind.db.ar.service.IArUserZxPicService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户纹身咨询图片数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-12
 */
@Service
public class ArUserZxPicServiceImpl extends ServiceImpl<ArUserZxPicMapper, ArUserZxPic> implements IArUserZxPicService {

    @Override
    public List<ArUserZxPic> findAllByTattooId(Long id) {
        return this.baseMapper.findAllByTattooId(id);
    }
}
