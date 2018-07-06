package cn.wind.db.sys.service.impl;

import cn.wind.db.sys.dao.SysUserAttemptsMapper;
import cn.wind.db.sys.entity.SysUserAttempts;
import cn.wind.db.sys.service.ISysUserAttemptsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户登录尝试次数记录表 服务实现类
 * </p>
 *
 * @author xukk
 * @since 2018-06-19
 */
@Service
public class SysUserAttemptsServiceImpl extends ServiceImpl<SysUserAttemptsMapper, SysUserAttempts> implements ISysUserAttemptsService {

}
