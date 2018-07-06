package cn.wind.db.cli.service.impl;

import cn.wind.db.cli.dao.CliClientMapper;
import cn.wind.db.cli.entity.CliClient;
import cn.wind.db.cli.service.ICliClientService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 平台会员表 服务实现类
 * </p>
 *
 * @author xukk
 * @since 2018-06-19
 */
@Service
public class CliClientServiceImpl extends ServiceImpl<CliClientMapper, CliClient> implements ICliClientService {
    @Override
    public void updateForAccountNonLocked(Boolean accountNonLocked, String username) {
        this.baseMapper.updateForAccountNonLocked(accountNonLocked, username);
    }

    @Override
    public CliClient findByUsername(String username) {
        return this.baseMapper.findByUsername(username);
    }
}
