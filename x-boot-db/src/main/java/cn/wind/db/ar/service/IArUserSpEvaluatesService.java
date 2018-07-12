package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserSpEvaluates;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户特价纹身评论数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-11
 */
public interface IArUserSpEvaluatesService extends IService<ArUserSpEvaluates> {

    Page<ArUserSpEvaluates> findAllByConditions(Page page, Map<String, Object> map);

    ArUserSpEvaluates findOneInSecondEvaluate(Map<String, Object> map3);
}
