package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserFollows;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 用户关注数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-16
 */
public interface IArUserFollowsService extends IService<ArUserFollows> {

    ArUserFollows findOneByUserIdAndFollowId(Long userId, Long followId);

    List<Long> findAllFollowIdsByUserId(Long userId);
}
