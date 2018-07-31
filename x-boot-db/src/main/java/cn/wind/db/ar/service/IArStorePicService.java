package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArStorePic;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 店铺室内环境图集数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-23
 */
public interface IArStorePicService extends IService<ArStorePic> {

    List<ArStorePic> findAllByStoreId(Long storeId);
}
