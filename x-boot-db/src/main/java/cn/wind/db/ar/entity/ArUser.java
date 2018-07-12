package cn.wind.db.ar.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import cn.wind.mybatis.common.AuditEntity;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * <p>
 * App用户表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user")
public class ArUser extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 昵称
     */
    private String account;
    /**
     * 用户电话
     */
    private String phone;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像
     */
    private String icon;
    /**
     * 性别 1-男；0-女
     */
    private Integer sex=1;
    /**
     * 个人简介
     */
    private String profile;
    /**
     * 身份 0-爱好者；1-纹身师
     */
    private Integer identity=0;
    /**
     * 省ID
     */
    private Long province;
    /**
     * 市ID
     */
    private Long city;
    /**
     * 区县
     */
    private Long county;
    /**
     * 详细地址
     */
    private String perAddress;
    /**
     * 工作年限
     */
    private Integer workNum=0;
    /**
     * 工作地点名
     */
    private String workPlace;
    /**
     * 签约认证ID
     */
    private Long signId;
    /**
     * 实名认证ID
     */
    private Long nameId;
    /**
     * 店铺认证ID
     */
    private Long storeId;
    /**
     * 认证状态 1-未认证 2-认证中 3-已认证
     */
    private Integer signStatus=1;
    /**
     * 认证状态 1-未认证 2-认证中 3-已认证
     */
    private Integer nameStatus=1;
    /**
     * 认证状态 1-未认证 2-认证中 3-已认证
     */
    private Integer storeStatus=1;
    /**
     * 微信openId
     */
    private String wxOpenId;
    /**
     * qqopenId
     */
    private String qqOpenId;
    /**
     * 等级
     */
    private Integer perLevel=1;
    /**
     * 活跃值
     */
    private Long activeNum=0L;
    /**
     * 魅力值
     */
    private Long charmNum=0L;
    /**
     * 财富值
     */
    private Long wealthNum=0L;
    /**
     * 余额
     */
    private BigDecimal balance= new BigDecimal("0.00");
    /**
     * 是否点过赞 1-点过 0-未点过
     */
    private Integer greatStatus=0;


}
