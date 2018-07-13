package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserSkGallery;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 图库找图数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-13
 */
public interface IArUserSkGalleryService extends IService<ArUserSkGallery> {

    Page<ArUserSkGallery> findAllByConditions(Page page, Map<Object, Object> map);
}
