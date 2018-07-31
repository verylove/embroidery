package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserDyEvaluates;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 动态作品用户评价数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
public interface IArUserDyEvaluatesService extends IService<ArUserDyEvaluates> {

    Page<ArUserDyEvaluates> findAllByConditions(Page page, Map<String, Object> map);

    ArUserDyEvaluates findOneInSecondEvaluate(Map<String, Object> map3);
}
