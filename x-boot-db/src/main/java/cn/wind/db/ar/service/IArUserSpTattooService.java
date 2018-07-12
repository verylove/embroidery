package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserSpTattoo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 特价纹身数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-10
 */
public interface IArUserSpTattooService extends IService<ArUserSpTattoo> {

    Page<ArUserSpTattoo> findByCoordinates(Page page, Map<String, Object> map);

    Page<ArUserSpTattoo> findAll(Page page);

    ArUserSpTattoo findOneById(Long spTattooId);
}
