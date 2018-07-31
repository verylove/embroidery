package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserMoneyRecord;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户金额变动记录数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-11
 */
public interface IArUserMoneyRecordService extends IService<ArUserMoneyRecord> {

    ArUserMoneyRecord findOnebyContions(Map<String, Object> map);
}
