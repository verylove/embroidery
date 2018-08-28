package cn.wind.db.bc.service;

import cn.wind.db.bc.entity.BcPkTotal;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 主播PK总数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-08-28
 */
public interface IBcPkTotalService extends IService<BcPkTotal> {

    BcPkTotal findOneByUserId(long userId);

    Long findRankInCity(Map<String, Object> map);
}
