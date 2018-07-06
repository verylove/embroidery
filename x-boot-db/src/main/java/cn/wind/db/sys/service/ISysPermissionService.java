package cn.wind.db.sys.service;

import cn.wind.db.sys.entity.SysPermission;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author xukk
 * @since 2018-06-07
 */
@CacheConfig(cacheNames = "userPermission")
public interface ISysPermissionService extends IService<SysPermission> {
    /**
     * 根据用户ID查询资源
     * @param userId
     * @return
     */

    @Cacheable(key = "#userId")
    List<SysPermission> findByUserId(Long userId);


    @Cacheable(key = "#roleId")
    List<SysPermission> findByRoleId(Long roleId);

    /**
     * 查询所有
     * @return
     */
    List<SysPermission> findAll();
    /**
     * 通过层级查找
     * 默认升序
     * @param level
     * @return
     */
    List<SysPermission> findByLevelOrderBySort(Integer level);

    /**
     * 通过parendId查找
     * @param parentId
     * @return
     */
    List<SysPermission> findByParentIdOrderBySort(Long parentId);
}
