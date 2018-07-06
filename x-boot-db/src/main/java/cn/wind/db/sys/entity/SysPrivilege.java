package cn.wind.db.sys.entity;

import cn.wind.mybatis.common.IdEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色资源表
 * </p>
 *
 * @author xukk
 * @since 2018-06-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_sys_privilege")
public class SysPrivilege extends IdEntity {

    private static final long serialVersionUID = 1L;

    private Long roleId;
    private Long permissionId;


}
