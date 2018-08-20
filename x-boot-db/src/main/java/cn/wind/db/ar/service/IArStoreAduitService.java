package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArStoreAduit;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户店铺认证申请数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-23
 */
public interface IArStoreAduitService extends IService<ArStoreAduit> {

    ArStoreAduit findOneByConditions(Map<String, Object> map);

    Page<ArStoreAduit> findAllByConditions(Page page, Map<String, Object> map);
}
