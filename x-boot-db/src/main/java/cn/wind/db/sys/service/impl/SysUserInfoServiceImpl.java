package cn.wind.db.sys.service.impl;

import cn.wind.db.sys.dao.SysUserInfoMapper;
import cn.wind.db.sys.entity.SysUserInfo;
import cn.wind.db.sys.service.ISysUserInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author xukk
 * @since 2018-06-07
 */
@Service
public class SysUserInfoServiceImpl extends ServiceImpl<SysUserInfoMapper, SysUserInfo> implements ISysUserInfoService {
    @Override
    public SysUserInfo findByUserId(Long userId) {
        return this.baseMapper.findByUserId(userId);
    }
}
