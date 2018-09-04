package cn.wind.db.bc.service.impl;

import cn.wind.db.bc.entity.BcGift;
import cn.wind.db.bc.dao.BcGiftMapper;
import cn.wind.db.bc.service.IBcGiftService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 直播礼物数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-08-29
 */
@Service
public class BcGiftServiceImpl extends ServiceImpl<BcGiftMapper, BcGift> implements IBcGiftService {

    @Override
    public List<BcGift> findAllOn() {
        return this.baseMapper.findAllOn();
    }
}
