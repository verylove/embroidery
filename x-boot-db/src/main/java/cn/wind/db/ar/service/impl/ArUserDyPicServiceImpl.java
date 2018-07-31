package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArUserDyPic;
import cn.wind.db.ar.dao.ArUserDyPicMapper;
import cn.wind.db.ar.service.IArUserDyPicService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 动态作品图片数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
@Service
public class ArUserDyPicServiceImpl extends ServiceImpl<ArUserDyPicMapper, ArUserDyPic> implements IArUserDyPicService {

    @Override
    public List<ArUserDyPic> findAllByDyWorksId(Long DyWorksId) {
        return this.baseMapper.findAllByDyWorksId(DyWorksId);
    }
}
