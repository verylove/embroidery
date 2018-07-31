package cn.wind.db.ml.service.impl;

import cn.wind.db.ml.entity.MlUserAddress;
import cn.wind.db.ml.dao.MlUserAddressMapper;
import cn.wind.db.ml.service.IMlUserAddressService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户收获地址数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
@Service
public class MlUserAddressServiceImpl extends ServiceImpl<MlUserAddressMapper, MlUserAddress> implements IMlUserAddressService {

    @Override
    public MlUserAddress findOneByConditions(Map<String, Object> map) {
        return this.baseMapper.findOneByConditions(map);
    }

    @Override
    public Integer updateByConditions(Map<String, Object> map) {
        return this.baseMapper.updateByConditions(map);
    }

    @Override
    public List<MlUserAddress> findAllByConditions(Map<String, Object> map) {
        return this.baseMapper.findAllByConditions(map);
    }
}
