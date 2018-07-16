package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserSkEvaluates;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 图库找图用户评价数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-13
 */
public interface IArUserSkEvaluatesService extends IService<ArUserSkEvaluates> {

    Page<ArUserSkEvaluates> findAllByConditions(Page page, Map<String, Object> map);

    ArUserSkEvaluates findOneInSecondEvaluate(Map<String, Object> map);
}
