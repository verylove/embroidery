package cn.wind.db.ar.entity;

import java.util.Date;

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
 * 用户预约订单数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_appoint_order")
public class ArAppointOrder extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 纹身师ID
     */
    private Long artistId;
    /**
     * 昵称
     */
    private String nick;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 需求描述
     */
    private String content;
    /**
     * 预约日期
     */
    private Date appointDate;
    /**
     * 预约时间
     */
    private String appointTime;
    /**
     * 1-有效 0-失效
     */
    private Integer status=1;

    @TableField(exist = false)
    private ArUser user;


}
