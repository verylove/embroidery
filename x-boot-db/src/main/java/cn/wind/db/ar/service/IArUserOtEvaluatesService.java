package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserOtEvaluates;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 纹纹达人用户评价数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-17
 */
public interface IArUserOtEvaluatesService extends IService<ArUserOtEvaluates> {

    Page<ArUserOtEvaluates> findAllByConditions(Page page, Map<String, Object> map);

    ArUserOtEvaluates findOneInSecondEvaluate(Map<String, Object> map3);
}
