package cn.wind.db.bc.service.impl;

import cn.wind.db.bc.entity.BcPkDetail;
import cn.wind.db.bc.dao.BcPkDetailMapper;
import cn.wind.db.bc.service.IBcPkDetailService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 主播PK详情数据表 服务实现类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-08-29
 */
@Service
public class BcPkDetailServiceImpl extends ServiceImpl<BcPkDetailMapper, BcPkDetail> implements IBcPkDetailService {

    @Override
    public BcPkDetail findOneByIds(long inviteUserID, long enInviteUserID) {
        return this.baseMapper.findOneByIds(inviteUserID,enInviteUserID);
    }
}
