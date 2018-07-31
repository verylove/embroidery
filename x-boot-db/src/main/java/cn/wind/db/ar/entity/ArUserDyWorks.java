package cn.wind.db.ar.entity;

import java.math.BigDecimal;

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
 * 作品/动态数据表
 * </p>
 *
 * @author changzhaoliang
 * @since 2018-07-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cx_ar_user_dy_works")
public class ArUserDyWorks extends AuditEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 作品名称
     */
    private String name;
    /**
     * 参考价格
     */
    private BigDecimal referencePrice=new BigDecimal("0.00");
    /**
     * 作品耗时
     */
    private BigDecimal timeConsume=new BigDecimal("0.00");
    /**
     * 纹身故事
     */
    private String content;
    /**
     * 1-作品 2-动态  3-动态作品
     */
    private Integer type;
    /**
     * 城市ID
     */
    private Long city;
    /**
     * 点赞数
     */
    private Long greatNum=0L;
    /**
     * 评价数
     */
    private Long messageNum=0L;
    /**
     * 1-有效 0-失效
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

    @TableField(exist=false)
    private ArUser user;

    @TableField(exist=false)
    private Long distance;


}
