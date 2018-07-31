package cn.wind.db.ml.service;

import cn.wind.db.ml.entity.MlUserAddress;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户收获地址数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
public interface IMlUserAddressService extends IService<MlUserAddress> {

    MlUserAddress findOneByConditions(Map<String, Object> map);

    Integer updateByConditions(Map<String, Object> map);

    List<MlUserAddress> findAllByConditions(Map<String, Object> map);
}
