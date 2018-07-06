package cn.wind.db.sys.dao;

import cn.wind.db.sys.entity.SysUserInfo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author xukk
 * @since 2018-06-07
 */
public interface SysUserInfoMapper extends BaseMapper<SysUserInfo> {
    /**
     * 根据用户ID获取用户详情
     * @param userId
     * @return
     */
    SysUserInfo findByUserId(@Param("userId")Long userId);
}
