package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArSignAudit;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户签约保障申请数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-23
 */
public interface IArSignAuditService extends IService<ArSignAudit> {

    ArSignAudit findOneByConditions(Map<String, Object> map);
}
