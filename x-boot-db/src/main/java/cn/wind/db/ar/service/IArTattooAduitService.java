package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArTattooAduit;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户纹身师申请数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-23
 */
public interface IArTattooAduitService extends IService<ArTattooAduit> {

    ArTattooAduit findOneByConditions(Map<String, Object> map);
}
