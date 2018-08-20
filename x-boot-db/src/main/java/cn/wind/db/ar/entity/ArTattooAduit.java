package cn.wind.db.ar.entity;

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
 * 用户纹身师申请数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_tattoo_aduit")
public class ArTattooAduit extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号
     */
    private String cardNum;
    /**
     * 身份证照正面
     */
    private String cardPicOne;
    /**
     * 身份证照反面
     */
    private String cardPicTwo;
    /**
     * 身份证照手持
     */
    private String cardPicThree;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 状态 1-待审批 2-审批通过 3-审批拒绝
     */
    private Integer status;

    @TableField(exist=false)
    private ArUser user;

}
