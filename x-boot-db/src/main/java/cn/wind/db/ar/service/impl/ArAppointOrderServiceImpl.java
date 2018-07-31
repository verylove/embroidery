package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArAppointOrder;
import cn.wind.db.ar.dao.ArAppointOrderMapper;
import cn.wind.db.ar.service.IArAppointOrderService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 用户预约订单数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-23
 */
@Service
public class ArAppointOrderServiceImpl extends ServiceImpl<ArAppointOrderMapper, ArAppointOrder> implements IArAppointOrderService {

    @Override
    public Page<ArAppointOrder> findAllByConditions(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllByConditions(page,map));
    }

    @Override
    public Page<ArAppointOrder> findAllByConditions2(Page page, Map<String, Object> map) {
        return page.setRecords(this.baseMapper.findAllByConditions2(page,map));
    }
}
