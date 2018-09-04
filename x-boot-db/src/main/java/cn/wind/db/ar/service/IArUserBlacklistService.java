package cn.wind.db.ar.service;

import cn.wind.db.ar.entity.ArUserBlacklist;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 用户拉黑数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-08-30
 */
public interface IArUserBlacklistService extends IService<ArUserBlacklist> {

    ArUserBlacklist findOneByUserIdAndBlackId(Long userId, Long toUserId);
}
