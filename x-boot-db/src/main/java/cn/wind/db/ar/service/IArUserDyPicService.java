package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserDyPic;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 动态作品图片数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
public interface IArUserDyPicService extends IService<ArUserDyPic> {

    List<ArUserDyPic> findAllByDyWorksId(Long id);
}
