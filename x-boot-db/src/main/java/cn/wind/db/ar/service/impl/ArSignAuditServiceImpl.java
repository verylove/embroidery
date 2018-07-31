package cn.wind.db.ar.service.impl;

import cn.wind.db.ar.entity.ArSignAudit;
import cn.wind.db.ar.dao.ArSignAuditMapper;
import cn.wind.db.ar.service.IArSignAuditService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 用户签约保障申请数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-23
 */
@Service
public class ArSignAuditServiceImpl extends ServiceImpl<ArSignAuditMapper, ArSignAudit> implements IArSignAuditService {

    @Override
    public ArSignAudit findOneByConditions(Map<String, Object> map) {
        return this.baseMapper.findOneByConditions(map);
    }
}
