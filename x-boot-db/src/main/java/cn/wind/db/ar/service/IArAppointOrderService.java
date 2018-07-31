package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArAppointOrder;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户预约订单数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-23
 */
public interface IArAppointOrderService extends IService<ArAppointOrder> {

    Page<ArAppointOrder> findAllByConditions(Page page, Map<String, Object> map);

    Page<ArAppointOrder> findAllByConditions2(Page page, Map<String, Object> map);
}
