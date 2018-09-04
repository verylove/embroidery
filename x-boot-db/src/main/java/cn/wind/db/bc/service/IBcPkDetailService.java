package cn.wind.db.bc.service;

import cn.wind.db.bc.entity.BcPkDetail;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 主播PK详情数据表 服务类
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-08-29
 */
public interface IBcPkDetailService extends IService<BcPkDetail> {

    BcPkDetail findOneByIds(long inviteUserID, long enInviteUserID);
}
