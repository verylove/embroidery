package cn.wind.xboot.vo.sys;

import cn.wind.xboot.vo.sys.SysUserInfoVo;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>Title: SysUserVo</p>
 * <p>Description: TODO</p>
 *
 * @author xukk
 * @version 1.0
 * @date 2018/6/22
 */
@Data
public class SysUserVo {
    private Long id;
    /**
     * UUID
     */
    private String uuid;
    /**
     * 用户名
     */
    private String username;
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
    private Boolean accountNonLocked;
    /**
     * 是否启用
     */
    private Boolean enabled;
    /**
     * 用户状态 1 正常 -1 软删除
     */
    private Integer status;

    private String password;

    private Integer type;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Long createBy;
    private Long modifyBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;
    private SysUserInfoVo userInfo;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;

    private String email;
    private Long userInfoId;
    private String province;
    private String city;
    private String district;
    private String address;
    private String[] addressArray;
    public void init(){
        this.userInfo=new SysUserInfoVo();
        this.userInfo.setId(userInfoId);
        this.userInfo.setEmail(email);
        this.userInfo.setGender(gender);
        this.userInfo.setNickName(nickName);
        this.userInfo.setAvatar(avatar);
        this.userInfo.setBirthday(birthday);
        this.userInfo.setUserNo(userNo);
        this.userInfo.setFixedPhone(fixedPhone);
        this.userInfo.setRealName(realName);
        this.userInfo.setIdcard(idcard);
        this.userInfo.setMobile(mobile);
        if(addressArray != null && addressArray.length>0){
            this.userInfo.setProvince(addressArray.length>0?addressArray[0]:province);
            this.userInfo.setCity(addressArray.length>1?addressArray[1]:city);
            this.userInfo.setDistrict(addressArray.length>2?addressArray[2]:district);
        }
        this.userInfo.setAddress(address);
    }

}
