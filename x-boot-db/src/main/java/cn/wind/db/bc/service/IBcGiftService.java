package cn.wind.db.bc.service;

import cn.wind.db.bc.entity.BcGift;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 直播礼物数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-08-29
 */
public interface IBcGiftService extends IService<BcGift> {

    List<BcGift> findAllOn();
}
