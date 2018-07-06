package cn.wind.db.cli.service;

import cn.wind.db.cli.entity.CliClient;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 平台会员表 服务类
 * </p>
 *
 * @author xukk
 * @since 2018-06-19
 */
public interface ICliClientService extends IService<CliClient> {
    /**
     * 更新accountNonLocked属性
     *
     * @param accountNonLocked
     * @param username
     */
    void updateForAccountNonLocked(Boolean accountNonLocked, String username);

    /**
     * 根据用户名获取用户
     *
     * @param username
     * @return
     */
    CliClient findByUsername(String username);

}
