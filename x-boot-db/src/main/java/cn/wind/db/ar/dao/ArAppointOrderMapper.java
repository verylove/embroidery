package cn.wind.db.ar.dao;

import cn.wind.db.ar.entity.ArAppointOrder;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户预约订单数据表 Mapper 接口
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-23
 */
public interface ArAppointOrderMapper extends BaseMapper<ArAppointOrder> {

    List<ArAppointOrder> findAllByConditions(Pagination page, Map<String, Object> map);

    List<ArAppointOrder> findAllByConditions2(Pagination page, Map<String, Object> map);
}
