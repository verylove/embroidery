package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserZxPic;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 用户纹身咨询图片数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-12
 */
public interface IArUserZxPicService extends IService<ArUserZxPic> {

    List<ArUserZxPic> findAllByTattooId(Long id);
}
