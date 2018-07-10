package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserSpPic;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 特价纹身相关图片数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-10
 */
public interface IArUserSpPicService extends IService<ArUserSpPic> {

    List<ArUserSpPic> findAllByTattoId(Long id);
}
