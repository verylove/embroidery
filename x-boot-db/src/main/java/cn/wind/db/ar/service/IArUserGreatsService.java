package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserGreats;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 用户被点赞次数数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-20
 */
public interface IArUserGreatsService extends IService<ArUserGreats> {

    ArUserGreats findByUserIdAndGreatId(Long userId, Long userId1);
}
