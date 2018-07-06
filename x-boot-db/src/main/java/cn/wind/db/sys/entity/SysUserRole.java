package cn.wind.db.sys.entity;

import cn.wind.mybatis.common.IdEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author xukk
 * @since 2018-06-21
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_sys_user_role")
public class SysUserRole extends IdEntity {

    private static final long serialVersionUID = 1L;
    private Long userId;
    private Long roleId;


}
