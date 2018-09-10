package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserTbTopic;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户贴吧话题数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-16
 */
public interface IArUserTbTopicService extends IService<ArUserTbTopic> {

    Page<ArUserTbTopic> findAllByConditions(Page page, Map<String, Object> map);

    ArUserTbTopic findOneByConditons(Map<String, Object> map);

    Page<ArUserTbTopic> findAllTbByCondition(Page page, Map<String, Object> map);
}
