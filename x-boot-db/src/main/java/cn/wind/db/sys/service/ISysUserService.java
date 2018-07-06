package cn.wind.db.sys.service;

import cn.wind.db.sys.entity.SysUser;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import org.apache.ibatis.session.RowBounds;

import java.sql.Wrapper;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author xukk
 * @since 2018-06-07
 */
public interface ISysUserService extends IService<SysUser> {
    /**
     * 更新accountNonLocked属性
     * @param accountNonLocked
     * @param username
     */
    void updateForAccountNonLocked(Boolean accountNonLocked,String username);

    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    SysUser findByUsername(String username);


    /**
     * 级联保存
     * @param sysUser
     * @return
     */
    boolean save(SysUser sysUser);

    SysUser findUserInfoById(Long userId);

    /**
     * 根据查询条件查询
     * @param page
     * @param var1
     * @return
     */
    Page<SysUser> findByCondition(Page page,Map<String, Object> var1);

    boolean updatePasswordById(String password,Long id);
}
