package cn.wind.xboot.vo.sys;

import cn.wind.db.sys.entity.SysUserInfo;
import lombok.Data;

import java.util.Date;

/**
 * <p>Title: SysUserInfoVo</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/22
 */
@Data
public class SysUserInfoVo {

    private Long id;
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
    private String province;
    private String city;
    private String district;
    private String address;
}
