package cn.wind.db.cli.entity;

import cn.wind.mybatis.common.AuditEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 平台会员表
 * </p>
 *
 * @author xukk
 * @since 2018-06-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_cli_client")
public class CliClient extends AuditEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 账户是否未过期
     */
    private Boolean accountNonExpired;
    /**
     * 登录凭据是否未过期
     */
    private Boolean credentialsNonExpired;
    /**
     * 账户是否未锁定
     */
    @TableField("account_non_Locked")
    private Boolean accountNonLocked;
    private Boolean enabled;
    /**
     * 用户状态 1 正常
     */
    private Integer status;
    /**
     * 创建者
     */
    private Integer createById;
    /**
     * 修改者
     */
    private Integer modifyById;
    private Date lockTime;
    private String privateKey;
    private String publicKey;


}
