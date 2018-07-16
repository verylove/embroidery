package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserSkPic;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 图库找图图片数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-13
 */
public interface IArUserSkPicService extends IService<ArUserSkPic> {

    List<ArUserSkPic> findAllByGalleryId(Long id);
}
