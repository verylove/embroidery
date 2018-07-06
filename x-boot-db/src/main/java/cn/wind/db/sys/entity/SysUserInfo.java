package cn.wind.db.sys.entity;

import cn.wind.mybatis.common.AuditEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author xukk
 * @since 2018-06-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("t_sys_user_info")
public class SysUserInfo extends AuditEntity {

    private static final long serialVersionUID = 1L;

    private Long userId;
    /**
     * 上级ID
     */
    private Long pId;
    /**
     * 员工编号
     */
    private String userNo;
    private String nickName;
    /**
     * 真实名字
     */
    private String realName;
    private String idcard;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 固话
     */
    private String fixedPhone;
    /**
     * 性别
     */
    private Integer gender;
    private String avatar;
    /**
     * 生日
     */
    private Date birthday;
    private String email;
    @Version
    private Integer version;

    private String province;
    private String city;
    private String district;
    private String address;


}
