package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserTbEvaluates;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户贴吧话题评论数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-16
 */
public interface IArUserTbEvaluatesService extends IService<ArUserTbEvaluates> {

    Page<ArUserTbEvaluates> findAllByConditions(Page page, Map<String, Object> map);

    ArUserTbEvaluates findOneInSecondEvaluate(Map<String, Object> map3);
}
