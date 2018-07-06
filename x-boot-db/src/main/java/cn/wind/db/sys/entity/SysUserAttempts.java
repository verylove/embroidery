package cn.wind.db.sys.entity;

import cn.wind.mybatis.common.IdEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 用户登录尝试次数记录表
 * </p>
 *
 * @author xukk
 * @since 2018-06-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_sys_user_attempts")
public class SysUserAttempts extends IdEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户标识
     */
    private String username;
    /**
     * 尝试次数
     */
    private Integer attempts;
    /**
     * 最后修改时间
     */
    private Date lastModified;


}
