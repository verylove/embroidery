package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserOtPic;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 纹纹达人图片数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-17
 */
public interface IArUserOtPicService extends IService<ArUserOtPic> {

    List<ArUserOtPic> findAllByOtTattooId(Long id);
}
