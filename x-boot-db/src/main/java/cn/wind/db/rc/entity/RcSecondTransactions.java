package cn.wind.db.rc.entity;

import java.math.BigDecimal;
import java.util.Date;

import cn.wind.db.ar.entity.ArUser;
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
 * 二手市场数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_rc_second_transactions")
public class RcSecondTransactions extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 城市ID
     */
    private Long city;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 购买时间
     */
    private Date buyTime;
    /**
     * 购买价格
     */
    private BigDecimal buyPrice=new BigDecimal("0.00");
    /**
     * 转让价格
     */
    private BigDecimal transferPrice=new BigDecimal("0.00");
    /**
     * 其他说明
     */
    private String content;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 纬度
     */
    private String latitude;
    /**
     * 发布城市ID
     */
    private Long cityWhere;
    /**
     * 1-有效 0-失效
     */
    private Integer status=1;

    @TableField(exist = false)
    private ArUser user;

    @TableField(exist=false)
    private Long distance;

}
