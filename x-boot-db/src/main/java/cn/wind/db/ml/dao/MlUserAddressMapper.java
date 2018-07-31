package cn.wind.db.ml.dao;

import cn.wind.db.ml.entity.MlUserAddress;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户收获地址数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-25
 */
public interface MlUserAddressMapper extends BaseMapper<MlUserAddress> {

    MlUserAddress findOneByConditions(Map<String, Object> map);

    Integer updateByConditions(Map<String, Object> map);

    List<MlUserAddress> findAllByConditions(Map<String, Object> map);
}
