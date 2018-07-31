package cn.wind.xboot.service.app;

import cn.wind.db.ar.entity.ArAppointOrder;
import cn.wind.db.ar.service.IArAppointOrderService;
import cn.wind.xboot.dto.app.ar.arAppointOrderDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: changzhaoliang
 * @Date: 2018/7/23 18:07
 * @Description:
 */
@Service
public class CXArAppointManage {

    @Autowired
    private IArAppointOrderService appointOrderService;

    @Transactional
    public void newOrder(arAppointOrderDto dto, Long userId)throws Exception {
        ArAppointOrder appointOrder = new ArAppointOrder();
        BeanUtils.copyProperties(dto,appointOrder);
        appointOrder.setUserId(userId);
        appointOrder.setStatus(1);
        appointOrderService.insert(appointOrder);
    }
}
