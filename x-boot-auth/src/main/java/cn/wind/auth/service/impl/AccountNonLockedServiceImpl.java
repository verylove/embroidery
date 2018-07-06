package cn.wind.auth.service.impl;

import cn.wind.auth.service.AccountNonLockedService;
import cn.wind.common.enums.ServerEnum;
import cn.wind.db.cli.service.ICliClientService;
import cn.wind.db.sys.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xukk
 * @date 2018/3/14
 */
@Service
public class AccountNonLockedServiceImpl implements AccountNonLockedService {
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ICliClientService cliClientService;
    @Override
    public void resetAccountNonLocked(String scope, String username) {
        if (scope.equals(ServerEnum.app.name())) {
            cliClientService.updateForAccountNonLocked(true,username);
        } else {
            sysUserService.updateForAccountNonLocked(true,username);
        }
    }
}
