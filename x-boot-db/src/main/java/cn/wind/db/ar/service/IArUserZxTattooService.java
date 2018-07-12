package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserZxTattoo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户纹身咨询数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-12
 */
public interface IArUserZxTattooService extends IService<ArUserZxTattoo> {

    Page<ArUserZxTattoo> findAllByConditions(Page page, Map<String, Object> map);

    ArUserZxTattoo findOneByConditions(Map<String, Object> map);
}
