package cn.wind.db.ar.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import cn.wind.mybatis.common.AuditEntity;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 特价纹身数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_sp_tattoo")
public class ArUserSpTattoo extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 所在城市
     */
    private Long city;
    /**
     * 活动名称
     */
    private String name;
    /**
     * 活动开始日期
     */
    private Date beginTime;
    /**
     * 活动截止日期
     */
    private Date endTime;
    /**
     * 原价
     */
    private BigDecimal originalPrice=new BigDecimal("0.00");
    /**
     * 优惠价
     */
    private BigDecimal preferentialPrice=new BigDecimal("0.00");
    /**
     * 说明
     */
    private String description;
    /**
     * 补充
     */
    private String remark;
    /**
     * 微信
     */
    private String microLetter;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 类型 1-有效 0-失效
     */
    private Integer status=1;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 点赞数
     */
    private Long greatNum=0L;
    /**
     * 留言数
     */
    private Long messageNum=0L;

    @TableField(exist=false)
    private ArUser user;

    @TableField(exist=false)
    private Long distance;

}
