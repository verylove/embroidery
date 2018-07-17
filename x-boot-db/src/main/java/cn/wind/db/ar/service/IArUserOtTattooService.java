package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserOtTattoo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 纹纹达人数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-17
 */
public interface IArUserOtTattooService extends IService<ArUserOtTattoo> {


    Page<ArUserOtTattoo> findAllByConditions(Page page, Map<String, Object> map);

    ArUserOtTattoo findOneByConditions(Map<String, Object> map);
}
