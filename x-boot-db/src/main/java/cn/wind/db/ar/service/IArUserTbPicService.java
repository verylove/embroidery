package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserTbPic;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 用户贴吧话题图片数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-16
 */
public interface IArUserTbPicService extends IService<ArUserTbPic> {

    List<ArUserTbPic> findAllByTopicId(Long id);
}
