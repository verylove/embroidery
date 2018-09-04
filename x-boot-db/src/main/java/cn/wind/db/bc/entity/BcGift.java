package cn.wind.db.bc.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.annotations.Version;
import cn.wind.mybatis.common.AuditEntity;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 直播礼物数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-08-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_bc_gift")
public class BcGift extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 礼物名称
     */
    private String name;
    /**
     * 礼物图片
     */
    private String pic;
    /**
     * 礼物价值（刺币）
     */
    private BigDecimal worth= new BigDecimal("0.00");
    /**
     * 礼物状态 1-有效 0-下架
     */
    private Integer status=1;


}
