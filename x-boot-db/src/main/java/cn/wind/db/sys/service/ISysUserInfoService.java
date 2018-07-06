package cn.wind.db.sys.service;

import cn.wind.db.sys.entity.SysUserInfo;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author xukk
 * @since 2018-06-07
 */
public interface ISysUserInfoService extends IService<SysUserInfo> {
    /**
     * 根据用户ID获取用户详情
     * @param userId
     * @return
     */
    SysUserInfo findByUserId(Long userId);
}
