package cn.wind.db.cli.service.impl;

import cn.wind.db.cli.dao.CliClientAttemptsMapper;
import cn.wind.db.cli.entity.CliClientAttempts;
import cn.wind.db.cli.service.ICliClientAttemptsService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员用户尝试登陆次数记录表 服务实现类
 * </p>
 *
 * @author xukk
 * @since 2018-06-19
 */
@Service
public class CliClientAttemptsServiceImpl extends ServiceImpl<CliClientAttemptsMapper, CliClientAttempts> implements ICliClientAttemptsService {

}
